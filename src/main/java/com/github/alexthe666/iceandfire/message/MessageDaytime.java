package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageDaytime implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageDaytime> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "daytime"));
    public static final StreamCodec<FriendlyByteBuf, MessageDaytime> CODEC = StreamCodec.of((buf, msg) -> MessageDaytime.write(msg, buf), MessageDaytime::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public int dragonId;
    public boolean isDay;

    public MessageDaytime(int dragonId, boolean isDay) {
        this.dragonId = dragonId;
        this.isDay = isDay;
    }

    public MessageDaytime() {
    }

    public static MessageDaytime read(FriendlyByteBuf buf) {
        return new MessageDaytime(buf.readInt(), buf.readBoolean());
    }

    public static void write(MessageDaytime message, FriendlyByteBuf buf) {
        buf.writeInt(message.dragonId);
        buf.writeBoolean(message.isDay);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageDaytime message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                Entity entity = player.level().getEntity(message.dragonId);
                if (entity instanceof EntityDragonBase) {
                    EntityDragonBase dragon = (EntityDragonBase) entity;
                    dragon.isDaytime = message.isDay;
                }
            }
        }
    }
}