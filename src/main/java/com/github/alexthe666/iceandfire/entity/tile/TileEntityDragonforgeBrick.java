package com.github.alexthe666.iceandfire.entity.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TileEntityDragonforgeBrick extends BlockEntity {

    public TileEntityDragonforgeBrick(BlockPos pos, BlockState state) {
        super(IafTileEntityRegistry.DRAGONFORGE_BRICK.get(), pos, state);
    }


    @Nullable
    public TileEntityDragonforge getConnectedTileEntity() {
        for (Direction facing : Direction.values()) {
            if (level.getBlockEntity(worldPosition.relative(facing)) instanceof TileEntityDragonforge) {
                return (TileEntityDragonforge) level.getBlockEntity(worldPosition.relative(facing));
            }
        }
        return null;
    }

}
