package einstein.usefulslime.mixin;

import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.VerticalCollider;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

@Mixin(Player.class)
public abstract class ClimbingPlayerMixin extends LivingEntity {

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Unique
    private static final EntityDataAccessor<Boolean> DATA_CLIMBING = SynchedEntityData.defineId(ClimbingPlayerMixin.class, EntityDataSerializers.BOOLEAN);

    protected ClimbingPlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSynchedData(CallbackInfo ci) {
        entityData.define(DATA_CLIMBING, false);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (level().isClientSide) {
            usefulSlime$setClimbing(horizontalCollision);
        }
    }

    @Override
    public boolean onClimbable() {
        boolean canWallClimb = getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SLIME_LEGGINGS.get())
                && getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SLIME_CHESTPLATE.get())
                && entityData.get(DATA_CLIMBING);

        boolean canHangClimb = getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SLIME_HELMET.get())
                && ((VerticalCollider) this).verticalCollisionAbove() && jumping;

        setNoGravity(canHangClimb);
        return super.onClimbable() || canWallClimb || canHangClimb;
    }

    @Unique
    private void usefulSlime$setClimbing(boolean collidingHorizontally) {
        entityData.set(DATA_CLIMBING, collidingHorizontally);
    }
}
