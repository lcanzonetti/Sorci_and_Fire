package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.util.IafNbt;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.message.MessageGetMyrmexHive;
import com.github.alexthe666.iceandfire.message.MessageSetMyrmexHiveNull;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ItemMyrmexStaff extends Item {

    public ItemMyrmexStaff(boolean jungle) {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void onCraftedBy(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
        IafNbt.setTag(itemStack, new CompoundTag());
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if (IafNbt.getTag(stack) == null) {
            IafNbt.setTag(stack, new CompoundTag());
            IafNbt.update(stack, tag -> tag.putUUID("HiveUUID", new UUID(0, 0)));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
        ItemStack itemStackIn = playerIn.getItemInHand(hand);
        if (playerIn.isShiftKeyDown()) {
            return super.use(worldIn, playerIn, hand);
        }
        if (IafNbt.getTag(itemStackIn) != null && IafNbt.getTag(itemStackIn).hasUUID("HiveUUID")) {
            UUID id = IafNbt.getTag(itemStackIn).getUUID("HiveUUID");
            if (!worldIn.isClientSide) {
                MyrmexHive hive = MyrmexWorldData.get(worldIn).getHiveFromUUID(id);
                MyrmexWorldData.addHive(worldIn, new MyrmexHive());
                if (hive != null) {
                    IceAndFire.sendMSGToAll(new MessageGetMyrmexHive(hive.toNBT()));
                } else {
                    IceAndFire.sendMSGToAll(new MessageSetMyrmexHiveNull());
                }
            } else if (id != null && !id.equals(new UUID(0, 0))) {
                IceAndFire.PROXY.openMyrmexStaffGui(itemStackIn);
            }
        }
        playerIn.swing(hand);
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, itemStackIn);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (!context.getPlayer().isShiftKeyDown()) {
            return super.useOn(context);
        } else {
            CompoundTag tag = IafNbt.getTag(context.getPlayer().getItemInHand(context.getHand()));
            if (tag != null && tag.hasUUID("HiveUUID")) {
                UUID id = tag.getUUID("HiveUUID");
                if (!context.getLevel().isClientSide) {
                    MyrmexHive hive = MyrmexWorldData.get(context.getLevel()).getHiveFromUUID(id);
                    if (hive != null) {
                        IceAndFire.sendMSGToAll(new MessageGetMyrmexHive(hive.toNBT()));
                    } else {
                        IceAndFire.sendMSGToAll(new MessageSetMyrmexHiveNull());
                    }
                } else if (id != null && !id.equals(new UUID(0, 0))) {
                    IceAndFire.PROXY.openMyrmexAddRoomGui(context.getPlayer().getItemInHand(context.getHand()), context.getClickedPos(), context.getPlayer().getDirection());
                }
            }
            context.getPlayer().swing(context.getHand());
            return InteractionResult.SUCCESS;
        }
    }
}
