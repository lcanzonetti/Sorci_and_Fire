package com.github.alexthe666.iceandfire.api;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.food.FoodProperties;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class FoodUtils {

    public static int getFoodPoints(Entity entity) {
        int foodPoints = Math.round(entity.getBbWidth() * entity.getBbHeight() * 10);
        if (entity instanceof AgeableMob) {
            return foodPoints;
        }
        if (entity instanceof Player) {
            return 15;
        }
        return 0;
    }

    public static int getFoodPoints(ItemStack item, boolean meatOnly, boolean includeFish) {
        FoodProperties properties = item == null ? null : item.getFoodProperties(null);
        if (item != null && !item.isEmpty() && properties != null) {
            int food = properties.nutrition() * 10;
            if (!meatOnly) {
                return food;
            } else if (item.is(ItemTags.MEAT)) {
                return food;
            } else if (includeFish && item.getItem() == Items.COD) {
                return food;
            }
        }
        return 0;
    }

    public static boolean isSeeds(ItemStack stack) {
        return stack.is(Tags.Items.SEEDS);
    }
}
