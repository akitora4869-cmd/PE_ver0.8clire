package jp.evolvegame.client.mixin;

import jp.evolvegame.client.MonsterRenderBridge;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void evolve$replacePlayerModel(AbstractClientPlayerEntity player,
                                           float yaw,
                                           float tickDelta,
                                           MatrixStack matrices,
                                           VertexConsumerProvider vertexConsumers,
                                           int light,
                                           CallbackInfo ci) {
        if (MonsterRenderBridge.renderMonster(player, yaw, tickDelta, matrices, vertexConsumers, light)) {
            ci.cancel();
        }
    }
}
