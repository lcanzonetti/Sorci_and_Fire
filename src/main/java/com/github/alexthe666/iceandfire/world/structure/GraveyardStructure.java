package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.config.biome.IafSpawnBiomeData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class GraveyardStructure extends IafJigsawStructure {

    public static final MapCodec<GraveyardStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> jigsawCodec(instance).apply(instance, GraveyardStructure::new));

    public GraveyardStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth) {
        super(settings, startPool, maxDepth);
    }

    @Override
    protected boolean isEnabled() {
        return IafConfig.generateGraveyards;
    }

    @Override
    protected Pair<String, IafSpawnBiomeData> biomeConfig() {
        return BiomeConfig.graveyardBiomes;
    }

    @Override
    protected int heightOffset() {
        return 1;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return IafWorldRegistry.GRAVEYARD.get();
    }
}
