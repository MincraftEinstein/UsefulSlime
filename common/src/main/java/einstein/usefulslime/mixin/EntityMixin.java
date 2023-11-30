package einstein.usefulslime.mixin;

import einstein.usefulslime.util.VerticalCollider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements VerticalCollider {

    @Shadow public boolean verticalCollision;
    private boolean verticalCollisionAbove;

    @Inject(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;verticalCollisionBelow:Z"))
    private void move(MoverType type, Vec3 vec3, CallbackInfo ci) {
        verticalCollisionAbove = verticalCollision && vec3.y > 0;
    }

    @Override
    public boolean verticalCollisionAbove() {
        return verticalCollisionAbove;
    }
}
