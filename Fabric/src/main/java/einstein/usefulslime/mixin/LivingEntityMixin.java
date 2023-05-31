package einstein.usefulslime.mixin;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.LivingFallData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    private static final ThreadLocal<LivingFallData> CURRENT_FALL_DATA = new ThreadLocal<>();

    @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> callbackInfo) {
        LivingFallData data = new LivingFallData((LivingEntity) (Object) this, distance, damageMultiplier);
        UsefulSlime.onFall(data);
        if (data.isCanceled()) {
            callbackInfo.setReturnValue(false);
        }
        CURRENT_FALL_DATA.set(data);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "LOAD"), ordinal = 0, argsOnly = true)
    private float modifyFallDistance(float distance) {
        LivingFallData data = CURRENT_FALL_DATA.get();
        if (data != null) {
            distance = data.getDistance();
        }
        return distance;
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "LOAD"), ordinal = 1, argsOnly = true)
    private float modifyFallDamageMultiplier(float damageMultiplyer) {
        LivingFallData data = CURRENT_FALL_DATA.get();
        if (data != null) {
            damageMultiplyer = data.getDamageMultiplier();
        }
        return damageMultiplyer;
    }
}
