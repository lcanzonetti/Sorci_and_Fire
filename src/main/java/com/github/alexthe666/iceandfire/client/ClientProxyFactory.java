package com.github.alexthe666.iceandfire.client;

import com.github.alexthe666.iceandfire.CommonProxy;

/**
 * Keeps the reference to {@link ClientProxy} out of common code so dedicated servers never load it.
 */
public class ClientProxyFactory {
    public static CommonProxy create() {
        return new ClientProxy();
    }
}
