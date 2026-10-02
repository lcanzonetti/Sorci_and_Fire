package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class IafBiomeModifiers {

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, IceAndFire.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<FeatureModifier>> CONFIG_DRIVEN = BIOME_MODIFIER_SERIALIZERS.register("config_driven", () ->
        RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryOps.retrieveGetter(Registries.PLACED_FEATURE)).apply(instance, FeatureModifier::new)));

    /**
     * Adds features and spawns according to the Ice and Fire server config and biome config files
     * (data/iceandfire/neoforge/biome_modifier/config_driven.json).
     */
    public record FeatureModifier(HolderGetter<PlacedFeature> features) implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                IafWorldRegistry.addFeatures(biome, builder.getGenerationSettings(), features);
                IafEntityRegistry.addSpawners(biome, builder.getMobSpawnSettings());
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CONFIG_DRIVEN.get();
        }
    }
}
