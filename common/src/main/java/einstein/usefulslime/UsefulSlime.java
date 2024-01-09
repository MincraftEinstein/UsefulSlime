package einstein.usefulslime;

import commonnetwork.api.Dispatcher;
import einstein.usefulslime.init.ModBlocks;
import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.init.ModPackets;
import einstein.usefulslime.items.SlimeArmor;
import einstein.usefulslime.networking.serverbound.ServerBoundDamageSlimeBootsPacket;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.LivingFallData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsefulSlime {

    public static final String MOD_ID = "usefulslime";
    public static final String MOD_NAME = "Useful Slime";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        ModBlocks.init();
        ModItems.init();
        ModPackets.init();
    }

    public static void clientSetup() {
        ModPackets.init();
    }

    public static void onFall(LivingFallData data) {
        LivingEntity entity = data.getEntity();

        if (entity == null) {
            return;
        }

        ItemStack footStack = entity.getItemBySlot(EquipmentSlot.FEET);

        if (!(footStack.getItem() instanceof SlimeArmor)) {
            return;
        }

        float distance = data.getDistance();
        Level level = entity.level();

        if (!entity.isShiftKeyDown() && distance > 2) {
            data.setDamageMultiplier(0);
            entity.fallDistance = 0;

            if (level.isClientSide) {
                double d = 0.9500000000000001D;

                entity.setDeltaMovement(entity.getDeltaMovement().x, entity.getDeltaMovement().y * -0.9F, entity.getDeltaMovement().z);
                entity.hasImpulse = true;
                entity.setOnGround(false);
                entity.setDeltaMovement(entity.getDeltaMovement().x / d, entity.getDeltaMovement().y, entity.getDeltaMovement().z / d);

                if (ModCommonConfigs.INSTANCE.bouncingDamagesSlimeBoots.get()) {
                    Dispatcher.sendToServer(new ServerBoundDamageSlimeBootsPacket(Math.round(distance / 10)));
                }
            }
            else {
                data.setCanceled(true);
            }

            entity.playSound(SoundEvents.SLIME_SQUISH);

            for (int i = 0; i < 8; i++) {
                float random1 = entity.getRandom().nextFloat() * 6.2831855F;
                float random2 = entity.getRandom().nextFloat() * 0.5F + 0.5F;
                float xOffset = Mth.sin(random1) * 0.5F * random2;
                float yOffset = Mth.cos(random1) * 0.5F * random2;
                level.addParticle(ParticleTypes.ITEM_SLIME, entity.getX() + xOffset, entity.getY(), entity.getZ() + yOffset, 0, 0, 0);
            }

            BounceHandler.addBounceHandler(entity, entity.getDeltaMovement().y);
        }
        else if (!level.isClientSide && entity.isShiftKeyDown()) {
            data.setDamageMultiplier(0.2F);
        }
    }

    public static void damageEquipment(LivingEntity entity, EquipmentSlot slot) {
        damageEquipment(entity, slot, 1);
    }

    public static void damageEquipment(LivingEntity entity, EquipmentSlot slot, int damage) {
        entity.getItemBySlot(slot).hurtAndBreak(damage, entity, broadcaster -> broadcaster.broadcastBreakEvent(slot));
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}