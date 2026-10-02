package com.github.alexthe666.iceandfire.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockFallingGeneric extends FallingBlock {
    public Item itemBlock;

    public BlockFallingGeneric(IafMaterial materialIn, float hardness, float resistance, SoundType sound) {
        super(
            materialIn.properties()
                .sound(sound)
                .strength(hardness, resistance)
        );
    }

    @SuppressWarnings("deprecation")
    public BlockFallingGeneric(IafMaterial materialIn, float hardness, float resistance, SoundType sound, boolean slippery) {
        super(
            materialIn.properties()
                .sound(sound)
                .strength(hardness, resistance)
                .friction(0.98F)
        );
    }


    public int getDustColor(BlockState blkst) {
        return -8356741;
    }
}
