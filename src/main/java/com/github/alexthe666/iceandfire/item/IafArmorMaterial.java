package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Ice and Fire armor material definition. Since 1.20.5 armor materials are registry entries, so every
 * definition registers a vanilla {@link ArmorMaterial} and keeps the extra Ice and Fire data (durability factor).
 */
public class IafArmorMaterial {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, IceAndFire.MODID);
    private static final Map<ResourceLocation, IafArmorMaterial> BY_ID = new HashMap<>();

    protected static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReduction;
    private final int enchantability;
    private final Holder<SoundEvent> sound;
    private final float toughness;
    private Supplier<Ingredient> repairMaterial = () -> Ingredient.EMPTY;
    private final DeferredHolder<ArmorMaterial, ArmorMaterial> holder;

    public IafArmorMaterial(String name, int durability, int[] damageReduction, int enchantability, Holder<SoundEvent> sound, float toughness) {
        this.name = name;
        this.maxDamageFactor = durability;
        this.damageReduction = damageReduction;
        this.enchantability = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        String id = sanitize(name);
        this.holder = ARMOR_MATERIALS.register(id, () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                for (ArmorItem.Type type : ArmorItem.Type.values()) {
                    map.put(type, getDefenseForSlot(type.getSlot()));
                }
            }),
            this.enchantability, this.sound, () -> this.repairMaterial.get(),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, id))),
            this.toughness, 0));
        BY_ID.put(this.holder.getId(), this);
    }

    private static String sanitize(String name) {
        String path = name.contains(":") ? name.substring(name.indexOf(':') + 1) : name;
        return path.toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    public static ArmorItem.Type typeFor(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> ArmorItem.Type.HELMET;
            case CHEST -> ArmorItem.Type.CHESTPLATE;
            case LEGS -> ArmorItem.Type.LEGGINGS;
            case FEET -> ArmorItem.Type.BOOTS;
            default -> throw new IllegalArgumentException("Not an armor slot: " + slot);
        };
    }

    /**
     * Looks up the Ice and Fire definition backing a registered armor material, or null for foreign materials.
     */
    public static IafArmorMaterial from(Holder<ArmorMaterial> material) {
        return material.unwrapKey().map(key -> BY_ID.get(key.location())).orElse(null);
    }

    public Holder<ArmorMaterial> holder() {
        return holder;
    }

    public String getName() {
        return name;
    }

    public int getDurabilityForSlot(EquipmentSlot slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    public int getDefenseForSlot(EquipmentSlot slotIn) {
        return damageReduction[slotIn.getIndex()];
    }

    public int getEnchantmentValue() {
        return enchantability;
    }

    public float getToughness() {
        return toughness;
    }

    public float getKnockbackResistance() {
        return 0;
    }

    public void setRepairMaterial(Ingredient ingredient) {
        this.repairMaterial = () -> ingredient;
    }

    public void setRepairMaterial(Supplier<Ingredient> ingredient) {
        this.repairMaterial = ingredient;
    }
}
