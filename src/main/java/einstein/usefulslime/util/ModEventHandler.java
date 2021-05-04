package einstein.usefulslime.util;

import einstein.usefulslime.items.SlimeBoots;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModEventHandler {

	@SubscribeEvent
	public void onFall(final LivingFallEvent event) {
		final EntityLivingBase entity = event.getEntityLiving();
		if (entity == null) {
			return;
		}
		final ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		if (!(feet.getItem() instanceof SlimeBoots)) {
			return;
		}
		if (!entity.isSneaking() && event.getDistance() > 2.0f) {
			event.setDamageMultiplier(0);
			entity.fallDistance = 0;
			if (entity.world.isRemote) {
				entity.motionY *= -0.9;
				entity.isAirBorne = true;
				entity.onGround = false;
				final double f = 0.9500000000000001;
				entity.motionX /= f;
				entity.motionZ /= f;
			} else {
				event.setCanceled(true);
			}
			entity.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0f, 1.0f);
			BounceHandler.addBounceHandler(entity, entity.motionY);
		} else if (!entity.world.isRemote && entity.isSneaking()) {
			event.setDamageMultiplier(0.2f);
		}
	}
}
