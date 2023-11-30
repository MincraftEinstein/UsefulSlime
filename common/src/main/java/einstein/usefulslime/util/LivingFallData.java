package einstein.usefulslime.util;

import net.minecraft.world.entity.LivingEntity;

public class LivingFallData {

    private final LivingEntity entity;
    private float distance;
    private float damageMultiplier;
    private boolean canceled;

    public LivingFallData(LivingEntity entity, float distance, float damageMultiplier) {
        this.entity = entity;
        this.distance = distance;
        this.damageMultiplier = damageMultiplier;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
