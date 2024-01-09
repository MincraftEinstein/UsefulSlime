package einstein.usefulslime.init;

import commonnetwork.api.Network;
import einstein.usefulslime.networking.serverbound.ServerBoundDamageSlimeBootsPacket;
import einstein.usefulslime.networking.serverbound.ServerBoundHangClimbPacket;
import einstein.usefulslime.networking.serverbound.ServerBoundWallClimbPacket;

public class ModPackets {

    public static void init() {
        Network.registerPacket(ServerBoundHangClimbPacket.CHANNEL, ServerBoundHangClimbPacket.class, ServerBoundHangClimbPacket::encode, ServerBoundHangClimbPacket::decode, ServerBoundHangClimbPacket::handle);
        Network.registerPacket(ServerBoundWallClimbPacket.CHANNEL, ServerBoundWallClimbPacket.class, ServerBoundWallClimbPacket::encode, ServerBoundWallClimbPacket::decode, ServerBoundWallClimbPacket::handle);
        Network.registerPacket(ServerBoundDamageSlimeBootsPacket.CHANNEL, ServerBoundDamageSlimeBootsPacket.class, ServerBoundDamageSlimeBootsPacket::encode, ServerBoundDamageSlimeBootsPacket::decode, ServerBoundDamageSlimeBootsPacket::handle);
    }
}
