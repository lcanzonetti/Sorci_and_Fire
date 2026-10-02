package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Block tags that replace the block {@code Material} checks removed in 1.20.
 * Their contents live in data/iceandfire/tags/block/material and mirror the old vanilla materials.
 */
public class IafBlockTags {
    public static final TagKey<Block> MATERIAL_STONE = material("stone");
    public static final TagKey<Block> MATERIAL_DIRT = material("dirt");
    public static final TagKey<Block> MATERIAL_GRASS = material("grass");
    public static final TagKey<Block> MATERIAL_SAND = material("sand");
    public static final TagKey<Block> MATERIAL_WOOD = material("wood");
    public static final TagKey<Block> MATERIAL_PLANT = material("plant");
    public static final TagKey<Block> MATERIAL_ICE = material("ice");

    private static TagKey<Block> material(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "material/" + name));
    }
}
