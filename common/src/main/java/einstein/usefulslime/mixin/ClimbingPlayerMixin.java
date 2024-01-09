package einstein.usefulslime.mixin;

import commonnetwork.api.Dispatcher;
import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.networking.HangClimbPacket;
import einstein.usefulslime.networking.WallClimbPacket;
import einstein.usefulslime.util.ClimbingEntity;
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

    @Unique
    private int usefulSlime$distanceWallClimbed = 0;

    @Unique
    private int usefulSlime$distanceHangClimbed = 0;

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    protected ClimbingPlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "checkMovementStatistics", at = @At("TAIL"))
    private void checkMovementStatistics(double distanceX, double distanceY, double distanceZ, CallbackInfo ci) {
        if (!level().isClientSide && !isPassenger() && onClimbable()) {
            if (usefulSlime$climbingEntity.usefulSlime$canWallClimb() && ModCommonConfigs.INSTANCE.wallClimbingDamagesSlimeChestplateAndLeggings.get()) {
                if (distanceY > 0) {
                    usefulSlime$distanceWallClimbed++;
                    if (usefulSlime$distanceWallClimbed >= 30) {
                        usefulSlime$distanceWallClimbed = 0;
                        damageEquipment(this, EquipmentSlot.CHEST);
                        damageEquipment(this, EquipmentSlot.LEGS);
                    }
                }
            }

            if (usefulSlime$climbingEntity.usefulSlime$canHangClimb() && ModCommonConfigs.INSTANCE.hangClimbingDamagesSlimeHelmet.get()) {
                if (Math.sqrt(distanceX * distanceX + distanceZ * distanceZ) > 0) {
                    usefulSlime$distanceHangClimbed++;
                    if (usefulSlime$distanceHangClimbed >= 100) {
                        usefulSlime$distanceHangClimbed = 0;
                        damageEquipment(this, EquipmentSlot.HEAD);
                    }
                }
            }
        }
    }

    @Override
    public boolean onClimbable() {
        if (level().isClientSide) {
            boolean canHangClimb = getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SLIME_HELMET.get())
                    && usefulSlime$climbingEntity.usefulSlime$verticalCollisionAbove()
                    && jumping;

            boolean canWallClimb = getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SLIME_LEGGINGS.get())
                    && getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SLIME_CHESTPLATE.get())
                    && horizontalCollision;

            usefulSlime$climbingEntity.usefulSlime$setHangClimbing(canHangClimb);
            Dispatcher.sendToServer(new HangClimbPacket(canHangClimb));
            setNoGravity(usefulSlime$climbingEntity.usefulSlime$canHangClimb());

            usefulSlime$climbingEntity.usefulSlime$setWallClimbing(canWallClimb);
            Dispatcher.sendToServer(new WallClimbPacket(canWallClimb));
        }
        return super.onClimbable() || usefulSlime$climbingEntity.usefulSlime$canWallClimb() || usefulSlime$climbingEntity.usefulSlime$canHangClimb();
    }
}
