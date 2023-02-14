package einstein.usefulslime.mixin;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.util.LivingFallData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected abstract int calculateFallDamage(float $$0, float $$1);

    @Shadow protected abstract SoundEvent getFallDamageSound(int $$0);

    @Shadow protected abstract void playBlockFallSound();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * @author MincraftEinstein
     * @reason Needed to change the value of <b>{@code distance}</b> and <b>{@code damageMultiplier}</b> and at
     * the time of writing this, the only way I know of to maintain parity between Forge and Fabric is to overwrite
     * the method
     */
    @Overwrite
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) (this);
        LivingFallData data = new LivingFallData(entity, distance, damageMultiplier);
        UsefulSlime.onFall(data);
        distance = data.getDistance();
        damageMultiplier = data.getDamageMultiplier();
        if (data.isCanceled()) {
            return false;
        }

        // Copied directly from Minecraft's source code (DO NOT CHANGE!!!)
        boolean causeFallDamage = super.causeFallDamage(distance, damageMultiplier, source);
        int damage = calculateFallDamage(distance, damageMultiplier);
        if (damage > 0) {
            playSound(getFallDamageSound(damage), 1.0F, 1.0F);
            playBlockFallSound();
            hurt(source, damage);
            return true;
        } else {
            return causeFallDamage;
        }
    }
}
