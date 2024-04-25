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
            if (usefulSlime$climbingEntity.usefulSlime$canWallClimb()) {
                if (distanceY > 0) {
                    usefulSlime$distanceWallClimbed++;

                    if (usefulSlime$distanceWallClimbed % (random.nextInt(2, 4) * 10) == 0) {
                        level().playSound(null, getX(), getY(), getZ(), SoundEvents.SLIME_BLOCK_STEP, getSoundSource(), 0.15F, 1);
                    }

                    if (usefulSlime$distanceWallClimbed >= 100 && ModCommonConfigs.INSTANCE.wallClimbingDamagesSlimeChestplateAndLeggings.get()) {
                        usefulSlime$distanceWallClimbed = 0;
                        damageEquipment(this, EquipmentSlot.CHEST);
                        damageEquipment(this, EquipmentSlot.LEGS);
                    }
                }
            }

            if (usefulSlime$climbingEntity.usefulSlime$canHangClimb()) {
                if (Math.sqrt(distanceX * distanceX + distanceZ * distanceZ) > 0) {
                    usefulSlime$distanceHangClimbed++;

                    if (usefulSlime$distanceHangClimbed % (random.nextInt(2, 4) * 10) == 0) {
                        level().playSound(null, getX(), getY(), getZ(), SoundEvents.SLIME_BLOCK_STEP, getSoundSource(), 0.15F, 1);
                    }

                    if (usefulSlime$distanceHangClimbed >= 50 && ModCommonConfigs.INSTANCE.hangClimbingDamagesSlimeHelmet.get()) {
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
