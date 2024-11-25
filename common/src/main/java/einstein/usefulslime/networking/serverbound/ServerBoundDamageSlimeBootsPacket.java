package einstein.usefulslime.networking.serverbound;

import einstein.usefulslime.UsefulSlime;
import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.EquipmentSlot;

public record ServerBoundDamageSlimeBootsPacket(int damage) implements CustomPacketPayload {

    public static final Type<ServerBoundDamageSlimeBootsPacket> TYPE = new Type<>(UsefulSlime.loc("damage_slime_boots"));
    public static final StreamCodec<FriendlyByteBuf, ServerBoundDamageSlimeBootsPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ServerBoundDamageSlimeBootsPacket::damage,
            ServerBoundDamageSlimeBootsPacket::new
    );

    public static void handle(ServerBoundDamageSlimeBootsPacket packet, ServerPlayNetworkContext context) {
        if (context.networkSide().equals(PacketFlow.CLIENTBOUND)) {
            UsefulSlime.damageEquipment(context.player(), EquipmentSlot.FEET, packet.damage);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
