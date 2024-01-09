package einstein.usefulslime.mixin;

import einstein.usefulslime.util.ClimbingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements ClimbingEntity {

    @Shadow
    public boolean verticalCollision;

    @Unique
    private boolean usefulSlime$verticalCollisionAbove;

    @Unique
    private boolean usefulSlime$canHangClimb;

    @Unique
    private boolean usefulSlime$canWallClimb;

    @Inject(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;verticalCollisionBelow:Z"))
    private void move(MoverType type, Vec3 vec3, CallbackInfo ci) {
        usefulSlime$verticalCollisionAbove = verticalCollision && vec3.y > 0;
    }

    @Override
    public boolean usefulSlime$verticalCollisionAbove() {
        return usefulSlime$verticalCollisionAbove;
    }

    @Override
    public boolean usefulSlime$canHangClimb() {
        return usefulSlime$canHangClimb;
    }

    @Override
    public boolean usefulSlime$canWallClimb() {
        return usefulSlime$canWallClimb;
    }

    @Override
    public void usefulSlime$setHangClimbing(boolean hangClimbing) {
        usefulSlime$canHangClimb = hangClimbing;
    }

    @Override
    public void usefulSlime$setWallClimbing(boolean wallClimbing) {
        usefulSlime$canWallClimb = wallClimbing;
    }
}
