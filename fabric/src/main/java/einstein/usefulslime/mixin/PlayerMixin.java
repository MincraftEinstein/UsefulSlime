package einstein.usefulslime.mixin;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.EntityBounceModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    private final Player player = (Player) (Object) this;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (BounceHandler.BOUNCING_ENTITIES.containsKey(player)) {
            BounceHandler.BOUNCING_ENTITIES.get(player).onPlayerTick(player);
        }
    }

    @Inject(method = "causeFallDamage(FFLnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        EntityBounceModifiers.causeFallDamage(distance, 0, player, cir);
    }

    @ModifyVariable(method = "causeFallDamage", at = @At(value = "LOAD"), ordinal = 0, argsOnly = true)
    private float modifyFallDistance(float distance) {
        if (player.getAbilities().mayfly) {
            return EntityBounceModifiers.modifyFallDistance(distance);
        }
        return distance;
    }
}
