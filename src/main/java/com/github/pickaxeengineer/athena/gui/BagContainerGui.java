package com.github.pickaxeengineer.athena.gui;

import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.containers.BagContainer;
import com.github.pickaxeengineer.athena.items.AthenaItemRegistry;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class BagContainerGui extends ContainerScreen<BagContainer> {

    public BagContainerGui(BagContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX,mouseY);
    }


    final float PLAYER_LABEL_XPOS = 8;
    final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

    final float BAG_LABEL_YPOS = 6;
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format(AthenaItemRegistry.BAG.get().getTranslationKey());
        float BAG_LABEL_XPOS = (xSize / 2.0F) - font.getStringWidth(s) / 2.0F;                  // centre the label
        font.drawString(s, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());

        float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        font.drawString(this.playerInventory.getDisplayName().getFormattedText(),
                PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getInstance();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        // width and height are the size provided to the window when initialised after creation.
        // xSize, ySize are the expected size of the texture-? usually seems to be left as a default.
        // The code below is typical for vanilla containers, so I've just copied that- it appears to centre the texture within
        //  the available window
        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;
        this.blit(edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
    }


    // This is the resource location for the background image
    private static final ResourceLocation TEXTURE = new ResourceLocation(AthenaMod.MODID, "textures/gui/mbe32_flower_bag_bg.png");
}
