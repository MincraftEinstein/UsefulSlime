package einstein.usefulslime.mixin;

import com.mojang.authlib.GameProfile;
import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static einstein.usefulslime.UsefulSlime.CONFIGS;
import static einstein.usefulslime.UsefulSlime.damageEquipment;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    @Unique
    private final ClimbingEntity usefulSlime$climbingEntity = (ClimbingEntity) this;

    @Unique
    private int usefulSlime$distanceWallClimbed = 0;

    @Unique
    private int usefulSlime$distanceHangClimbed = 0;

    public ServerPlayerMixin(Level level, BlockPos pos, float yHeadRot, GameProfile profile) {
        super(level, pos, yHeadRot, profile);
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

                    if (usefulSlime$distanceWallClimbed >= 100 && CONFIGS.wallClimbingDamagesSlimeChestplateAndLeggings) {
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

                    if (usefulSlime$distanceHangClimbed >= 50 && CONFIGS.hangClimbingDamagesSlimeHelmet) {
                        usefulSlime$distanceHangClimbed = 0;
                        damageEquipment(this, EquipmentSlot.HEAD);
                    }
                }
            }
        }
    }
}
