package einstein.usefulslime.mixin;

import commonnetwork.api.Dispatcher;
import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.networking.serverbound.ServerBoundHangClimbPacket;
import einstein.usefulslime.networking.serverbound.ServerBoundWallClimbPacket;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static einstein.usefulslime.UsefulSlime.damageEquipment;

@Mixin(Player.class)
public abstract class ClimbingPlayerMixin extends LivingEntity {

    @Unique
    private final ClimbingEntity usefulSlime$climbingEntity = (ClimbingEntity) this;

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    protected ClimbingPlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean onClimbable() {
        if (level().isClientSide) {
            boolean canHangClimb = getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SLIME_HELMET.get())
                    && usefulSlime$climbingEntity.usefulSlime$verticalCollisionAbove()
                    && jumping;

            boolean canWallClimb = getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SLIME_LEGGINGS.get())
                    && getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SLIME_CHESTPLATE.get())
                    && horizontalCollision && !super.onClimbable();

            usefulSlime$climbingEntity.usefulSlime$setHangClimbing(canHangClimb);
            Dispatcher.sendToServer(new ServerBoundHangClimbPacket(canHangClimb));
            setNoGravity(canHangClimb);

            usefulSlime$climbingEntity.usefulSlime$setWallClimbing(canWallClimb);
            Dispatcher.sendToServer(new ServerBoundWallClimbPacket(canWallClimb));
        }
        return super.onClimbable() || usefulSlime$climbingEntity.usefulSlime$canWallClimb() || usefulSlime$climbingEntity.usefulSlime$canHangClimb();
    }
}
