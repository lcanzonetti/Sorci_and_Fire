package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    public int getExpDrop(@NotNull BlockState state, @NotNull LevelAccessor level, @NotNull BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, @NotNull ItemStack tool) {
        // Silk touch is handled by the loot tables / vanilla ore logic in 1.21 through the enchantment component
        if (tool.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH)) > 0) {
            return 0;
        }
        return this.getExperience(level.getRandom());
    }

    protected int getExperience(RandomSource rand) {
        if (this == IafBlockRegistry.SAPPHIRE_ORE.get() || this == IafBlockRegistry.AMYTHEST_ORE.get()) {
            return Mth.nextInt(rand, 3, 7);
        }
        return 0;
    }
}
