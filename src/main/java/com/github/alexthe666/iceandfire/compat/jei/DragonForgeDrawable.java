package com.github.alexthe666.iceandfire.compat.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DragonForgeDrawable implements IDrawable {
    private final ResourceLocation texture;

    public DragonForgeDrawable(String dragonType) {
        this.texture = ResourceLocation.parse("iceandfire:textures/gui/dragonforge_" + dragonType + ".png");
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 120;
    }

    @Override
    public void draw(@NotNull GuiGraphics guiGraphics, int xOffset, int yOffset) {
        guiGraphics.blit(texture, xOffset, yOffset, 3, 4, 170, 79);
        int scaledProgress = (Minecraft.getInstance().player.tickCount % 100) * 128 / 100;
        guiGraphics.blit(texture, xOffset + 9, yOffset + 19, 0, 166, scaledProgress, 38);
    }
}
