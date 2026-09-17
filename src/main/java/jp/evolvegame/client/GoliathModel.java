package jp.evolvegame.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public final class GoliathModel {
    private final ModelPart root;
    private final ModelPart body, head, neck;
    private final ModelPart leftUpperArm, leftForearm, rightUpperArm, rightForearm;
    private final ModelPart leftThigh, leftLowerLeg, leftFoot, rightThigh, rightLowerLeg, rightFoot;

    public GoliathModel(ModelPart root) {
        this.root=root;
        this.body=root.getChild("root").getChild("body");
        ModelPart chest=body.getChild("chest");
        this.head=chest.getChild("head");
        this.neck=chest.getChild("neck");
        this.leftUpperArm=chest.getChild("left_upper_arm");
        this.leftForearm=leftUpperArm.getChild("left_forearm");
        this.rightUpperArm=chest.getChild("right_upper_arm");
        this.rightForearm=rightUpperArm.getChild("right_forearm");
        this.leftThigh=body.getChild("left_thigh");
        this.leftLowerLeg=leftThigh.getChild("left_lower_leg");
        this.leftFoot=leftLowerLeg.getChild("left_foot");
        this.rightThigh=body.getChild("right_thigh");
        this.rightLowerLeg=rightThigh.getChild("right_lower_leg");
        this.rightFoot=rightLowerLeg.getChild("right_foot");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData=new ModelData();
        ModelPartData part_root = modelData.getRoot().addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_body = part_root.addChild("body", ModelPartBuilder.create(), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_chest = part_body.addChild("chest", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0000f, -28.0000f, -6.0000f, 12.0000f, 10.0000f, 11.0000f).uv(0, 0).cuboid(-5.0000f, -18.0000f, -3.5000f, 10.0000f, 6.0000f, 7.0000f).uv(0, 0).cuboid(-5.0000f, -27.0000f, 1.0000f, 10.0000f, 4.0000f, 5.0000f).uv(0, 0).cuboid(-4.0000f, -29.0000f, 3.0000f, 8.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_head = part_chest.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0000f, -7.0000f, -4.0000f, 8.0000f, 7.0000f, 8.0000f), ModelTransform.of(0.0000f, -26.0000f, -3.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_jaw = part_head.addChild("jaw", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5000f, 0.0000f, -2.0000f, 7.0000f, 3.0000f, 7.0000f), ModelTransform.of(0.0000f, -1.0000f, -3.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_upper_arm = part_chest.addChild("left_upper_arm", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0000f, -5.0000f, -4.0000f, 4.0000f, 6.0000f, 8.0000f).uv(0, 0).cuboid(-4.0000f, 0.0000f, -3.0000f, 5.0000f, 10.0000f, 6.0000f), ModelTransform.of(-6.0000f, -23.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_forearm = part_left_upper_arm.addChild("left_forearm", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5000f, 0.0000f, -3.0000f, 5.0000f, 8.0000f, 6.0000f), ModelTransform.of(-1.5000f, 10.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_hand = part_left_forearm.addChild("left_hand", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0000f, 0.0000f, -3.5000f, 6.0000f, 5.0000f, 7.0000f), ModelTransform.of(0.0000f, 8.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_upper_arm = part_chest.addChild("right_upper_arm", ModelPartBuilder.create().uv(0, 0).cuboid(0.0000f, -5.0000f, -4.0000f, 4.0000f, 6.0000f, 8.0000f).uv(0, 0).cuboid(-1.0000f, 0.0000f, -3.0000f, 5.0000f, 10.0000f, 6.0000f), ModelTransform.of(6.0000f, -23.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_forearm = part_right_upper_arm.addChild("right_forearm", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5000f, 0.0000f, -3.0000f, 5.0000f, 8.0000f, 6.0000f), ModelTransform.of(1.5000f, 10.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_hand = part_right_forearm.addChild("right_hand", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0000f, 0.0000f, -3.5000f, 6.0000f, 5.0000f, 7.0000f), ModelTransform.of(0.0000f, 8.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_neck = part_chest.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0000f, -5.0000f, -3.0000f, 6.0000f, 5.0000f, 6.0000f), ModelTransform.of(0.0000f, -25.0000f, 3.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_trap = part_chest.addChild("left_trap", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0000f, -2.0000f, -1.0000f, 5.0000f, 4.0000f, 6.0000f), ModelTransform.of(3.0000f, -26.0000f, 3.0000f, 0.0000f, 0.0000f, 0.2618f));
        ModelPartData part_right_trap = part_chest.addChild("right_trap", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0000f, -2.0000f, -1.0000f, 5.0000f, 4.0000f, 6.0000f), ModelTransform.of(-3.0000f, -26.0000f, 3.0000f, 0.0000f, 0.0000f, -0.2618f));
        ModelPartData part_lrft_lat = part_chest.addChild("lrft_lat", ModelPartBuilder.create().uv(0, 0).cuboid(2.0000f, -25.0000f, 3.0000f, 4.0000f, 7.0000f, 5.0000f).uv(0, 0).cuboid(1.0000f, -22.0000f, 3.0000f, 4.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_lat = part_chest.addChild("right_lat", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0000f, -25.0000f, 3.0000f, 4.0000f, 7.0000f, 5.0000f).uv(0, 0).cuboid(-5.0000f, -22.0000f, 3.0000f, 4.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_mid_back = part_chest.addChild("mid_back", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0000f, -24.0000f, 3.0000f, 6.0000f, 7.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_lower_back = part_body.addChild("lower_back", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0000f, -18.0000f, 3.0000f, 8.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_thigh = part_body.addChild("left_thigh", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5000f, 0.0000f, -2.0000f, 5.0000f, 7.0000f, 5.0000f), ModelTransform.of(-2.5000f, -14.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_lower_leg = part_left_thigh.addChild("left_lower_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5000f, 0.0000f, -2.0000f, 4.0000f, 7.0000f, 4.0000f), ModelTransform.of(0.0000f, 7.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_foot = part_left_lower_leg.addChild("left_foot", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5000f, 0.0000f, -4.0000f, 5.0000f, 3.0000f, 7.0000f), ModelTransform.of(0.0000f, 7.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_thigh = part_body.addChild("right_thigh", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, 0.0000f, -2.0000f, 5.0000f, 7.0000f, 5.0000f), ModelTransform.of(2.5000f, -14.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_lower_leg = part_right_thigh.addChild("right_lower_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5000f, 0.0000f, -2.0000f, 4.0000f, 7.0000f, 4.0000f), ModelTransform.of(0.0000f, 7.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_foot = part_right_lower_leg.addChild("right_foot", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5000f, 0.0000f, -4.0000f, 5.0000f, 3.0000f, 7.0000f), ModelTransform.of(0.0000f, 7.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_glute = part_body.addChild("glute", ModelPartBuilder.create(), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_left_glute = part_glute.addChild("left_glute", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0000f, -15.0000f, 2.0000f, 4.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        ModelPartData part_right_glute = part_glute.addChild("right_glute", ModelPartBuilder.create().uv(0, 0).cuboid(0.0000f, -15.0000f, 2.0000f, 4.0000f, 4.0000f, 4.0000f), ModelTransform.of(0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f));
        return TexturedModelData.of(modelData,64,64);
    }

    public void animate(float time, float speed, float headYawDeg, float headPitchDeg) {
        reset(root);
        float phase=time * 6.2831855f;
        float swing=MathHelper.sin(phase) * speed;
        body.roll = MathHelper.sin(phase) * 0.05236f * speed;
        leftUpperArm.pitch = -swing * 0.31416f;
        rightUpperArm.pitch = swing * 0.31416f;
        leftForearm.pitch = -0.18f - Math.abs(swing)*0.20f;
        rightForearm.pitch = -0.18f - Math.abs(swing)*0.20f;
        leftThigh.pitch = swing * 0.43633f;
        rightThigh.pitch = -swing * 0.43633f;
        leftLowerLeg.pitch = -0.18f - Math.max(0f,-swing)*0.35f;
        rightLowerLeg.pitch = -0.18f - Math.max(0f,swing)*0.35f;
        leftFoot.pitch = -0.10f - Math.abs(swing)*0.12f;
        rightFoot.pitch = -0.10f - Math.abs(swing)*0.12f;
        head.yaw = headYawDeg * 0.017453292f * 0.55f;
        head.pitch = headPitchDeg * 0.017453292f * 0.35f + MathHelper.sin(phase*2f)*0.025f*speed;
        neck.pitch = 0.035f + head.pitch*0.25f;
    }

    private static void reset(ModelPart part) {
        part.traverse().forEach(ModelPart::resetTransform);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        root.render(matrices,vertices,light,overlay,color);
    }
}
