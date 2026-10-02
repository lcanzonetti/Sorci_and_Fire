package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.util.RandomSource;


public class BlockIafOre extends Block {
    public Item itemBlock;

    public BlockIafOre(int toollevel, float hardness, float resistance) {
        super(
            IafMaterial.STONE.properties()
                
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
		);
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }

    protected int getExperience(RandomSource rand) {
        if (this == IafBlockRegistry.SAPPHIRE_ORE.get() || this == IafBlockRegistry.AMYTHEST_ORE.get()) {
            return Mth.nextInt(rand, 3, 7);
        }
        return 0;
    }
}
