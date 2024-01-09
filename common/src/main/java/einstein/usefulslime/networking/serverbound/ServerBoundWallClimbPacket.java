package einstein.usefulslime.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ServerBoundWallClimbPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("wall_climb");

    private final boolean canWallClimb;

    public ServerBoundWallClimbPacket(boolean canWallClimb) {
        this.canWallClimb = canWallClimb;
    }

    public static ServerBoundWallClimbPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundWallClimbPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(canWallClimb);
    }

    public static void handle(PacketContext<ServerBoundWallClimbPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            ServerBoundWallClimbPacket packet = context.message();
            ((ClimbingEntity) context.sender()).usefulSlime$setWallClimbing(packet.canWallClimb);
        }
    }
}
