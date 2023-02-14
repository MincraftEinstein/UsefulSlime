package einstein.usefulslime.mixin;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.PlayerTickData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (BounceHandler.BOUNCING_ENTITIES.containsKey(player)) {
            BounceHandler.BOUNCING_ENTITIES.get(player).onPlayerTick(new PlayerTickData(player));
        }
    }
}
