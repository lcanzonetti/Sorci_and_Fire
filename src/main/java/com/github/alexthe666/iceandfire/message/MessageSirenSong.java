package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageSirenSong implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSirenSong> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "siren_song"));
    public static final StreamCodec<FriendlyByteBuf, MessageSirenSong> CODEC = StreamCodec.of((buf, msg) -> MessageSirenSong.write(msg, buf), MessageSirenSong::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public int sirenId;
    public boolean isSinging;

    public MessageSirenSong(int sirenId, boolean isSinging) {
        this.sirenId = sirenId;
        this.isSinging = isSinging;
    }

    public MessageSirenSong() {
    }

    public static MessageSirenSong read(FriendlyByteBuf buf) {
        return new MessageSirenSong(buf.readInt(), buf.readBoolean());
    }

    public static void write(MessageSirenSong message, FriendlyByteBuf buf) {
        buf.writeInt(message.sirenId);
        buf.writeBoolean(message.isSinging);
    }


    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSirenSong message, IPayloadContext context) {
            Player player = context.player();
            if (context.flow() == PacketFlow.CLIENTBOUND) {
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.sirenId);
                if (entity != null && entity instanceof EntitySiren) {
                    EntitySiren siren = (EntitySiren) entity;
                    siren.setSinging(message.isSinging);
                }
            }
        }
    }

}