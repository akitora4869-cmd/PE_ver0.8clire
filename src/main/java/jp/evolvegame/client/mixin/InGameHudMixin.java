package jp.evolvegame.client.mixin;

import jp.evolvegame.client.EvolveClientState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    /** Hides vanilla armor/health/air/hunger for Monster only. */
    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void evolve$hideMonsterStatusBars(DrawContext context, CallbackInfo ci) {
        if (EvolveClientState.isMonster()) ci.cancel();
    }

    /** Hides the vanilla XP bar; EVOLUTION is rendered by the dedicated HUD. */
    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void evolve$hideMonsterExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        if (EvolveClientState.isMonster()) ci.cancel();
    }

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void evolve$hideMonsterExperienceLevel(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (EvolveClientState.isMonster()) ci.cancel();
    }

    /** Hotbar input still works, but Monster uses the compact ability row. */
    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void evolve$hideMonsterHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (EvolveClientState.isMonster()) ci.cancel();
    }

    /** The server sidebar is useful for debugging but duplicates the Monster HUD in normal play. */
    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V",
            at = @At("HEAD"), cancellable = true)
    private void evolve$hideMonsterSidebar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (EvolveClientState.isMonster()) ci.cancel();
    }
}
