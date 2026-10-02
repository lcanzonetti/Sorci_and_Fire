package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageHippogryphArmor implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageHippogryphArmor> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "hippogryph_armor"));
    public static final StreamCodec<FriendlyByteBuf, MessageHippogryphArmor> CODEC = StreamCodec.of((buf, msg) -> MessageHippogryphArmor.write(msg, buf), MessageHippogryphArmor::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public int dragonId;
    public int slot_index;
    public int armor_type;

    public MessageHippogryphArmor(int dragonId, int slot_index, int armor_type) {
        this.dragonId = dragonId;
        this.slot_index = slot_index;
        this.armor_type = armor_type;
    }

    public MessageHippogryphArmor() {
    }

    public static MessageHippogryphArmor read(FriendlyByteBuf buf) {
        return new MessageHippogryphArmor(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MessageHippogryphArmor message, FriendlyByteBuf buf) {
        buf.writeInt(message.dragonId);
        buf.writeInt(message.slot_index);
        buf.writeInt(message.armor_type);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageHippogryphArmor message, IPayloadContext context) {
            Player player = context.player();
            if (player != null) {
                if (player.level() != null) {
                    Entity entity = player.level().getEntity(message.dragonId);
                    if (entity != null && entity instanceof EntityHippogryph) {
                        EntityHippogryph hippo = (EntityHippogryph) entity;
                        if (message.slot_index == 0) {
                            hippo.setSaddled(message.armor_type == 1);
                        }
                        if (message.slot_index == 1) {
                            hippo.setChested(message.armor_type == 1);
                        }
                        if (message.slot_index == 2) {
                            hippo.setArmor(message.armor_type);
                        }
                    }
                    if (entity != null && entity instanceof EntityHippocampus) {
                        EntityHippocampus hippo = (EntityHippocampus) entity;
                        if (message.slot_index == 0) {
                            hippo.setSaddled(message.armor_type == 1);
                        }
                        if (message.slot_index == 1) {
                            hippo.setChested(message.armor_type == 1);
                        }
                        if (message.slot_index == 2) {
                            hippo.setArmor(message.armor_type);
                        }
                    }
                }
            }
        }
    }
}