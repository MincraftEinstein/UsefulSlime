package einstein.usefulslime.networking;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import einstein.usefulslime.UsefulSlime;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class DamageSlimeBootsPacket {

    public static final ResourceLocation CHANNEL = UsefulSlime.loc("damage_slime_boots");

    private final int damage;

    public DamageSlimeBootsPacket(int damage) {
        this.damage = damage;
    }

    public static DamageSlimeBootsPacket decode(FriendlyByteBuf buf) {
        return new DamageSlimeBootsPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(damage);
    }

    public static void handle(PacketContext<DamageSlimeBootsPacket> context) {
        if (context.side().equals(Side.SERVER)) {
            DamageSlimeBootsPacket packet = context.message();
            UsefulSlime.damageEquipment(context.sender(), EquipmentSlot.FEET, packet.damage);
        }
    }
}
