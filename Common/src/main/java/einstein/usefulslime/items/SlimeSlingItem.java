package einstein.usefulslime.items;

import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.BounceHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SlimeSlingItem extends Item {

    public SlimeSlingItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResultHolder use(Level level, @NotNull Player player, InteractionHand hand) {
        final ItemStack itemStackIn = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) {
            return;
        }

        if (!player.onGround()) {
            return;
        }

        int timeUsed = getUseDuration(stack) - timeLeft;
        float i = timeUsed / 20F;
        i = (i * i + i * 2) / 3;
        i *= 4;

        if (i > 6) {
            i = 6;
        }

        i *= 1;
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

        if (hitResult != null) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                final Vec3 vec3 = player.getLookAngle().normalize();

                if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SLIME_CHESTPLATE.get() && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SLIME_HELMET.get()) {
                    i += 2;
                }

                if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SLIME_BOOTS.get() && player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SLIME_LEGGINGS.get()) {
                    i += 2;
                }

                player.push(vec3.x * -i, vec3.y * -i / 3, vec3.z * -i);
                BounceHandler.addBounceHandler(player);
                EquipmentSlot slot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                stack.hurtAndBreak(1, player, it -> it.broadcastBreakEvent(slot));
            }

            if (i > 1) {
                player.playSound(SoundEvents.SLIME_JUMP_SMALL, 1, 1);
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(final ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(final ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredientStack) {
        return ingredientStack.is(Items.SLIME_BALL);
    }
}
