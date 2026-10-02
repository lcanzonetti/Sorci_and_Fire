package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.IafTabItem;
import net.minecraft.core.NonNullList;
import com.github.alexthe666.iceandfire.item.ItemGeneric;
import com.github.alexthe666.iceandfire.item.ItemStoneStatue;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IafCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IceAndFire.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ITEMS = TABS.register("items", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup." + IceAndFire.MODID))
        .icon(() -> new ItemStack(IafItemRegistry.DRAGON_SKULL_FIRE.get()))
        .displayItems((parameters, output) -> IafItemRegistry.ITEMS.getEntries().forEach(holder -> {
            Item item = holder.get();
            if (item instanceof IafTabItem tabItem) {
                NonNullList<ItemStack> variants = NonNullList.create();
                tabItem.fillItemCategory(variants);
                output.acceptAll(variants);
            } else if (isVisibleItem(item)) {
                output.accept(item);
            }
        }))
        .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_BLOCKS = TABS.register("blocks", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.iceandfire.blocks"))
        .icon(() -> new ItemStack(IafBlockRegistry.DRAGON_SCALE_RED.get()))
        .withTabsBefore(TAB_ITEMS.getKey())
        .displayItems((parameters, output) -> IafBlockRegistry.BLOCKS.getEntries().forEach(holder -> {
            Block block = holder.get();
            Item item = block.asItem();
            if (item != net.minecraft.world.item.Items.AIR && IafBlockRegistry.isInTab(block)) {
                output.accept(item);
            }
        }))
        .build());

    private static boolean isVisibleItem(Item item) {
        if (item instanceof ItemStoneStatue) {
            return false;
        }
        return !(item instanceof ItemGeneric generic) || !generic.isHidden();
    }
}
