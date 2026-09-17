package jp.evolvegame.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public final class MonsterRenderBridge {
    private static final Identifier GOLIATH_TEXTURE = Identifier.of("evolveclient","textures/entity/goliath.png");
    private static GoliathModel model;

    private MonsterRenderBridge() {}

    public static boolean renderMonster(AbstractClientPlayerEntity player, float yaw, float tickDelta,
                                        MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MonsterStage stage=stageFor(player);
        if(stage==MonsterStage.NONE) return false;
        if(model==null) model=new GoliathModel(GoliathModel.getTexturedModelData().createModel());

        double horizontal=player.getVelocity().horizontalLength();
        float movement=(float)Math.min(1.0,horizontal*7.0);
        float time=(player.age+tickDelta)/20.0f;

        model.animate(time,movement,0f,player.getPitch());

        matrices.push();
        // PlayerEntityRenderer is cancelled by our mixin, so the normal LivingEntityRenderer
        // model-space conversion is not applied for us. Minecraft ModelPart uses +Y downward.
        // Recreate that conversion here: flip model X/Y into world render space, then anchor
        // the model at the player's feet. This fixes the Goliath appearing vertically inverted.
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f-yaw));
        float scale=switch(stage){case STAGE_1->0.92f;case STAGE_2->1.00f;case STAGE_3->1.08f;default->1f;};
        matrices.scale(-scale,-scale,scale);
        matrices.translate(0.0f,-1.501f,0.0f);

        VertexConsumer vertices=vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(GOLIATH_TEXTURE));
        model.render(matrices,vertices,light,OverlayTexture.DEFAULT_UV,0xFFFFFFFF);
        matrices.pop();
        return true;
    }

    private static MonsterStage stageFor(AbstractClientPlayerEntity player) {
        if(player.getScoreboardTeam()==null) return MonsterStage.NONE;
        String n=player.getScoreboardTeam().getName();
        if(n.startsWith("evolve_monster_s1")) return MonsterStage.STAGE_1;
        if(n.startsWith("evolve_monster_s2")) return MonsterStage.STAGE_2;
        if(n.startsWith("evolve_monster_s3")) return MonsterStage.STAGE_3;
        return MonsterStage.NONE;
    }
}
