package com.github.alexthe666.iceandfire.item;

import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ItemDragonBow extends BowItem {
    public static final Predicate<ItemStack> DRAGON_ARROWS = (stack)
        -> stack.is(TagKey.create(Registries.ITEM, IafTagRegistry.DRAGON_ARROWS));

    public ItemDragonBow() {
        super(new Item.Properties().durability(584));
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return DRAGON_ARROWS;
    }
}
