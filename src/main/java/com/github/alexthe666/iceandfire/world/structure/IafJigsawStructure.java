package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.config.biome.IafSpawnBiomeData;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * Shared logic of the Ice and Fire jigsaw structures: placed on the lowest corner of a 5x5 footprint,
 * gated by the server config and the biome config files.
 */
public abstract class IafJigsawStructure extends Structure {

    protected final Holder<StructureTemplatePool> startPool;
    protected final int maxDepth;

    protected IafJigsawStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth) {
        super(settings);
        this.startPool = startPool;
        this.maxDepth = maxDepth;
    }

    protected static <S extends IafJigsawStructure> Products.P3<RecordCodecBuilder.Mu<S>, StructureSettings, Holder<StructureTemplatePool>, Integer> jigsawCodec(RecordCodecBuilder.Instance<S> instance) {
        return instance.group(
            settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            Codec.intRange(0, 20).fieldOf("max_depth").forGetter(structure -> structure.maxDepth)
        );
    }

    protected abstract boolean isEnabled();

    protected abstract Pair<String, IafSpawnBiomeData> biomeConfig();

    protected int heightOffset() {
        return 1;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (!isEnabled()) {
            return Optional.empty();
        }
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        ChunkPos pos = context.chunkPos();
        LevelHeightAccessor height = context.heightAccessor();
        Rotation rotation = Rotation.getRandom(context.random());
        int xOffset = 5;
        int zOffset = 5;
        if (rotation == Rotation.CLOCKWISE_90) {
            xOffset = -5;
        } else if (rotation == Rotation.CLOCKWISE_180) {
            xOffset = -5;
            zOffset = -5;
        } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            zOffset = -5;
        }

        int x = pos.getMiddleBlockX();
        int z = pos.getMiddleBlockZ();
        int y1 = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, height, context.randomState());
        int y2 = chunkGenerator.getFirstOccupiedHeight(x, z + zOffset, Heightmap.Types.WORLD_SURFACE_WG, height, context.randomState());
        int y3 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z, Heightmap.Types.WORLD_SURFACE_WG, height, context.randomState());
        int y4 = chunkGenerator.getFirstOccupiedHeight(x + xOffset, z + zOffset, Heightmap.Types.WORLD_SURFACE_WG, height, context.randomState());
        int yMin = Math.min(Math.min(y1, y2), Math.min(y3, y4));
        BlockPos blockpos = pos.getMiddleBlockPosition(yMin + heightOffset());

        Holder<Biome> biome = context.biomeSource().getNoiseBiome(QuartPos.fromBlock(blockpos.getX()), QuartPos.fromBlock(blockpos.getY()), QuartPos.fromBlock(blockpos.getZ()), context.randomState().sampler());
        if (!BiomeConfig.test(biomeConfig(), biome)) {
            return Optional.empty();
        }

        return JigsawPlacement.addPieces(context, this.startPool, Optional.empty(), this.maxDepth, blockpos, false, Optional.empty(), 128,
            PoolAliasLookup.EMPTY, DimensionPadding.ZERO, LiquidSettings.APPLY_WATERLOGGING);
    }
}
