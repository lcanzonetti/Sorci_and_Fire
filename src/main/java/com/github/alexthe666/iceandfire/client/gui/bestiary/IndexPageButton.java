package com.github.alexthe666.iceandfire.client.gui.bestiary;

import net.minecraft.client.gui.GuiGraphics;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class IndexPageButton extends Button {

    public IndexPageButton(int x, int y, Component buttonText,
                           net.minecraft.client.gui.components.Button.OnPress butn) {
        super(x, y, 160, 32, buttonText, butn, DEFAULT_NARRATION);
        this.width = 160;
        this.height = 32;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY, float partial) {
        if (this.active) {
            Font font = IafConfig.useVanillaFont ? Minecraft.getInstance().font : (Font) IceAndFire.PROXY.getFontRenderer();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, ResourceLocation.parse("iceandfire:textures/gui/bestiary/widgets.png"));
            boolean flag = isHoveredOrFocused();
            matrixStack.blit(ResourceLocation.parse("iceandfire:textures/gui/bestiary/widgets.png"), this.getX(), this.getY(), 0, flag ? 32 : 0, this.width, this.height);
            int j = flag ? 0XFAE67D : 0X303030;
            matrixStack.drawString(font, this.getMessage().getVisualOrderText(), (this.getX() + this.width / 2 - font.width(this.getMessage().getString()) / 2), this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24, false);
        }
    }
}
