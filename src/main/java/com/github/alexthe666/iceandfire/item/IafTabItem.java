package com.github.alexthe666.iceandfire.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

/**
 * Items that show several variants in the creative tab (replacement for the removed Item#fillItemCategory).
 */
public interface IafTabItem {
    void fillItemCategory(NonNullList<ItemStack> items);
}
