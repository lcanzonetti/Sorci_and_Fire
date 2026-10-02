package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageSetMyrmexHiveNull implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSetMyrmexHiveNull> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "set_myrmex_hive_null"));
    public static final StreamCodec<FriendlyByteBuf, MessageSetMyrmexHiveNull> CODEC = StreamCodec.of((buf, msg) -> MessageSetMyrmexHiveNull.write(msg, buf), MessageSetMyrmexHiveNull::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public MessageSetMyrmexHiveNull() {
    }

    public static MessageSetMyrmexHiveNull read(FriendlyByteBuf buf) {
        return new MessageSetMyrmexHiveNull();
    }

    public static void write(MessageSetMyrmexHiveNull message, FriendlyByteBuf buf) {
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSetMyrmexHiveNull message, IPayloadContext context) {
            Player player = context.player();
            if (player != null) {
                IceAndFire.PROXY.setReferencedHive(null);
            }
        }
    }
}