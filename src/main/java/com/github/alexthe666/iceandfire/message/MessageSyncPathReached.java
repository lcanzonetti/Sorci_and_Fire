package com.github.alexthe666.iceandfire.message;

import org.jetbrains.annotations.NotNull;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Message to sync the reached positions over to the client for rendering.
 */
public class MessageSyncPathReached implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MessageSyncPathReached> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "sync_path_reached"));
    public static final StreamCodec<FriendlyByteBuf, MessageSyncPathReached> CODEC = StreamCodec.of((buf, msg) -> msg.write(buf), MessageSyncPathReached::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Set of reached positions.
     */
    public Set<BlockPos> reached = new HashSet<>();

    /**
     * Create the message to send a set of positions over to the client side.
     *
     */
    public MessageSyncPathReached(final Set<BlockPos> reached)
    {
        super();
        this.reached = reached;
    }

    public void write(final FriendlyByteBuf buf) {
        buf.writeInt(reached.size());
        for (final BlockPos node : reached) {
            buf.writeBlockPos(node);
        }

    }

    public static MessageSyncPathReached read(final FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<BlockPos> reached = new HashSet<>();
        for (int i = 0; i < size; i++) {
            reached.add(buf.readBlockPos());
        }
        return new MessageSyncPathReached(reached);
    }

    public LogicalSide getExecutionSide()
    {
        return LogicalSide.CLIENT;
    }

    public boolean handle(IPayloadContext context) {
        context.enqueueWork(() -> {

            if (context.flow() == PacketFlow.CLIENTBOUND) {
                for (final MNode node : Pathfinding.lastDebugNodesPath) {
                    if (reached.contains(node.pos)) {
                        node.setReachedByWorker(true);
                    }
                }
            }

        });
        return true;
    }

}
