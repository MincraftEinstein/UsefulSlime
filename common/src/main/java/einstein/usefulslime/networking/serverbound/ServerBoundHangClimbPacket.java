package einstein.usefulslime.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ServerBoundHangClimbPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("hang_climb");

    private final boolean canHangClimb;

    public ServerBoundHangClimbPacket(boolean canHangClimb) {
        this.canHangClimb = canHangClimb;
    }

    public static ServerBoundHangClimbPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundHangClimbPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(canHangClimb);
    }

    public static void handle(PacketContext<ServerBoundHangClimbPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            ServerBoundHangClimbPacket packet = context.message();
            ((ClimbingEntity) context.sender()).usefulSlime$setHangClimbing(packet.canHangClimb);
        }
    }
}
