package einstein.usefulslime.mixin;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.init.ModBlocks;
import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

    @Shadow
    public abstract boolean isFallFlying();

    @Unique
    private final LivingEntity usefulSlime$me = (LivingEntity) (Object) this;

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "travel", at = @At("TAIL"))
    private void travel(Vec3 travelVector, CallbackInfo ci) {
        BlockPos belowPos = getBlockPosBelowThatAffectsMyMovement();
        BlockState belowState = level().getBlockState(belowPos);

        if (belowState.is(ModBlocks.SLIPPERY_SLIME_BLOCK.get())) {
            int max = ModCommonConfigs.INSTANCE.maxSlipperySlimeBlockSpeed.get();
            Vec3 movement = getDeltaMovement();

            if (movement.x > max) {
                setDeltaMovement(max, movement.y, movement.z);
            }

            if (movement.z > max) {
                setDeltaMovement(movement.x, movement.y, max);
            }

            if (movement.x < -max) {
                setDeltaMovement(-max, movement.y, movement.z);
            }

            if (movement.z < -max) {
                setDeltaMovement(movement.x, movement.y, -max);
            }
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!isInvulnerableTo(source) && isFallFlying() && source.is(DamageTypes.FLY_INTO_WALL) && isAlive()) {
            if (getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SLIME_HELMET.get())) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.SLIME_SQUISH, getSoundSource(), 1, 1);
                UsefulSlime.damageEquipment(usefulSlime$me, EquipmentSlot.HEAD, Math.round(amount / 2));
                cir.setReturnValue(false);
            }
        }
    }
}
