package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageUpdatePixieHouseModel implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageUpdatePixieHouseModel> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "update_pixie_house_model"));
    public static final StreamCodec<FriendlyByteBuf, MessageUpdatePixieHouseModel> CODEC = StreamCodec.of((buf, msg) -> MessageUpdatePixieHouseModel.write(msg, buf), MessageUpdatePixieHouseModel::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public long blockPos;
    public int houseType;

    public MessageUpdatePixieHouseModel(long blockPos, int houseType) {
        this.blockPos = blockPos;
        this.houseType = houseType;

    }

    public MessageUpdatePixieHouseModel() {
    }

    public static MessageUpdatePixieHouseModel read(FriendlyByteBuf buf) {
        return new MessageUpdatePixieHouseModel(buf.readLong(), buf.readInt());
    }

    public static void write(MessageUpdatePixieHouseModel message, FriendlyByteBuf buf) {
        buf.writeLong(message.blockPos);
        buf.writeInt(message.houseType);
    }


    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdatePixieHouseModel message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    BlockPos pos = BlockPos.of(message.blockPos);
                    if (player.level().getBlockEntity(pos) != null) {
                        if (player.level().getBlockEntity(pos) instanceof TileEntityPixieHouse) {
                            TileEntityPixieHouse house = (TileEntityPixieHouse) player.level().getBlockEntity(pos);
                            house.houseType = message.houseType;
                        }
                        if (player.level().getBlockEntity(pos) instanceof TileEntityJar) {
                            TileEntityJar jar = (TileEntityJar) player.level().getBlockEntity(pos);
                            jar.pixieType = message.houseType;
                        }
                    }
                }
            }
        }
    }
}