package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Damage types are a datapack registry since 1.19.4, see data/iceandfire/damage_type.
 */
public class IafDamageRegistry {
    public static final String GORGON_DMG_TYPE = "gorgon";
    public static final String DRAGON_FIRE_TYPE = "dragon_fire";
    public static final String DRAGON_ICE_TYPE = "dragon_ice";
    public static final String DRAGON_LIGHTNING_TYPE = "dragon_lightning";

    public static final ResourceKey<DamageType> GORGON = key(GORGON_DMG_TYPE);
    public static final ResourceKey<DamageType> DRAGON_FIRE = key(DRAGON_FIRE_TYPE);
    public static final ResourceKey<DamageType> DRAGON_ICE = key(DRAGON_ICE_TYPE);
    public static final ResourceKey<DamageType> DRAGON_LIGHTNING = key(DRAGON_LIGHTNING_TYPE);

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, name));
    }

    private static Holder<DamageType> holder(Entity entity, ResourceKey<DamageType> key) {
        return entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
    }

    /**
     * Damage source with the randomised Ice and Fire death messages (death.attack.[type].[0|1] and .attacker_[0|1]).
     */
    public static class CustomEntityDamageSource extends DamageSource {
        public CustomEntityDamageSource(Holder<DamageType> type, @Nullable Entity directEntity, @Nullable Entity causingEntity) {
            super(type, directEntity, causingEntity);
        }

        @Override
        public @NotNull Component getLocalizedDeathMessage(LivingEntity entityLivingBaseIn) {
            LivingEntity livingentity = entityLivingBaseIn.getKillCredit();
            String s = "death.attack." + this.getMsgId();
            int index = entityLivingBaseIn.getRandom().nextInt(2);
            String s1 = s + "." + index;
            String s2 = s + ".attacker_" + index;
            return livingentity != null ? Component.translatable(s2, entityLivingBaseIn.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s1, entityLivingBaseIn.getDisplayName());
        }
    }

    public static DamageSource causeGorgonDamage(Entity entity) {
        return new CustomEntityDamageSource(holder(entity, GORGON), entity, entity);
    }

    public static DamageSource causeDragonFireDamage(Entity entity) {
        return new CustomEntityDamageSource(holder(entity, DRAGON_FIRE), entity, entity);
    }

    public static DamageSource causeIndirectDragonFireDamage(Entity source, @Nullable Entity indirectEntityIn) {
        return new CustomEntityDamageSource(holder(source, DRAGON_FIRE), source, indirectEntityIn);
    }

    public static DamageSource causeDragonIceDamage(Entity entity) {
        return new CustomEntityDamageSource(holder(entity, DRAGON_ICE), entity, entity);
    }

    public static DamageSource causeIndirectDragonIceDamage(Entity source, @Nullable Entity indirectEntityIn) {
        return new CustomEntityDamageSource(holder(source, DRAGON_ICE), source, indirectEntityIn);
    }

    public static DamageSource causeDragonLightningDamage(Entity entity) {
        return new CustomEntityDamageSource(holder(entity, DRAGON_LIGHTNING), entity, entity);
    }

    public static DamageSource causeIndirectDragonLightningDamage(Entity source, @Nullable Entity indirectEntityIn) {
        return new CustomEntityDamageSource(holder(source, DRAGON_LIGHTNING), source, indirectEntityIn);
    }
}
