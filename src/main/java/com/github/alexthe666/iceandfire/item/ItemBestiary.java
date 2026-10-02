package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.util.IafNbt;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.google.common.primitives.Ints;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class ItemBestiary extends Item {

    public ItemBestiary() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void onCraftedBy(ItemStack stack, @NotNull Level worldIn, @NotNull Player playerIn) {
        IafNbt.setTag(stack, new CompoundTag());
        IafNbt.update(stack, tag -> tag.putIntArray("Pages", new int[]{0}));

    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab group, @NotNull NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            items.add(new ItemStack(this));
            ItemStack stack = new ItemStack(IafItemRegistry.BESTIARY.get());
            IafNbt.setTag(stack, new CompoundTag());
            int[] pages = new int[EnumBestiaryPages.values().length];
            for (int i = 0; i < EnumBestiaryPages.values().length; i++) {
                pages[i] = i;
            }
            IafNbt.update(stack, tag -> tag.putIntArray("Pages", pages));
            items.add(stack);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemStackIn = playerIn.getItemInHand(handIn);
        if (worldIn.isClientSide) {
            IceAndFire.PROXY.openBestiaryGui(itemStackIn);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if (IafNbt.getTag(stack) == null) {
            IafNbt.setTag(stack, new CompoundTag());
            IafNbt.update(stack, tag -> tag.putIntArray("Pages", new int[]{EnumBestiaryPages.INTRODUCTION.ordinal()}));

        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (IafNbt.getTag(stack) != null) {
            if (IceAndFire.PROXY.shouldSeeBestiaryContents()) {
                tooltip.add(Component.translatable("bestiary.contains").withStyle(ChatFormatting.GRAY));
                final Set<EnumBestiaryPages> pages = EnumBestiaryPages
                    .containedPages(Ints.asList(IafNbt.getTag(stack).getIntArray("Pages")));
                for (EnumBestiaryPages page : pages) {
                    tooltip.add(Component.literal(ChatFormatting.WHITE + "-").append(Component.translatable("bestiary." + EnumBestiaryPages.values()[page.ordinal()].toString().toLowerCase())).withStyle(ChatFormatting.GRAY));
                }
            } else {
                tooltip.add(Component.translatable("bestiary.hold_shift").withStyle(ChatFormatting.GRAY));
            }

        }
    }

}
