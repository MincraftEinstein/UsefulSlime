package einstein.usefulslime.util;

import einstein.usefulslime.items.SlimeBoots;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ModEventHandler {

    @SubscribeEvent
    public void onFall(final @NotNull LivingFallEvent event) {
        final LivingEntity entity = event.getEntity();

        if (entity == null) {
            return;
        }

        final ItemStack feetSlot = entity.getItemBySlot(EquipmentSlot.FEET);

        if (!(feetSlot.getItem() instanceof SlimeBoots)) {
            return;
        }

        if (!entity.isShiftKeyDown() && event.getDistance() > 2) {
            event.setDamageMultiplier(0);
            entity.fallDistance = 0;
            if (entity.level.isClientSide) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * -0.9F, entity.getDeltaMovement().z);
                entity.hasImpulse = true;
                entity.setOnGround(false);
                final double d = 0.9500000000000001D;
                entity.setDeltaMovement(entity.getDeltaMovement().x / d, entity.getDeltaMovement().y, entity.getDeltaMovement().z / d);
            } else {
                event.setCanceled(true);
            }
            entity.playSound(SoundEvents.SLIME_SQUISH, 1, 1);
            BounceHandler.addBounceHandler(entity, entity.getDeltaMovement().y);
        } else if (!entity.level.isClientSide && entity.isShiftKeyDown()) {
            event.setDamageMultiplier(0.2F);
        }
    }
}
