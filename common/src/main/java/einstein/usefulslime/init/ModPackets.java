package einstein.usefulslime.init;

import commonnetwork.api.Network;
import einstein.usefulslime.networking.HangClimbPacket;
import einstein.usefulslime.networking.WallClimbPacket;

public class ModPackets {

    public static void init() {
        Network.registerPacket(HangClimbPacket.CHANNEL, HangClimbPacket.class, HangClimbPacket::encode, HangClimbPacket::decode, HangClimbPacket::handle);
        Network.registerPacket(WallClimbPacket.CHANNEL, WallClimbPacket.class, WallClimbPacket::encode, WallClimbPacket::decode, WallClimbPacket::handle);
    }
}
