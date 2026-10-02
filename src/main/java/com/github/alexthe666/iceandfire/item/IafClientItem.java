package com.github.alexthe666.iceandfire.item;

import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

/**
 * Items that provide client extensions (custom armor models, BEWLRs). NeoForge 21 removed
 * Item#initializeClient, so these are collected in {@code RegisterClientExtensionsEvent} instead.
 */
public interface IafClientItem {

    default void initializeClient(Consumer<IClientItemExtensions> consumer) {
    }
}
