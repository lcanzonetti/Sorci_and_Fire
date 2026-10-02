package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageUpdatePixieJar implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageUpdatePixieJar> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "update_pixie_jar"));
    public static final StreamCodec<FriendlyByteBuf, MessageUpdatePixieJar> CODEC = StreamCodec.of((buf, msg) -> MessageUpdatePixieJar.write(msg, buf), MessageUpdatePixieJar::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public long blockPos;
    public boolean isProducing;

    public MessageUpdatePixieJar(long blockPos, boolean isProducing) {
        this.blockPos = blockPos;
        this.isProducing = isProducing;

    }

    public MessageUpdatePixieJar() {
    }

    public static MessageUpdatePixieJar read(FriendlyByteBuf buf) {
        return new MessageUpdatePixieJar(buf.readLong(), buf.readBoolean());
    }

    public static void write(MessageUpdatePixieJar message, FriendlyByteBuf buf) {
        buf.writeLong(message.blockPos);
        buf.writeBoolean(message.isProducing);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdatePixieJar message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    BlockPos pos = BlockPos.of(message.blockPos);
                    if (player.level().getBlockEntity(pos) != null) {
                        if (player.level().getBlockEntity(pos) instanceof TileEntityJar) {
                            TileEntityJar jar = (TileEntityJar) player.level().getBlockEntity(pos);
                            jar.hasProduced = message.isProducing;
                        }
                    }
                }
            }
        }
    }
}