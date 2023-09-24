package einstein.usefulslime.mixin;

import einstein.usefulslime.util.EntityBounceModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        EntityBounceModifiers.causeFallDamage(distance, damageMultiplier, (LivingEntity) (Object) this, cir);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "LOAD"), ordinal = 0, argsOnly = true)
    private float modifyFallDistance(float distance) {
        return EntityBounceModifiers.modifyFallDistance(distance);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "LOAD"), ordinal = 1, argsOnly = true)
    private float modifyFallDamageMultiplier(float damageMultiplier) {
        return EntityBounceModifiers.modifyFallDamageMultiplier(damageMultiplier);
    }
}
