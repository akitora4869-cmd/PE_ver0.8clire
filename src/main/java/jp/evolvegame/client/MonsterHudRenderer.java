package jp.evolvegame.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Compact Evolve-inspired Monster HUD.
 *
 * v0.3.2 deliberately avoids large information panels. Vitals and abilities live
 * around the top-center sight line, close to a boss-bar layout, preserving the
 * player's view of the world. Textures can be overridden by a resource pack.
 */
public final class MonsterHudRenderer implements HudRenderCallback {
    private static final int WHITE = 0xFFF2F2F2;
    private static final int MUTED = 0xFFB7B7B7;
    private static final int RED = 0xFFE3342F;
    private static final int CYAN = 0xFF46BCE7;
    private static final int PURPLE = 0xFFC24EFF;
    private static final int DARK = 0xC9000000;

    private static final Identifier BAR_FRAME_SLIM = id("textures/hud/boss_bar_frame.png");
    private static final Identifier HP_FILL_SLIM = id("textures/hud/boss_bar_hp.png");
    private static final Identifier ARMOR_FILL_SLIM = id("textures/hud/boss_bar_armor.png");
    private static final Identifier EVO_FILL_SLIM = id("textures/hud/boss_bar_evolution.png");
    private static final Identifier SKILL_RING = id("textures/hud/skill_ring.png");
    private static final Identifier SKILL_RING_SELECTED = id("textures/hud/skill_ring_selected.png");
    private static final Identifier SKILL_RING_READY = id("textures/hud/skill_ring_ready.png");
    private static final Identifier COOLDOWN_CIRCLE = id("textures/hud/cooldown_circle.png");
    private static final Identifier EVOLVING_BANNER = id("textures/hud/evolving_banner_slim.png");

    private record SkillIcon(int slot, String key, String name, Identifier icon) {}

    private static final SkillIcon[] SKILLS = {
            new SkillIcon(0, "1", "ROCK", id("textures/skills/rock.png")),
            new SkillIcon(1, "2", "LEAP", id("textures/skills/leap.png")),
            new SkillIcon(2, "3", "FIRE", id("textures/skills/fire.png")),
            new SkillIcon(3, "4", "CHARGE", id("textures/skills/charge.png")),
            new SkillIcon(4, "5", "EVOLVE", id("textures/skills/evolve.png"))
    };

    private static Identifier id(String path) {
        return Identifier.of("evolveclient", path);
    }

    public static void register() {
        HudRenderCallback.EVENT.register(new MonsterHudRenderer());
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null || !EvolveClientState.isMonster() || client.options.hudHidden) return;

        int sw = context.getScaledWindowWidth();
        int sh = context.getScaledWindowHeight();

        drawTopHud(context, client, player, sw);
        drawSkills(context, client, player, sw);
        drawEvolving(context, client, sw, sh);
    }

    private void drawTopHud(DrawContext c, MinecraftClient client, ClientPlayerEntity player, int sw) {
        int barW = Math.max(180, Math.min(300, sw * 34 / 100));
        int barH = 6;
        int x = (sw - barW) / 2;
        int y = 5;

        float hpMax = EvolveClientState.maxMonsterHealth();
        int hp = Math.max(0, Math.min((int) hpMax, player.experienceLevel));
        float hpRatio = hpMax <= 0.0f ? 0.0f : clamp(hp / hpMax);

        float armorMax = EvolveClientState.maxMonsterArmor();
        float armor = Math.max(0.0f, player.getAbsorptionAmount());
        float armorRatio = armorMax <= 0.0f ? 0.0f : clamp(armor / armorMax);

        String armorLabel = "ARMOR";
        String hpLabel = "HP";
        int labelW = Math.max(client.textRenderer.getWidth(armorLabel), client.textRenderer.getWidth(hpLabel));
        int labelX = x - labelW - 5;
        c.drawTextWithShadow(client.textRenderer, Text.literal(armorLabel), labelX, y - 1, CYAN);
        c.drawTextWithShadow(client.textRenderer, Text.literal(hpLabel), x - client.textRenderer.getWidth(hpLabel) - 5, y + 7, RED);

        drawSlimMeter(c, BAR_FRAME_SLIM, ARMOR_FILL_SLIM, x, y, barW, barH, armorRatio);
        drawSlimMeter(c, BAR_FRAME_SLIM, HP_FILL_SLIM, x, y + 7, barW, barH, hpRatio);

        String stage = "STAGE " + EvolveClientState.stageNumber();
        int stageW = client.textRenderer.getWidth(stage);
        c.fill(x + barW + 5, y - 1, x + barW + stageW + 11, y + 13, DARK);
        c.drawTextWithShadow(client.textRenderer, Text.literal(stage), x + barW + 8, y + 2,
                EvolveClientState.stage() == MonsterStage.STAGE_3 ? PURPLE : WHITE);
    }

    private void drawSlimMeter(DrawContext c, Identifier frame, Identifier fill,
                               int x, int y, int w, int h, float ratio) {
        int fillW = Math.round(w * clamp(ratio));
        if (fillW > 0) {
            int sourceW = Math.max(1, Math.round(256.0f * fillW / w));
            c.drawTexture(fill, x, y, fillW, h, 0.0f, 0.0f, sourceW, 10, 256, 10);
        }
        drawTexture(c, frame, x, y, w, h, 256, 10);
    }

    private void drawSkills(DrawContext c, MinecraftClient client, ClientPlayerEntity player, int sw) {
        int size = sw < 520 ? 23 : 27;
        int gap = 8;
        int evolveGap = 8;
        int rowW = size * SKILLS.length + gap * (SKILLS.length - 1) + evolveGap;
        int startX = (sw - rowW) / 2;
        int y = 24;
        int selected = player.getInventory().selectedSlot;

        for (int i = 0; i < SKILLS.length; i++) {
            SkillIcon skill = SKILLS[i];
            int x = startX + i * (size + gap) + (i == 4 ? evolveGap : 0);
            boolean active = selected == skill.slot;
            boolean evolveReady = skill.slot == 4 && player.experienceProgress >= 0.999f
                    && EvolveClientState.stage() != MonsterStage.STAGE_3 && !EvolveClientState.isEvolving();
            ItemStack stack = player.getInventory().getStack(skill.slot);
            boolean cooldown = !stack.isEmpty() && player.getItemCooldownManager().isCoolingDown(stack.getItem());

            Identifier ring = evolveReady ? SKILL_RING_READY : (active ? SKILL_RING_SELECTED : SKILL_RING);
            drawTexture(c, ring, x, y, size, size, 48, 48);

            int icon = Math.max(14, size - 8);
            drawTexture(c, skill.icon, x + (size - icon) / 2, y + (size - icon) / 2, icon, icon, 128, 128);

            if (cooldown) drawTexture(c, COOLDOWN_CIRCLE, x, y, size, size, 48, 48);

            int keyW = client.textRenderer.getWidth(skill.key);
            c.drawTextWithShadow(client.textRenderer, Text.literal(skill.key),
                    x + (size - keyW) / 2, y + size - 1, cooldown ? MUTED : WHITE);
        }
    }

    private void drawEvolving(DrawContext c, MinecraftClient client, int sw, int sh) {
        if (!EvolveClientState.isEvolving()) return;

        int w = Math.max(150, Math.min(220, sw / 4));
        int h = 28;
        int x = (sw - w) / 2;
        int y = Math.max(75, sh / 7);
        drawTexture(c, EVOLVING_BANNER, x, y, w, h, 320, 40);

        String text = "EVOLVING  •  CANNOT CANCEL";
        int tw = client.textRenderer.getWidth(text);
        c.drawTextWithShadow(client.textRenderer, Text.literal(text), x + (w - tw) / 2,
                y + (h - client.textRenderer.fontHeight) / 2, PURPLE);
    }

    private void drawTexture(DrawContext c, Identifier texture,
                             int x, int y, int w, int h, int sourceW, int sourceH) {
        c.drawTexture(texture, x, y, w, h, 0.0f, 0.0f, sourceW, sourceH, sourceW, sourceH);
    }

    private float clamp(float v) {
        return Math.max(0.0f, Math.min(1.0f, v));
    }
}
