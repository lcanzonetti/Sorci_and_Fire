package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.item.IafArmorMaterial;
import com.github.alexthe666.iceandfire.block.BlockSeaSerpentScales;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentArmor;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentScales;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Locale;

public enum EnumSeaSerpent {
    BLUE(ChatFormatting.BLUE),
    BRONZE(ChatFormatting.GOLD),
    DEEPBLUE(ChatFormatting.DARK_BLUE),
    GREEN(ChatFormatting.DARK_GREEN),
    PURPLE(ChatFormatting.DARK_PURPLE),
    RED(ChatFormatting.DARK_RED),
    TEAL(ChatFormatting.AQUA);

    public String resourceName;
    public ChatFormatting color;
    public IafArmorMaterial armorMaterial;
    public DeferredItem<Item> scale;
    public DeferredItem<Item> helmet;
    public DeferredItem<Item> chestplate;
    public DeferredItem<Item> leggings;
    public DeferredItem<Item> boots;
    public DeferredBlock<Block> scaleBlock;

    EnumSeaSerpent(ChatFormatting color) {
        this.resourceName = this.name().toLowerCase(Locale.ROOT);
        this.color = color;
        this.scaleBlock = IafBlockRegistry.BLOCKS.register("sea_serpent_scale_block_%s".formatted(this.resourceName), () -> new BlockSeaSerpentScales(resourceName, color));
        //this.scaleBlock = new BlockSeaSerpentScales(this.resourceName, this.color);
    }


    public static void initArmors() {
        for (EnumSeaSerpent color : EnumSeaSerpent.values()) {
            color.armorMaterial = new IafArmorMaterial("iceandfire:sea_serpent_scales_" + color.resourceName, 30, new int[]{4, 8, 7, 4}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 2.5F);
            color.scale = IafItemRegistry.ITEMS.register("sea_serpent_scales_" + color.resourceName, () ->
                new ItemSeaSerpentScales(color.resourceName, color.color));
            color.helmet = IafItemRegistry.ITEMS.register("tide_" + color.resourceName + "_helmet", () ->
                new ItemSeaSerpentArmor(color, color.armorMaterial, EquipmentSlot.HEAD));
            color.chestplate = IafItemRegistry.ITEMS.register("tide_" + color.resourceName + "_chestplate", () ->
                new ItemSeaSerpentArmor(color, color.armorMaterial, EquipmentSlot.CHEST));
            color.leggings = IafItemRegistry.ITEMS.register("tide_" + color.resourceName + "_leggings", () ->
                new ItemSeaSerpentArmor(color, color.armorMaterial, EquipmentSlot.LEGS));
            color.boots = IafItemRegistry.ITEMS.register("tide_" + color.resourceName + "_boots", () ->
                new ItemSeaSerpentArmor(color, color.armorMaterial, EquipmentSlot.FEET));
        }
    }
}
