package einstein.usefulslime;

import einstein.usefulslime.items.SlimeArmor;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.LivingFallData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsefulSlime {

    public static final String MOD_ID = "usefulslime";
    public static final String MOD_NAME = "Useful Slime";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        ModInit.init();
    }

    public static void onFall(LivingFallData data) {
        final LivingEntity entity = data.getEntity();

        if (entity == null) {
            return;
        }

        final ItemStack feetSlot = entity.getItemBySlot(EquipmentSlot.FEET);

        if (!(feetSlot.getItem() instanceof SlimeArmor)) {
            return;
        }

        if (!entity.isShiftKeyDown() && data.getDistance() > 2) {
            data.setDamageMultiplier(0);
            entity.fallDistance = 0;
            if (entity.level.isClientSide) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * -0.9F, entity.getDeltaMovement().z);
                entity.hasImpulse = true;
                entity.setOnGround(false);
                final double d = 0.9500000000000001D;
                entity.setDeltaMovement(entity.getDeltaMovement().x / d, entity.getDeltaMovement().y, entity.getDeltaMovement().z / d);
            }
            else {
                data.setCanceled(true);
            }
            entity.playSound(SoundEvents.SLIME_SQUISH, 1, 1);
            BounceHandler.addBounceHandler(entity, entity.getDeltaMovement().y);
        }
        else if (!entity.level.isClientSide && entity.isShiftKeyDown()) {
            data.setDamageMultiplier(0.2F);
        }
    }
}