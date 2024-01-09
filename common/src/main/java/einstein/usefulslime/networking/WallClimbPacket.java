package einstein.usefulslime.networking;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class WallClimbPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("wall_climb");

    private final boolean canWallClimb;

    public WallClimbPacket(boolean canWallClimb) {
        this.canWallClimb = canWallClimb;
    }

    public static WallClimbPacket decode(FriendlyByteBuf buf) {
        return new WallClimbPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(canWallClimb);
    }

    public static void handle(PacketContext<WallClimbPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            WallClimbPacket packet = context.message();
            ((ClimbingEntity) context.sender()).usefulSlime$setWallClimbing(packet.canWallClimb);
        }
    }
}
