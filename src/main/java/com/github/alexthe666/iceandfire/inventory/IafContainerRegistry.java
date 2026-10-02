package com.github.alexthe666.iceandfire.inventory;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.core.registries.Registries;
import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public class IafContainerRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister
        .create(Registries.MENU, IceAndFire.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ContainerLectern>> IAF_LECTERN_CONTAINER = register(
        () -> new MenuType<>(ContainerLectern::new, FeatureFlags.DEFAULT_FLAGS), "iaf_lectern");
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerPodium>> PODIUM_CONTAINER = register(
        () -> new MenuType<>(ContainerPodium::new, FeatureFlags.DEFAULT_FLAGS), "podium");
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerDragon>> DRAGON_CONTAINER = register(
        () -> new MenuType<>(ContainerDragon::new, FeatureFlags.DEFAULT_FLAGS), "dragon");
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerHippogryph>> HIPPOGRYPH_CONTAINER = register(
        () -> new MenuType<>(ContainerHippogryph::new, FeatureFlags.DEFAULT_FLAGS), "hippogryph");
    public static final DeferredHolder<MenuType<?>, MenuType<HippocampusContainerMenu>> HIPPOCAMPUS_CONTAINER = register(
        () -> new MenuType<>(HippocampusContainerMenu::new, FeatureFlags.DEFAULT_FLAGS), "hippocampus");
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerDragonForge>> DRAGON_FORGE_CONTAINER = register(
        () -> new MenuType<>(ContainerDragonForge::new, FeatureFlags.DEFAULT_FLAGS), "dragon_forge");

    public static <C extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<C>> register(Supplier<MenuType<C>> type,
                                                                                         String name) {
        return CONTAINERS.register(name, type);
    }

}
