package jp.evolvegame.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

/** Minimal Hunter combat HUD. Exact magazine count remains server-authoritative and is shown in ActionBar. */
public final class HunterHudRenderer implements HudRenderCallback {
    private static final int WHITE=0xFFF2F2F2, CYAN=0xFF65D7FF, DARK=0xB9000000;
    public static void register(){HudRenderCallback.EVENT.register(new HunterHudRenderer());}
    @Override public void onHudRender(DrawContext c, RenderTickCounter t){
        MinecraftClient mc=MinecraftClient.getInstance(); ClientPlayerEntity p=mc.player;
        if(p==null||!EvolveClientState.isHunter()||mc.options.hudHidden)return;
        ItemStack held=p.getMainHandStack(); if(held.isEmpty())return;
        String name=held.getName().getString();
        if(!(name.contains("RIFLE")||name.contains("CARBINE")||name.contains("SHOTGUN")||name.contains("HARPOON")))return;
        int sw=c.getScaledWindowWidth(), sh=c.getScaledWindowHeight(); int w=Math.max(110,mc.textRenderer.getWidth(name)+22); int x=sw-w-10,y=sh-47;
        c.fill(x,y,sw-8,sh-20,DARK); c.fill(x,y,x+2,sh-20,CYAN);
        c.drawTextWithShadow(mc.textRenderer,Text.literal(name),x+8,y+5,CYAN);
        c.drawTextWithShadow(mc.textRenderer,Text.literal("R-CLICK FIRE   F RELOAD"),x+8,y+16,WHITE);
    }
}
