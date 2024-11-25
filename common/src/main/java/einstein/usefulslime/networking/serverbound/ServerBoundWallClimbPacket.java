package einstein.usefulslime.networking.serverbound;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerBoundWallClimbPacket(boolean canWallClimb) implements CustomPacketPayload {

    public static final Type<ServerBoundWallClimbPacket> TYPE = new Type<>(UsefulSlime.loc("wall_climb"));
    public static final StreamCodec<FriendlyByteBuf, ServerBoundWallClimbPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerBoundWallClimbPacket::canWallClimb,
            ServerBoundWallClimbPacket::new
    );

    public static void handle(ServerBoundWallClimbPacket packet, ServerPlayNetworkContext context) {
        if (context.networkSide().equals(PacketFlow.CLIENTBOUND)) {
            ((ClimbingEntity) context.player()).usefulSlime$setWallClimbing(packet.canWallClimb);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
