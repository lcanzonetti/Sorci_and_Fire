package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.inventory.IafContainerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class IafGuiRegistry {

    public static void register() {
        MenuScreens.register(IafContainerRegistry.IAF_LECTERN_CONTAINER.get(), () -> new GuiLectern());
        MenuScreens.register(IafContainerRegistry.PODIUM_CONTAINER.get(), () -> new GuiPodium());
        MenuScreens.register(IafContainerRegistry.DRAGON_CONTAINER.get(), () -> new GuiDragon());
        MenuScreens.register(IafContainerRegistry.HIPPOGRYPH_CONTAINER.get(), () -> new GuiHippogryph());
        MenuScreens.register(IafContainerRegistry.HIPPOCAMPUS_CONTAINER.get(), () -> new GuiHippocampus());
        MenuScreens.register(IafContainerRegistry.DRAGON_FORGE_CONTAINER.get(), () -> new GuiDragonForge());
    }
}
