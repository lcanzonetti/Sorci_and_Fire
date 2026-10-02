package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.config.biome.IafSpawnBiomeData;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.feature.*;
import com.github.alexthe666.iceandfire.world.gen.*;
import com.github.alexthe666.iceandfire.world.structure.DreadMausoleumStructure;
import com.github.alexthe666.iceandfire.world.structure.GorgonTempleStructure;
import com.github.alexthe666.iceandfire.world.structure.GraveyardStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.storage.LevelData;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.function.Supplier;

public class IafWorldRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, IceAndFire.MODID);
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, IceAndFire.MODID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FIRE_DRAGON_ROOST;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ICE_DRAGON_ROOST;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_ROOST;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> FIRE_DRAGON_CAVE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> ICE_DRAGON_CAVE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_CAVE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> CYCLOPS_CAVE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> PIXIE_VILLAGE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SIREN_ISLAND;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> HYDRA_CAVE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_DESERT;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_JUNGLE;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_DEATH_WORM;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_L;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_F;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_I;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_HIPPOCAMPUS;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_SEA_SERPENT;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_STYMPHALIAN_BIRD;
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> SPAWN_WANDERING_CYCLOPS;

    public static final DeferredHolder<StructureType<?>, StructureType<GorgonTempleStructure>> GORGON_TEMPLE = STRUCTURE_TYPES.register("gorgon_temple", () -> () -> GorgonTempleStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<DreadMausoleumStructure>> MAUSOLEUM = STRUCTURE_TYPES.register("mausoleum", () -> () -> DreadMausoleumStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<GraveyardStructure>> GRAVEYARD = STRUCTURE_TYPES.register("graveyard", () -> () -> GraveyardStructure.CODEC);

    // Structures themselves are defined in data/iceandfire/worldgen/structure
    public static final ResourceKey<Structure> GORGON_TEMPLE_CF = structure("gorgon_temple");
    public static final ResourceKey<Structure> MAUSOLEUM_CF = structure("mausoleum");
    public static final ResourceKey<Structure> GRAVEYARD_CF = structure("graveyard");

    // Placed features are defined in data/iceandfire/worldgen/placed_feature
    public static final ResourceKey<PlacedFeature> FIRE_LILY_CF = placed("fire_lily");
    public static final ResourceKey<PlacedFeature> FROST_LILY_CF = placed("frost_lily");
    public static final ResourceKey<PlacedFeature> LIGHTNING_LILY_CF = placed("lightning_lily");
    public static final ResourceKey<PlacedFeature> COPPER_ORE_CF = placed("copper_ore");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_CF = placed("silver_ore");
    public static final ResourceKey<PlacedFeature> SAPPHIRE_ORE_CF = placed("sapphire_ore");
    public static final ResourceKey<PlacedFeature> AMETHYST_ORE_CF = placed("amethyst_ore");
    public static final ResourceKey<PlacedFeature> FIRE_DRAGON_ROOST_CF = placed("fire_dragon_roost");
    public static final ResourceKey<PlacedFeature> ICE_DRAGON_ROOST_CF = placed("ice_dragon_roost");
    public static final ResourceKey<PlacedFeature> LIGHTNING_DRAGON_ROOST_CF = placed("lightning_dragon_roost");
    public static final ResourceKey<PlacedFeature> FIRE_DRAGON_CAVE_CF = placed("fire_dragon_cave");
    public static final ResourceKey<PlacedFeature> ICE_DRAGON_CAVE_CF = placed("ice_dragon_cave");
    public static final ResourceKey<PlacedFeature> LIGHTNING_DRAGON_CAVE_CF = placed("lightning_dragon_cave");
    public static final ResourceKey<PlacedFeature> CYCLOPS_CAVE_CF = placed("cyclops_cave");
    public static final ResourceKey<PlacedFeature> PIXIE_VILLAGE_CF = placed("pixie_village");
    public static final ResourceKey<PlacedFeature> SIREN_ISLAND_CF = placed("siren_island");
    public static final ResourceKey<PlacedFeature> HYDRA_CAVE_CF = placed("hydra_cave");
    public static final ResourceKey<PlacedFeature> MYRMEX_HIVE_DESERT_CF = placed("myrmex_hive_desert");
    public static final ResourceKey<PlacedFeature> MYRMEX_HIVE_JUNGLE_CF = placed("myrmex_hive_jungle");
    public static final ResourceKey<PlacedFeature> SPAWN_DEATH_WORM_CF = placed("spawn_death_worm");
    public static final ResourceKey<PlacedFeature> SPAWN_DRAGON_SKELETON_L_CF = placed("spawn_dragon_skeleton_l");
    public static final ResourceKey<PlacedFeature> SPAWN_DRAGON_SKELETON_F_CF = placed("spawn_dragon_skeleton_f");
    public static final ResourceKey<PlacedFeature> SPAWN_DRAGON_SKELETON_I_CF = placed("spawn_dragon_skeleton_i");
    public static final ResourceKey<PlacedFeature> SPAWN_HIPPOCAMPUS_CF = placed("spawn_hippocampus");
    public static final ResourceKey<PlacedFeature> SPAWN_SEA_SERPENT_CF = placed("spawn_sea_serpent");
    public static final ResourceKey<PlacedFeature> SPAWN_STYMPHALIAN_BIRD_CF = placed("spawn_stymphalian_bird");
    public static final ResourceKey<PlacedFeature> SPAWN_WANDERING_CYCLOPS_CF = placed("spawn_wandering_cyclops");

    static {
        FIRE_DRAGON_ROOST = register("fire_dragon_roost", () -> new WorldGenFireDragonRoosts(NoneFeatureConfiguration.CODEC));
        ICE_DRAGON_ROOST = register("ice_dragon_roost", () -> new WorldGenIceDragonRoosts(NoneFeatureConfiguration.CODEC));
        LIGHTNING_DRAGON_ROOST = register("lightning_dragon_roost",
                () -> new WorldGenLightningDragonRoosts(NoneFeatureConfiguration.CODEC));
        FIRE_DRAGON_CAVE = register("fire_dragon_cave", () -> new WorldGenFireDragonCave(NoneFeatureConfiguration.CODEC));
        ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> new WorldGenIceDragonCave(NoneFeatureConfiguration.CODEC));
        LIGHTNING_DRAGON_CAVE = register("lightning_dragon_cave",
                () -> new WorldGenLightningDragonCave(NoneFeatureConfiguration.CODEC));
        CYCLOPS_CAVE = register("cyclops_cave", () -> new WorldGenCyclopsCave(NoneFeatureConfiguration.CODEC));
        PIXIE_VILLAGE = register("pixie_village", () -> new WorldGenPixieVillage(NoneFeatureConfiguration.CODEC));
        SIREN_ISLAND = register("siren_island", () -> new WorldGenSirenIsland(NoneFeatureConfiguration.CODEC));
        HYDRA_CAVE = register("hydra_cave", () -> new WorldGenHydraCave(NoneFeatureConfiguration.CODEC));
        MYRMEX_HIVE_DESERT = register("myrmex_hive_desert",
                () -> new WorldGenMyrmexHive(false, false, NoneFeatureConfiguration.CODEC));
        MYRMEX_HIVE_JUNGLE = register("myrmex_hive_jungle",
                () -> new WorldGenMyrmexHive(false, true, NoneFeatureConfiguration.CODEC));

        SPAWN_DEATH_WORM = register("spawn_death_worm", () -> new SpawnDeathWorm(NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_L = register("spawn_dragon_skeleton_l",
                () -> new SpawnDragonSkeleton(IafEntityRegistry.LIGHTNING_DRAGON.get(), NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_F = register("spawn_dragon_skeleton_f",
                () -> new SpawnDragonSkeleton(IafEntityRegistry.FIRE_DRAGON.get(), NoneFeatureConfiguration.CODEC));
        SPAWN_DRAGON_SKELETON_I = register("spawn_dragon_skeleton_i",
                () -> new SpawnDragonSkeleton(IafEntityRegistry.ICE_DRAGON.get(), NoneFeatureConfiguration.CODEC));
        SPAWN_HIPPOCAMPUS = register("spawn_hippocampus", () -> new SpawnHippocampus(NoneFeatureConfiguration.CODEC));
        SPAWN_SEA_SERPENT = register("spawn_sea_serpent", () -> new SpawnSeaSerpent(NoneFeatureConfiguration.CODEC));
        SPAWN_STYMPHALIAN_BIRD = register("spawn_stymphalian_bird",
                () -> new SpawnStymphalianBird(NoneFeatureConfiguration.CODEC));
        SPAWN_WANDERING_CYCLOPS = register("spawn_wandering_cyclops",
                () -> new SpawnWanderingCyclops(NoneFeatureConfiguration.CODEC));
    }

    private static <F extends Feature<?>> DeferredHolder<Feature<?>, F> register(final String name, final Supplier<? extends F> supplier) {
        return FEATURES.register(name, supplier);
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, name));
    }

    private static ResourceKey<Structure> structure(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, name));
    }

    public static boolean isFarEnoughFromSpawn(final LevelAccessor level, final BlockPos position) {
        LevelData spawnPoint = level.getLevelData();
        BlockPos spawnRelative = new BlockPos(spawnPoint.getXSpawn(), position.getY(), spawnPoint.getYSpawn());
        return !spawnRelative.closerThan(position, IafConfig.dangerousWorldGenDistanceLimit);
    }

    public static boolean isFarEnoughFromDangerousGen(final ServerLevelAccessor level, final BlockPos position, final String id) {
        return isFarEnoughFromDangerousGen(level, position, id, IafWorldData.FeatureType.SURFACE);
    }

    public static boolean isFarEnoughFromDangerousGen(final ServerLevelAccessor level, final BlockPos position, final String id, final IafWorldData.FeatureType type) {
        IafWorldData data = IafWorldData.get(level.getLevel());
        return data.check(type, position, id);
    }

    public static HashMap<String, Boolean> LOADED_FEATURES;

    static {
        LOADED_FEATURES = new HashMap<String, Boolean>();
        LOADED_FEATURES.put("FIRE_LILY_CF", false);
        LOADED_FEATURES.put("FROST_LILY_CF", false);
        LOADED_FEATURES.put("LIGHTNING_LILY_CF", false);
        LOADED_FEATURES.put("COPPER_ORE_CF", false);
        LOADED_FEATURES.put("SILVER_ORE_CF", false);
        LOADED_FEATURES.put("SAPPHIRE_ORE_CF", false);
        LOADED_FEATURES.put("AMETHYST_ORE_CF", false);
        LOADED_FEATURES.put("FIRE_DRAGON_ROOST_CF", false);
        LOADED_FEATURES.put("ICE_DRAGON_ROOST_CF", false);
        LOADED_FEATURES.put("LIGHTNING_DRAGON_ROOST_CF", false);
        LOADED_FEATURES.put("FIRE_DRAGON_CAVE_CF", false);
        LOADED_FEATURES.put("ICE_DRAGON_CAVE_CF", false);
        LOADED_FEATURES.put("LIGHTNING_DRAGON_CAVE_CF", false);
        LOADED_FEATURES.put("CYCLOPS_CAVE_CF", false);
        LOADED_FEATURES.put("PIXIE_VILLAGE_CF", false);
        LOADED_FEATURES.put("SIREN_ISLAND_CF", false);
        LOADED_FEATURES.put("HYDRA_CAVE_CF", false);
        LOADED_FEATURES.put("MYRMEX_HIVE_DESERT_CF", false);
        LOADED_FEATURES.put("MYRMEX_HIVE_JUNGLE_CF", false);
        LOADED_FEATURES.put("SPAWN_DEATH_WORM_CF", false);
        LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_L_CF", false);
        LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_F_CF", false);
        LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_I_CF", false);
        LOADED_FEATURES.put("SPAWN_HIPPOCAMPUS_CF", false);
        LOADED_FEATURES.put("SPAWN_SEA_SERPENT_CF", false);
        LOADED_FEATURES.put("SPAWN_STYMPHALIAN_BIRD_CF", false);
        LOADED_FEATURES.put("SPAWN_WANDERING_CYCLOPS_CF", false);
    }

    /**
     * Adds the config-driven features to a biome, called from {@link IafBiomeModifiers.FeatureModifier}.
     */
    public static void addFeatures(Holder<Biome> biomeHolder, BiomeGenerationSettingsBuilder generator, HolderGetter<PlacedFeature> features) {
        if (safelyTestBiome(BiomeConfig.fireLilyBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, features.getOrThrow(FIRE_LILY_CF));
            LOADED_FEATURES.put("FIRE_LILY_CF", true);
        }
        if (safelyTestBiome(BiomeConfig.lightningLilyBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, features.getOrThrow(LIGHTNING_LILY_CF));
            LOADED_FEATURES.put("LIGHTNING_LILY_CF", true);
        }
        if (safelyTestBiome(BiomeConfig.iceLilyBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, features.getOrThrow(FROST_LILY_CF));
            LOADED_FEATURES.put("FROST_LILY_CF", true);
        }
        if (safelyTestBiome(BiomeConfig.oreGenBiomes, biomeHolder)) {
            if (IafConfig.generateSilverOre) {
                generator.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, features.getOrThrow(SILVER_ORE_CF));
                LOADED_FEATURES.put("SILVER_ORE_CF", true);
            }
            if (IafConfig.generateCopperOre) {
                generator.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, features.getOrThrow(COPPER_ORE_CF));
                LOADED_FEATURES.put("COPPER_ORE_CF", true);
            }
        }
        if (IafConfig.generateSapphireOre && safelyTestBiome(BiomeConfig.sapphireBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, features.getOrThrow(SAPPHIRE_ORE_CF));
            LOADED_FEATURES.put("SAPPHIRE_ORE_CF", true);
        }
        if (IafConfig.generateAmythestOre && safelyTestBiome(BiomeConfig.amethystBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, features.getOrThrow(AMETHYST_ORE_CF));
            LOADED_FEATURES.put("AMETHYST_ORE_CF", true);
        }

        if (IafConfig.generateDragonRoosts) {
            if (safelyTestBiome(BiomeConfig.fireDragonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(FIRE_DRAGON_ROOST_CF));
                LOADED_FEATURES.put("FIRE_DRAGON_ROOST_CF", true);

            }
            if (safelyTestBiome(BiomeConfig.lightningDragonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(LIGHTNING_DRAGON_ROOST_CF));
                LOADED_FEATURES.put("LIGHTNING_DRAGON_ROOST_CF", true);
            }
            if (safelyTestBiome(BiomeConfig.iceDragonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(ICE_DRAGON_ROOST_CF));
                LOADED_FEATURES.put("ICE_DRAGON_ROOST_CF", true);
            }
        }

        if (IafConfig.generateDragonDens) {
            if (safelyTestBiome(BiomeConfig.fireDragonCaveBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, features.getOrThrow(FIRE_DRAGON_CAVE_CF));
                LOADED_FEATURES.put("FIRE_DRAGON_CAVE_CF", true);
            }
            if (safelyTestBiome(BiomeConfig.lightningDragonCaveBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, features.getOrThrow(LIGHTNING_DRAGON_CAVE_CF));
                LOADED_FEATURES.put("LIGHTNING_DRAGON_CAVE_CF", true);
            }
            if (safelyTestBiome(BiomeConfig.iceDragonCaveBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, features.getOrThrow(ICE_DRAGON_CAVE_CF));
                LOADED_FEATURES.put("ICE_DRAGON_CAVE_CF", true);
            }
        }

        if (IafConfig.generateCyclopsCaves && safelyTestBiome(BiomeConfig.cyclopsCaveBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(CYCLOPS_CAVE_CF));
            LOADED_FEATURES.put("CYCLOPS_CAVE_CF", true);
        }
        if (IafConfig.spawnPixies && safelyTestBiome(BiomeConfig.pixieBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(PIXIE_VILLAGE_CF));
            LOADED_FEATURES.put("PIXIE_VILLAGE_CF", true);
        }
        if (IafConfig.generateHydraCaves && safelyTestBiome(BiomeConfig.hydraBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(HYDRA_CAVE_CF));
            LOADED_FEATURES.put("HYDRA_CAVE_CF", true);
        }
        if (IafConfig.generateMyrmexColonies && safelyTestBiome(BiomeConfig.desertMyrmexBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(MYRMEX_HIVE_DESERT_CF));
            LOADED_FEATURES.put("MYRMEX_HIVE_DESERT_CF", true);
        }
        if (IafConfig.generateMyrmexColonies && safelyTestBiome(BiomeConfig.jungleMyrmexBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(MYRMEX_HIVE_JUNGLE_CF));
            LOADED_FEATURES.put("MYRMEX_HIVE_JUNGLE_CF", true);
        }
        if (IafConfig.generateSirenIslands && safelyTestBiome(BiomeConfig.sirenBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SIREN_ISLAND_CF));
            LOADED_FEATURES.put("SIREN_ISLAND_CF", true);
        }
        if (IafConfig.spawnDeathWorm && safelyTestBiome(BiomeConfig.deathwormBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_DEATH_WORM_CF));
            LOADED_FEATURES.put("SPAWN_DEATH_WORM_CF", true);
        }
        if (IafConfig.generateWanderingCyclops && safelyTestBiome(BiomeConfig.wanderingCyclopsBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_WANDERING_CYCLOPS_CF));
            LOADED_FEATURES.put("SPAWN_WANDERING_CYCLOPS_CF", true);
        }
        if (IafConfig.generateDragonSkeletons) {
            if (safelyTestBiome(BiomeConfig.lightningDragonSkeletonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_DRAGON_SKELETON_L_CF));
                LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_L_CF", true);
            }
            if (safelyTestBiome(BiomeConfig.fireDragonSkeletonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_DRAGON_SKELETON_F_CF));
                LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_F_CF", true);
            }
            if (safelyTestBiome(BiomeConfig.iceDragonSkeletonBiomes, biomeHolder)) {
                generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_DRAGON_SKELETON_I_CF));
                LOADED_FEATURES.put("SPAWN_DRAGON_SKELETON_I_CF", true);
            }
        }
        if (IafConfig.spawnHippocampus && safelyTestBiome(BiomeConfig.hippocampusBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_HIPPOCAMPUS_CF));
            LOADED_FEATURES.put("SPAWN_HIPPOCAMPUS_CF", true);
        }
        if (IafConfig.spawnSeaSerpents && safelyTestBiome(BiomeConfig.seaSerpentBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_SEA_SERPENT_CF));
            LOADED_FEATURES.put("SPAWN_SEA_SERPENT_CF", true);
        }
        if (IafConfig.spawnStymphalianBirds && safelyTestBiome(BiomeConfig.stymphalianBiomes, biomeHolder)) {
            generator.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, features.getOrThrow(SPAWN_STYMPHALIAN_BIRD_CF));
            LOADED_FEATURES.put("SPAWN_STYMPHALIAN_BIRD_CF", true);
        }

    }

    private static boolean safelyTestBiome(Pair<String, IafSpawnBiomeData> entry, Holder<Biome> biomeHolder) {
        try {
            return BiomeConfig.test(entry, biomeHolder);
        } catch (Exception e) {
            return false;
        }
    }
}
