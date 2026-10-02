package com.github.alexthe666.iceandfire.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.UnaryOperator;

/**
 * Stand-in for the block {@code Material} class removed in 1.20: each constant applies the
 * properties the old material implied (map color, flammability, replaceability, push reaction...).
 */
public enum IafMaterial {
    STONE(p -> p.mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)),
    METAL(p -> p.mapColor(MapColor.METAL)),
    WOOD(p -> p.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()),
    DIRT(p -> p.mapColor(MapColor.DIRT)),
    GRASS(p -> p.mapColor(MapColor.GRASS)),
    SAND(p -> p.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE)),
    CLAY(p -> p.mapColor(MapColor.CLAY)),
    GLASS(p -> p.mapColor(MapColor.NONE).instrument(NoteBlockInstrument.HAT)),
    ICE(p -> p.mapColor(MapColor.ICE)),
    ICE_SOLID(p -> p.mapColor(MapColor.ICE)),
    PLANT(p -> p.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY)),
    REPLACEABLE_PLANT(p -> p.mapColor(MapColor.PLANT).replaceable().ignitedByLava().pushReaction(PushReaction.DESTROY)),
    PORTAL(p -> p.mapColor(MapColor.NONE).pushReaction(PushReaction.BLOCK));

    private final UnaryOperator<BlockBehaviour.Properties> setup;

    IafMaterial(UnaryOperator<BlockBehaviour.Properties> setup) {
        this.setup = setup;
    }

    public BlockBehaviour.Properties properties() {
        return setup.apply(BlockBehaviour.Properties.of());
    }

    public SoundType defaultSound() {
        return switch (this) {
            case WOOD -> SoundType.WOOD;
            case DIRT, CLAY -> SoundType.GRAVEL;
            case GRASS, PLANT, REPLACEABLE_PLANT -> SoundType.GRASS;
            case SAND -> SoundType.SAND;
            case GLASS, ICE, ICE_SOLID, PORTAL -> SoundType.GLASS;
            case METAL -> SoundType.METAL;
            default -> SoundType.STONE;
        };
    }
}
