package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Data component types of Ice and Fire. Most item data currently lives in {@code minecraft:custom_data}
 * (see {@link com.github.alexthe666.iceandfire.util.IafNbt}).
 */
public class IafDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, IceAndFire.MODID);
}
