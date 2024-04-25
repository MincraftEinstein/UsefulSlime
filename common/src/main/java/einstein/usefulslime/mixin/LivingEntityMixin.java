package einstein.usefulslime.mixin;

import einstein.usefulslime.init.ModBlocks;
import einstein.usefulslime.init.ModCommonConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

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
}
