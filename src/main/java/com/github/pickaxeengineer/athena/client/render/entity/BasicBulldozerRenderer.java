package com.github.pickaxeengineer.athena.client.render.entity;

import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.client.model.entity.BasicBulldozerModel;
import com.github.pickaxeengineer.athena.entity.BasicBulldozerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BasicBulldozerRenderer extends EntityRenderer<BasicBulldozerEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(AthenaMod.MODID, "textures/entity/basic_bulldozer.png");

    protected final BasicBulldozerModel model = new BasicBulldozerModel();

    public BasicBulldozerRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowSize = 1f; // boat: .8f
    }

    @Override
    public void render(BasicBulldozerEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
//        double magicBoatNumberTranslate = .375d;
//        matrixStackIn.translate(0d,magicBoatNumberTranslate, 0d);
        // based on https://greyminecraftcoder.blogspot.com/2020/03/minecraft-model-1144.html
        // -1d in X axis experimentally found, then model is correct, but bb and shadow not
        matrixStackIn.translate(0d,1.501, 0d);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));

        // based on boat values
        matrixStackIn.scale(-1f,-1f,1f);
        // Blockbench: North oriented would result in 0 rotation.
//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180f));

        this.model.setRotationAngles(entityIn, partialTicks, 0f, -.1f,0,0f);
        IVertexBuilder vb1 = bufferIn.getBuffer(model.getRenderType(this.getEntityTexture(entityIn)));
        this.model.render(matrixStackIn,vb1, packedLightIn, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        // no boat, so no water buffer rendering stuff
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BasicBulldozerEntity entity) {
        return TEXTURE;
    }
}
