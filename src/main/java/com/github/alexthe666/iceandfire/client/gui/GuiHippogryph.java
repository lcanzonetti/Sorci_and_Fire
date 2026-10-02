package com.github.alexthe666.iceandfire.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.inventory.ContainerHippogryph;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiHippogryph extends AbstractContainerScreen<ContainerHippogryph> {
    private static final ResourceLocation TEXTURE = ResourceLocation.parse("iceandfire:textures/gui/hippogryph.png");
    private float mousePosx;
    private float mousePosY;

    public GuiHippogryph(ContainerHippogryph dragonInv, Inventory playerInv, Component name) {
        super(dragonInv, playerInv, name);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY) {
        Entity entity = IceAndFire.PROXY.getReferencedMob();
        Font font = this.getMinecraft().font;
        if (entity instanceof EntityHippogryph) {
            EntityHippogryph hippo = (EntityHippogryph) entity;
            matrixStack.drawString(font, hippo.getDisplayName().getString(), 8, 6, 4210752, false);
        }
        matrixStack.drawString(font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }

    @Override
    public void render(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.mousePosx = mouseX;
        this.mousePosY = mouseY;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        matrixStack.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        Entity entity = IceAndFire.PROXY.getReferencedMob();
        if (entity instanceof EntityHippogryph) {
            EntityHippogryph hippo = (EntityHippogryph) entity;
            if (hippo.isChested()) {
                matrixStack.blit(TEXTURE, i + 79, j + 17, 0, this.imageHeight, 5 * 18, 54);
            }
            IafGuiUtil.renderEntityInInventory(matrixStack, i + 51, j + 60, 17, i + 51 - this.mousePosx, j + 75 - 50 - this.mousePosY,
                hippo);
        }
    }

}
