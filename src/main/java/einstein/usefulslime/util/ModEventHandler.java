package einstein.usefulslime.util;

import einstein.usefulslime.items.SlimeBoots;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
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
		final ItemStack feet = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
		if (!(feet.getItem() instanceof SlimeBoots)) {
			return;
		}
		if (!entity.isSneaking() && event.getDistance() > 2.0f) {
			event.setDamageMultiplier(0.0f);
			entity.fallDistance = 0.0f;
			if (entity.world.isRemote) {
				entity.setMotion(entity.getMotion().x, entity.getMotion().y * -0.9, entity.getMotion().z);
				entity.isAirBorne = true;
				entity.setOnGround(false);
				final double f = 0.9500000000000001;
				entity.setMotion(entity.getMotion().x / f, entity.getMotion().y, entity.getMotion().z / f);
			} else {
				event.setCanceled(true);
			}
			entity.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0f, 1.0f);
			BounceHandler.addBounceHandler(entity, entity.getMotion().y);
		} else if (!entity.world.isRemote && entity.isSneaking()) {
			event.setDamageMultiplier(0.2f);
		}
	}
}
