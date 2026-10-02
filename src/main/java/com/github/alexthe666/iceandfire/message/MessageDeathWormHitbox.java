package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;


public class MessageDeathWormHitbox implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageDeathWormHitbox> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "death_worm_hitbox"));
    public static final StreamCodec<FriendlyByteBuf, MessageDeathWormHitbox> CODEC = StreamCodec.of((buf, msg) -> MessageDeathWormHitbox.write(msg, buf), MessageDeathWormHitbox::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public int deathWormId;
    public float scale;

    public MessageDeathWormHitbox(int deathWormId, float scale) {
        this.deathWormId = deathWormId;
        this.scale = scale;
    }

    public MessageDeathWormHitbox() {
    }

    public static MessageDeathWormHitbox read(FriendlyByteBuf buf) {
        return new MessageDeathWormHitbox(buf.readInt(), buf.readFloat());
    }

    public static void write(MessageDeathWormHitbox message, FriendlyByteBuf buf) {
        buf.writeInt(message.deathWormId);
        buf.writeFloat(message.scale);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageDeathWormHitbox message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    Entity entity = player.level().getEntity(message.deathWormId);
                    if (entity != null && entity instanceof EntityDeathWorm) {
                        EntityDeathWorm worm = (EntityDeathWorm) entity;
                        worm.initSegments(message.scale);
                    }
                }
            }
        }
    }
}