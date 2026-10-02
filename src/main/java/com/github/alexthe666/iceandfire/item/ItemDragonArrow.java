package com.github.alexthe666.iceandfire.item;

import javax.annotation.Nullable;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonArrow;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDragonArrow extends ArrowItem {
    public ItemDragonArrow() {
        super(new Properties());
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter, @Nullable ItemStack weapon) {
        EntityDragonArrow arrowentity = new EntityDragonArrow(IafEntityRegistry.DRAGON_ARROW.get(), shooter, worldIn);
        return arrowentity;
    }
}
