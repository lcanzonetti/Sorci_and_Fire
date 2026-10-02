package com.github.alexthe666.iceandfire.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class IafGuiUtil {

    private IafGuiUtil() {
    }

    /**
     * Same behavior as the 1.18 InventoryScreen#renderEntityInInventory: the entity's feet are placed at (x, y)
     * and it looks towards the given offsets.
     */
    public static void renderEntityInInventory(GuiGraphics guiGraphics, int x, int y, float scale, float lookX, float lookY, LivingEntity entity) {
        float f = (float) Math.atan(lookX / 40.0F);
        float f1 = (float) Math.atan(lookY / 40.0F);
        Quaternionf pose = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf camera = new Quaternionf().rotateX(f1 * 20.0F * ((float) Math.PI / 180F));
        pose.mul(camera);
        float bodyRot = entity.yBodyRot;
        float yRot = entity.getYRot();
        float xRot = entity.getXRot();
        float headRotO = entity.yHeadRotO;
        float headRot = entity.yHeadRot;
        entity.yBodyRot = 180.0F + f * 20.0F;
        entity.setYRot(180.0F + f * 40.0F);
        entity.setXRot(-f1 * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();
        InventoryScreen.renderEntityInInventory(guiGraphics, x, y, scale, new Vector3f(), pose, camera, entity);
        entity.yBodyRot = bodyRot;
        entity.setYRot(yRot);
        entity.setXRot(xRot);
        entity.yHeadRotO = headRotO;
        entity.yHeadRot = headRot;
    }
}
