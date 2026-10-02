package com.github.alexthe666.iceandfire.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemModArmor extends IafArmorItem {

    public ItemModArmor(IafArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Properties());
    }

    @Override
    public @NotNull String getDescriptionId(@NotNull ItemStack stack) {
        if (this == IafItemRegistry.EARPLUGS.get()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1) {
                return "item.iceandfire.air_pods";
            }
        }
        return super.getDescriptionId(stack);
    }

    @Override
    @Nullable
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        if (this.iafMaterial == IafItemRegistry.MYRMEX_DESERT_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "myrmex_desert_layer_2" : "myrmex_desert_layer_1") + ".png");
        }
        if (this.iafMaterial == IafItemRegistry.MYRMEX_JUNGLE_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "myrmex_jungle_layer_2" : "myrmex_jungle_layer_1") + ".png");
        }
        if (this.iafMaterial == IafItemRegistry.SHEEP_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/" + (slot == EquipmentSlot.LEGS ? "sheep_disguise_layer_2" : "sheep_disguise_layer_1") + ".png");
        }
        if (this.iafMaterial == IafItemRegistry.EARPLUGS_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/earplugs_layer_1.png");
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (this == IafItemRegistry.EARPLUGS.get()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1) {
                tooltip.add(Component.translatable("item.iceandfire.air_pods.desc").withStyle(ChatFormatting.GREEN));
            }
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
