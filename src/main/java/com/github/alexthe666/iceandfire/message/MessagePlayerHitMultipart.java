package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessagePlayerHitMultipart implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessagePlayerHitMultipart> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "player_hit_multipart"));
    public static final StreamCodec<FriendlyByteBuf, MessagePlayerHitMultipart> CODEC = StreamCodec.of((buf, msg) -> MessagePlayerHitMultipart.write(msg, buf), MessagePlayerHitMultipart::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public int creatureID;
    public int extraData;

    public MessagePlayerHitMultipart(int creatureID) {
        this.creatureID = creatureID;
        this.extraData = 0;
    }

    public MessagePlayerHitMultipart(int creatureID, int extraData) {
        this.creatureID = creatureID;
        this.extraData = extraData;
    }

    public MessagePlayerHitMultipart() {
    }

    public static MessagePlayerHitMultipart read(FriendlyByteBuf buf) {
        return new MessagePlayerHitMultipart(buf.readInt(), buf.readInt());
    }

    public static void write(MessagePlayerHitMultipart message, FriendlyByteBuf buf) {
        buf.writeInt(message.creatureID);
        buf.writeInt(message.extraData);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessagePlayerHitMultipart message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    Entity entity = player.level().getEntity(message.creatureID);
                    if (entity != null && entity instanceof LivingEntity) {
                        double dist = player.distanceTo(entity);
                        LivingEntity mob = (LivingEntity) entity;
                        if (dist < 100) {
                            player.attack(mob);
                            if (mob instanceof EntityHydra) {
                                ((EntityHydra) mob).triggerHeadFlags(message.extraData);
                            }
                        }
                    }
                }
            }
        }
    }
}
