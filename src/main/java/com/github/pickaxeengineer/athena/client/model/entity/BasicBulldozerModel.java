package com.github.pickaxeengineer.athena.client.model.entity;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.github.pickaxeengineer.athena.entity.BasicBulldozerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BasicBulldozerModel extends EntityModel<Entity> {
	private final ModelRenderer bulldozer;
	private final ModelRenderer body;
	private final ModelRenderer shovel;
	private final ModelRenderer shovelarms;
	private final ModelRenderer wheel;
	private final ModelRenderer rotatedWheel;
	private final ModelRenderer wheel2;
	private final ModelRenderer rotatedWheel2;
	private final ModelRenderer wheel3;
	private final ModelRenderer rotatedWheel3;
	private final ModelRenderer wheel4;
	private final ModelRenderer rotatedWheel4;

	public BasicBulldozerModel() {
		textureWidth = 128;
		textureHeight = 128;

		bulldozer = new ModelRenderer(this);
		bulldozer.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(bulldozer, 0.0F, 3.1416F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		bulldozer.addChild(body);
		body.setTextureOffset(0, 0).addBox(-10.0F, -21.0F, -15.0F, 20.0F, 16.0F, 24.0F, 0.0F, false);

		shovel = new ModelRenderer(this);
		shovel.setRotationPoint(0.0F, -10.0F, 13.0F);
		bulldozer.addChild(shovel);
		setRotationAngle(shovel, 0.4363F, 0.0F, 0.0F);
		shovel.setTextureOffset(68, 70).addBox(-14.0F, -7.5774F, -0.0937F, 28.0F, 16.0F, 2.0F, 0.0F, false);
		shovel.setTextureOffset(0, 0).addBox(-15.0F, 6.43F, -0.09F, 30.0F, 2.0F, 5.0F, 0.0F, false);

		shovelarms = new ModelRenderer(this);
		shovelarms.setRotationPoint(0.0F, -11.0F, 5.5F);
		bulldozer.addChild(shovelarms);
		setRotationAngle(shovelarms, -0.2618F, 0.0F, 0.0F);
		shovelarms.setTextureOffset(94, 111).addBox(10.0F, -1.5176F, -5.5681F, 2.0F, 2.0F, 15.0F, 0.0F, false);
		shovelarms.setTextureOffset(94, 111).addBox(-12.0F, -1.5176F, -5.5681F, 2.0F, 2.0F, 15.0F, 0.0F, false);

		wheel = new ModelRenderer(this);
		wheel.setRotationPoint(0.0F, 0.0F, 3.0F);
		bulldozer.addChild(wheel);
		wheel.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		rotatedWheel = new ModelRenderer(this);
		rotatedWheel.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel.addChild(rotatedWheel);
		setRotationAngle(rotatedWheel, -0.7854F, 0.0F, 0.0F);
		rotatedWheel.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		rotatedWheel.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel2 = new ModelRenderer(this);
		wheel2.setRotationPoint(0.0F, 0.0F, -14.0F);
		bulldozer.addChild(wheel2);
		wheel2.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel2.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		rotatedWheel2 = new ModelRenderer(this);
		rotatedWheel2.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel2.addChild(rotatedWheel2);
		setRotationAngle(rotatedWheel2, -0.7854F, 0.0F, 0.0F);
		rotatedWheel2.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		rotatedWheel2.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel3 = new ModelRenderer(this);
		wheel3.setRotationPoint(24.0F, 0.0F, -14.0F);
		bulldozer.addChild(wheel3);
		wheel3.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel3.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		rotatedWheel3 = new ModelRenderer(this);
		rotatedWheel3.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel3.addChild(rotatedWheel3);
		setRotationAngle(rotatedWheel3, -0.7854F, 0.0F, 0.0F);
		rotatedWheel3.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		rotatedWheel3.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel4 = new ModelRenderer(this);
		wheel4.setRotationPoint(24.0F, 0.0F, 3.0F);
		bulldozer.addChild(wheel4);
		wheel4.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel4.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		rotatedWheel4 = new ModelRenderer(this);
		rotatedWheel4.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel4.addChild(rotatedWheel4);
		setRotationAngle(rotatedWheel4, -0.7854F, 0.0F, 0.0F);
		rotatedWheel4.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		rotatedWheel4.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bulldozer.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}