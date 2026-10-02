package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.tile.IceAndFireTEISR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;

import java.util.function.Consumer;

public class BlockItemWithRender extends BlockItem {
    public BlockItemWithRender(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            static final Supplier<BlockEntityWithoutLevelRenderer> renderer = Suppliers.memoize(() -> new IceAndFireTEISR(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        });
    }

}
