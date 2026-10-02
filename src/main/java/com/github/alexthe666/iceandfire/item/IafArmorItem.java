package com.github.alexthe666.iceandfire.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Common base for Ice and Fire armor pieces, bridging the 1.18 style (material + slot) to the 1.21 armor API.
 */
public class IafArmorItem extends ArmorItem implements IafClientItem {

    protected final IafArmorMaterial iafMaterial;
    protected final EquipmentSlot slot;

    public IafArmorItem(IafArmorMaterial material, EquipmentSlot slot, Item.Properties properties) {
        super(material.holder(), IafArmorMaterial.typeFor(slot), properties.durability(material.getDurabilityForSlot(slot)));
        this.iafMaterial = material;
        this.slot = slot;
    }

    public IafArmorItem(IafArmorMaterial material, EquipmentSlot slot) {
        this(material, slot, new Item.Properties());
    }

    public IafArmorMaterial getIafMaterial() {
        return iafMaterial;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    /**
     * Replacement for the removed onArmorTick hook: called every tick while the stack is worn by a player.
     */
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof Player player && player.getItemBySlot(this.slot) == stack) {
            onArmorTick(stack, level, player);
        }
    }
}
