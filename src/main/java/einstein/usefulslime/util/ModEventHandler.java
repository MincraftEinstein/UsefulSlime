package einstein.usefulslime.util;

import einstein.usefulslime.items.SlimeBoots;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModEventHandler {
	
	@SubscribeEvent
	public void onFall(final LivingFallEvent event) {
		final LivingEntity entity = event.getEntityLiving();
		if (entity == null) {
			return;
		}
		final ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
		if (!(feet.getItem() instanceof SlimeBoots)) {
			return;
		}
		if (!entity.isShiftKeyDown() && event.getDistance() > 2.0f) {
			event.setDamageMultiplier(0.0f);
			entity.fallDistance = 0.0f;
			if (entity.level.isClientSide) {
				entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * -0.9, entity.getDeltaMovement().z);
				entity.hasImpulse = true;
				entity.setOnGround(false);
				final double f = 0.9500000000000001;
				entity.setDeltaMovement(entity.getDeltaMovement().x / f, entity.getDeltaMovement().y, entity.getDeltaMovement().z / f);
			} else {
				event.setCanceled(true);
			}
			entity.playSound(SoundEvents.SLIME_SQUISH, 1.0f, 1.0f);
			BounceHandler.addBounceHandler(entity, entity.getDeltaMovement().y);
		} else if (!entity.level.isClientSide && entity.isShiftKeyDown()) {
			event.setDamageMultiplier(0.2f);
		}
	}
}
