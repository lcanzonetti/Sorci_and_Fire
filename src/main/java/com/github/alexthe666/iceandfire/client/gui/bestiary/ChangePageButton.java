package com.github.alexthe666.iceandfire.client.gui.bestiary;

import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChangePageButton extends Button {
    private final boolean right;
    public int lastpage = 1;
    private final int color;

    public ChangePageButton(int x, int y, boolean right, int color, OnPress press) {
        super(x, y, 23, 10, Component.literal(""), press, DEFAULT_NARRATION);
        this.right = right;
        this.color = color;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY, float partial) {
        if (this.active) {
            boolean flag = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
            RenderSystem.setShaderTexture(0, ResourceLocation.parse("iceandfire:textures/gui/bestiary/widgets.png"));
            int i = 0;
            int j = 64;
            if (flag) {
                i += 23;
            }

            if (!this.right) {
                j += 13;
            }
            j += color * 23;

            matrixStack.blit(ResourceLocation.parse("iceandfire:textures/gui/bestiary/widgets.png"), this.getX(), this.getY(), i, j, width, height);
        }
    }
}
