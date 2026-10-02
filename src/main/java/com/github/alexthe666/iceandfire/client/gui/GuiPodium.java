package com.github.alexthe666.iceandfire.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import com.github.alexthe666.iceandfire.inventory.ContainerPodium;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiPodium extends AbstractContainerScreen<ContainerPodium> {

    public static final ResourceLocation PODUIM_TEXTURE = ResourceLocation.parse("iceandfire:textures/gui/podium.png");

    public GuiPodium(ContainerPodium container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageHeight = 133;
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics ms, int x, int y) {
        if (menu != null) {
            String s = I18n.get("block.iceandfire.podium");
            ms.drawString(this.getMinecraft().font, s, this.imageWidth / 2 - this.getMinecraft().font.width(s) / 2, 6, 4210752, false);
        }
        ms.drawString(this.getMinecraft().font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }


    @Override
    public void render(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, PODUIM_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        matrixStack.blit(PODUIM_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
