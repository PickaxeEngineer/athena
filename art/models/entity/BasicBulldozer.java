// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class BasicBulldozer extends EntityModel<Entity> {
	private final ModelRenderer body;
	private final ModelRenderer shovel;
	private final ModelRenderer shovelarms;
	private final ModelRenderer wheel;
	private final ModelRenderer RotatedWheel;
	private final ModelRenderer wheel2;
	private final ModelRenderer RotatedWheel2;
	private final ModelRenderer wheel3;
	private final ModelRenderer RotatedWheel3;
	private final ModelRenderer wheel4;
	private final ModelRenderer RotatedWheel4;

	public BasicBulldozer() {
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 14.0F, 13.0F);
		body.setTextureOffset(0, 0).addBox(-10.0F, -11.0F, -28.0F, 20.0F, 16.0F, 24.0F, 0.0F, false);

		shovel = new ModelRenderer(this);
		shovel.setRotationPoint(0.0F, 14.0F, 13.0F);
		setRotationAngle(shovel, 0.4363F, 0.0F, 0.0F);
		shovel.setTextureOffset(68, 70).addBox(-14.0F, -7.5774F, -0.0937F, 28.0F, 16.0F, 2.0F, 0.0F, false);
		shovel.setTextureOffset(0, 0).addBox(-15.0F, 6.43F, -0.09F, 30.0F, 2.0F, 5.0F, 0.0F, false);

		shovelarms = new ModelRenderer(this);
		shovelarms.setRotationPoint(0.0F, 13.0F, 5.5F);
		setRotationAngle(shovelarms, -0.2618F, 0.0F, 0.0F);
		shovelarms.setTextureOffset(94, 111).addBox(10.0F, -1.5176F, -5.5681F, 2.0F, 2.0F, 15.0F, 0.0F, false);
		shovelarms.setTextureOffset(94, 111).addBox(-12.0F, -1.5176F, -5.5681F, 2.0F, 2.0F, 15.0F, 0.0F, false);

		wheel = new ModelRenderer(this);
		wheel.setRotationPoint(0.0F, 24.0F, 3.0F);
		wheel.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		RotatedWheel = new ModelRenderer(this);
		RotatedWheel.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel.addChild(RotatedWheel);
		setRotationAngle(RotatedWheel, -0.7854F, 0.0F, 0.0F);
		RotatedWheel.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		RotatedWheel.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel2 = new ModelRenderer(this);
		wheel2.setRotationPoint(0.0F, 24.0F, -14.0F);
		wheel2.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel2.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		RotatedWheel2 = new ModelRenderer(this);
		RotatedWheel2.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel2.addChild(RotatedWheel2);
		setRotationAngle(RotatedWheel2, -0.7854F, 0.0F, 0.0F);
		RotatedWheel2.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		RotatedWheel2.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel3 = new ModelRenderer(this);
		wheel3.setRotationPoint(24.0F, 24.0F, -14.0F);
		wheel3.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel3.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		RotatedWheel3 = new ModelRenderer(this);
		RotatedWheel3.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel3.addChild(RotatedWheel3);
		setRotationAngle(RotatedWheel3, -0.7854F, 0.0F, 0.0F);
		RotatedWheel3.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		RotatedWheel3.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		wheel4 = new ModelRenderer(this);
		wheel4.setRotationPoint(24.0F, 24.0F, 3.0F);
		wheel4.setTextureOffset(0, 114).addBox(-14.0F, -10.0F, 0.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		wheel4.setTextureOffset(0, 114).addBox(-14.0F, -7.0F, -3.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);

		RotatedWheel4 = new ModelRenderer(this);
		RotatedWheel4.setRotationPoint(-12.0F, -5.0F, 2.0F);
		wheel4.addChild(RotatedWheel4);
		setRotationAngle(RotatedWheel4, -0.7854F, 0.0F, 0.0F);
		RotatedWheel4.setTextureOffset(0, 114).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
		RotatedWheel4.setTextureOffset(0, 114).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		shovel.render(matrixStack, buffer, packedLight, packedOverlay);
		shovelarms.render(matrixStack, buffer, packedLight, packedOverlay);
		wheel.render(matrixStack, buffer, packedLight, packedOverlay);
		wheel2.render(matrixStack, buffer, packedLight, packedOverlay);
		wheel3.render(matrixStack, buffer, packedLight, packedOverlay);
		wheel4.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}