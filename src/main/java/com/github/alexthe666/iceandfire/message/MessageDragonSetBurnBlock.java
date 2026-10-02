package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageDragonSetBurnBlock implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageDragonSetBurnBlock> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "dragon_set_burn_block"));
    public static final StreamCodec<FriendlyByteBuf, MessageDragonSetBurnBlock> CODEC = StreamCodec.of((buf, msg) -> MessageDragonSetBurnBlock.write(msg, buf), MessageDragonSetBurnBlock::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public int dragonId;
    public boolean breathingFire;
    public int posX;
    public int posY;
    public int posZ;

    public MessageDragonSetBurnBlock(int dragonId, boolean breathingFire, BlockPos pos) {
        this.dragonId = dragonId;
        this.breathingFire = breathingFire;
        posX = pos.getX();
        posY = pos.getY();
        posZ = pos.getZ();
    }

    public MessageDragonSetBurnBlock() {
    }


    public static MessageDragonSetBurnBlock read(FriendlyByteBuf buf) {
        return new MessageDragonSetBurnBlock(buf.readInt(), buf.readBoolean(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }

    public static void write(MessageDragonSetBurnBlock message, FriendlyByteBuf buf) {
        buf.writeInt(message.dragonId);
        buf.writeBoolean(message.breathingFire);
        buf.writeInt(message.posX);
        buf.writeInt(message.posY);
        buf.writeInt(message.posZ);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageDragonSetBurnBlock message, IPayloadContext context) {
            Player player = context.player();
            if(context.flow() == PacketFlow.CLIENTBOUND){
                player = IceAndFire.PROXY.getClientSidePlayer();
            }
            if (player != null) {
                if (player.level() != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.dragonId);
                        if (entity != null && entity instanceof EntityDragonBase) {
                            EntityDragonBase dragon = (EntityDragonBase) entity;
                            dragon.setBreathingFire(message.breathingFire);
                            dragon.burningTarget = new BlockPos(message.posX, message.posY, message.posZ);
                        }
                    }
                }
            }
        }
    }
}