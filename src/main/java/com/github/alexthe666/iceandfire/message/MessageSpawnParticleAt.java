package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageSpawnParticleAt implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSpawnParticleAt> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "spawn_particle_at"));
    public static final StreamCodec<FriendlyByteBuf, MessageSpawnParticleAt> CODEC = StreamCodec.of((buf, msg) -> MessageSpawnParticleAt.write(msg, buf), MessageSpawnParticleAt::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private double x;
    private double y;
    private double z;
    private int particleType;

    public MessageSpawnParticleAt() {
    }

    public MessageSpawnParticleAt(double x, double y, double z, int particleType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.particleType = particleType;
    }

    public static MessageSpawnParticleAt read(FriendlyByteBuf buf) {
        return new MessageSpawnParticleAt(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
    }

    public static void write(MessageSpawnParticleAt message, FriendlyByteBuf buf) {
        buf.writeDouble(message.x);
        buf.writeDouble(message.y);
        buf.writeDouble(message.z);
        buf.writeInt(message.particleType);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSpawnParticleAt message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == IafItemRegistry.DRAGON_DEBUG_STICK.get()) {
                    player.level().addParticle(ParticleTypes.SMOKE, message.x, message.y, message.z, 0, 0, 0);
                }
            }
        }
    }
}
