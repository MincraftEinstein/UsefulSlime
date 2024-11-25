package einstein.usefulslime.networking.serverbound;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerBoundHangClimbPacket(boolean canHangClimb) implements CustomPacketPayload {

    public static final Type<ServerBoundHangClimbPacket> TYPE = new Type<>(UsefulSlime.loc("hang_climb"));
    public static final StreamCodec<FriendlyByteBuf, ServerBoundHangClimbPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerBoundHangClimbPacket::canHangClimb,
            ServerBoundHangClimbPacket::new
    );

    public static void handle(ServerBoundHangClimbPacket packet, ServerPlayNetworkContext context) {
        if (context.networkSide().equals(PacketFlow.CLIENTBOUND)) {
            ((ClimbingEntity) context.player()).usefulSlime$setHangClimbing(packet.canHangClimb);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
