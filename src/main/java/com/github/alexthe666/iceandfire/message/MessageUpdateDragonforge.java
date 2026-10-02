package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageUpdateDragonforge implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageUpdateDragonforge> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "update_dragonforge"));
    public static final StreamCodec<FriendlyByteBuf, MessageUpdateDragonforge> CODEC = StreamCodec.of((buf, msg) -> MessageUpdateDragonforge.write(msg, buf), MessageUpdateDragonforge::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public long blockPos;
    public int cookTime;

    public MessageUpdateDragonforge(long blockPos, int houseType) {
        this.blockPos = blockPos;
        this.cookTime = houseType;

    }

    public MessageUpdateDragonforge() {
    }

    public static MessageUpdateDragonforge read(FriendlyByteBuf buf) {
        return new MessageUpdateDragonforge(buf.readLong(), buf.readInt());
    }

    public static void write(MessageUpdateDragonforge message, FriendlyByteBuf buf) {
        buf.writeLong(message.blockPos);
        buf.writeInt(message.cookTime);
    }


    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdateDragonforge message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    BlockPos pos = BlockPos.of(message.blockPos);
                    if (player.level().getBlockEntity(pos) != null) {
                        if (player.level().getBlockEntity(pos) instanceof TileEntityDragonforge) {
                            TileEntityDragonforge house = (TileEntityDragonforge) player.level().getBlockEntity(pos);
                            house.cookTime = message.cookTime;
                            if (message.cookTime > 0) {
                                house.lastDragonFlameTimer = 40;
                            }
                        }
                    }
                }
            }
        }
    }
}