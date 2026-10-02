package com.github.alexthe666.iceandfire.util;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

/**
 * Enchantments are a datapack registry since 1.21; vanilla enchantments are only available as keys.
 */
public final class IafEnchantmentHelper {

    private IafEnchantmentHelper() {
    }

    public static Holder<Enchantment> holder(HolderLookup.Provider registries, ResourceKey<Enchantment> key) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

    public static int getLevel(Level level, ResourceKey<Enchantment> key, ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        return EnchantmentHelper.getItemEnchantmentLevel(holder(level.registryAccess(), key), stack);
    }
}
