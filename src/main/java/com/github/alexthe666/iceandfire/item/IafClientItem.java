package com.github.alexthe666.iceandfire.item;

import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

/**
 * Marker for items that provide client extensions (custom armor models, BEWLRs). The method matches
 * Item#initializeClient, which NeoForge still calls for every registered item.
 */
public interface IafClientItem {

    default void initializeClient(Consumer<IClientItemExtensions> consumer) {
    }
}
