package com.github.alexthe666.iceandfire.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelFireArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelIceArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelLightningArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static com.github.alexthe666.iceandfire.item.IafItemRegistry.*;

public class ItemDragonsteelArmor extends IafArmorItem implements IProtectAgainstDragonItem {

    private static final ResourceLocation[] ARMOR_MODIFIERS = new ResourceLocation[]{
        ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "dragonsteel_armor_boots"),
        ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "dragonsteel_armor_leggings"),
        ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "dragonsteel_armor_chestplate"),
        ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "dragonsteel_armor_helmet")};
    private ItemAttributeModifiers attributeModifiers;
    private int bakedDefense = -1;

    public ItemDragonsteelArmor(IafArmorMaterial material, int renderIndex, EquipmentSlot slot) {
        super(material, slot, new Item.Properties());
    }

    @Override
    public void initializeClient(java.util.function.Consumer<net.neoforged.neoforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new net.neoforged.neoforge.client.extensions.common.IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, @NotNull EquipmentSlot armorSlot, @NotNull HumanoidModel<?> _default) {
                boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
                if (itemStack.getItem() instanceof ItemDragonsteelArmor armor) {
                    IafArmorMaterial armorMaterial = armor.getIafMaterial();
                    if (DRAGONSTEEL_FIRE_ARMOR_MATERIAL == armorMaterial)
                        return new ModelDragonsteelFireArmor(inner);
                    if (DRAGONSTEEL_ICE_ARMOR_MATERIAL == armorMaterial)
                        return new ModelDragonsteelIceArmor(inner);
                    if (DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL == armorMaterial)
                        return new ModelDragonsteelLightningArmor(inner);
                }
                return _default;
            }
        });
    }

    // Workaround for armor attributes being registered before the config gets loaded: rebuild them when the config changes
    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(@NotNull ItemStack stack) {
        int defense = getDefense();
        if (attributeModifiers == null || bakedDefense != defense) {
            bakedDefense = defense;
            ResourceLocation id = ARMOR_MODIFIERS[slot.getIndex()];
            EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            builder.add(Attributes.ARMOR, new AttributeModifier(id, defense, AttributeModifier.Operation.ADD_VALUE), group);
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(id, iafMaterial.getToughness(), AttributeModifier.Operation.ADD_VALUE), group);
            if (iafMaterial.getKnockbackResistance() > 0) {
                builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(id, iafMaterial.getKnockbackResistance(), AttributeModifier.Operation.ADD_VALUE), group);
            }
            attributeModifiers = builder.build();
        }
        return attributeModifiers;
    }

    @Override
    public int getMaxDamage(@NotNull ItemStack stack) {
        return iafMaterial.getDurabilityForSlot(this.slot);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.dragonscales_armor.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public int getDefense() {
        return iafMaterial.getDefenseForSlot(this.slot);
    }

    @Override
    public ResourceLocation getArmorTexture(@NotNull ItemStack stack, @NotNull Entity entity, @NotNull EquipmentSlot slot, ArmorMaterial.@NotNull Layer layer, boolean innerModel) {
        if (iafMaterial == DRAGONSTEEL_FIRE_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/armor_dragonsteel_fire" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png"));
        } else if (iafMaterial == IafItemRegistry.DRAGONSTEEL_ICE_ARMOR_MATERIAL) {
            return ResourceLocation.parse("iceandfire:textures/models/armor/armor_dragonsteel_ice" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png"));
        } else {
            return ResourceLocation.parse("iceandfire:textures/models/armor/armor_dragonsteel_lightning" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png"));
        }
    }
}
