package einstein.usefulslime.util;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class EntityBounceModifiers {

    private static final ThreadLocal<LivingFallData> CURRENT_FALL_DATA = new ThreadLocal<>();

    public static void causeFallDamage(float distance, float damageMultiplier, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingFallData data = new LivingFallData(entity, distance, damageMultiplier);
        UsefulSlime.onFall(data);
        if (data.isCanceled()) {
            cir.setReturnValue(false);
        }
        CURRENT_FALL_DATA.set(data);
    }

    public static float modifyFallDistance(float distance) {
        LivingFallData data = CURRENT_FALL_DATA.get();
        if (data != null) {
            distance = data.getDistance();
        }
        return distance;
    }

    public static float modifyFallDamageMultiplier(float damageMultiplyer) {
        LivingFallData data = CURRENT_FALL_DATA.get();
        if (data != null) {
            damageMultiplyer = data.getDamageMultiplier();
        }
        return damageMultiplyer;
    }
}
