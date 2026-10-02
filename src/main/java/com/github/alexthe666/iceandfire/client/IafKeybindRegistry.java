package com.github.alexthe666.iceandfire.client;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = IceAndFire.MODID)
public class IafKeybindRegistry {
    public static final KeyMapping dragon_fireAttack = new KeyMapping("key.dragon_fireAttack", 82, "key.categories.gameplay");
    public static final KeyMapping dragon_strike = new KeyMapping("key.dragon_strike", 71, "key.categories.gameplay");
    public static final KeyMapping dragon_down = new KeyMapping("key.dragon_down", 88, "key.categories.gameplay");
    public static final KeyMapping dragon_change_view = new KeyMapping("key.dragon_change_view", 296, "key.categories.misc");

    public static void init() {
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(dragon_fireAttack);
        event.register(dragon_strike);
        event.register(dragon_down);
        event.register(dragon_change_view);
    }
}
