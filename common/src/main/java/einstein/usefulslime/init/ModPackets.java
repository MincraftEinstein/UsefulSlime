package einstein.usefulslime.init;

import einstein.usefulslime.networking.serverbound.ServerBoundDamageSlimeBootsPacket;
import einstein.usefulslime.networking.serverbound.ServerBoundHangClimbPacket;
import einstein.usefulslime.networking.serverbound.ServerBoundWallClimbPacket;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.networking.api.C2SPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ModPackets {

    public static void init() {
        registerC2S(ServerBoundHangClimbPacket.TYPE, ServerBoundHangClimbPacket.STREAM_CODEC, ServerBoundHangClimbPacket::handle);
        registerC2S(ServerBoundWallClimbPacket.TYPE, ServerBoundWallClimbPacket.STREAM_CODEC, ServerBoundWallClimbPacket::handle);
        registerC2S(ServerBoundDamageSlimeBootsPacket.TYPE, ServerBoundDamageSlimeBootsPacket.STREAM_CODEC, ServerBoundDamageSlimeBootsPacket::handle);
    }

    private static <T extends CustomPacketPayload> void registerC2S(CustomPacketPayload.Type<T> type, StreamCodec<FriendlyByteBuf, T> codec, C2SPayloadHandler<T> handler) {
        ConfigApiJava.network().registerC2S(type, codec, handler);
    }
}
