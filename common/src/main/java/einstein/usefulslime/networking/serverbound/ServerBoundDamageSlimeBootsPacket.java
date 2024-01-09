package einstein.usefulslime.networking.serverbound;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class ServerBoundDamageSlimeBootsPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("damage_slime_boots");

    private final int damage;

    public ServerBoundDamageSlimeBootsPacket(int damage) {
        this.damage = damage;
    }

    public static ServerBoundDamageSlimeBootsPacket decode(FriendlyByteBuf buf) {
        return new ServerBoundDamageSlimeBootsPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(damage);
    }

    public static void handle(PacketContext<ServerBoundDamageSlimeBootsPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            ServerBoundDamageSlimeBootsPacket packet = context.message();
            UsefulSlime.damageEquipment(context.sender(), EquipmentSlot.FEET, packet.damage);
        }
    }
}
