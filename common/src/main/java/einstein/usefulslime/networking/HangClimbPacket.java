package einstein.usefulslime.networking;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class HangClimbPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("hang_climb");

    private final boolean canHangClimb;

    public HangClimbPacket(boolean canHangClimb) {
        this.canHangClimb = canHangClimb;
    }

    public static HangClimbPacket decode(FriendlyByteBuf buf) {
        return new HangClimbPacket(buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(canHangClimb);
    }

    public static void handle(PacketContext<HangClimbPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            HangClimbPacket packet = context.message();
            ((ClimbingEntity) context.sender()).usefulSlime$setHangClimbing(packet.canHangClimb);
        }
    }
}
