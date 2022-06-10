package einstein.usefulslime.items;

import javax.annotation.Nonnull;

import einstein.usefulslime.util.BounceHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Slimesling extends Item 
{
	public Slimesling(Properties properties) {
		super(properties);
	}

	@Override
	@Nonnull
	public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand) {
		final ItemStack itemStackIn = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
	}

	@Override
	public void releaseUsing(final ItemStack stack, final Level level, final LivingEntity entityLiving,
			final int timeLeft) {
		if (!(entityLiving instanceof Player)) {
			return;
		}
		final Player player = (Player) entityLiving;
		if (!player.isOnGround()) {
			return;
		}
		final int i = this.getUseDuration(stack) - timeLeft;
		float f = i / 20.0f;
		f = (f * f + f * 2.0f) / 3.0f;
		f *= 4.0f;
		if (f > 6.0f) {
			f = 6.0f;
		}
		f *= 1.5;
		final HitResult mop = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
		if (mop != null && mop.getType() == HitResult.Type.BLOCK) {
			final Vec3 vec = player.getLookAngle().normalize();
			player.push(vec.x * -f, vec.y * -f / 3.0, vec.z * -f);
			player.playSound(SoundEvents.SLIME_JUMP_SMALL, 1.0f, 1.0f);
			BounceHandler.addBounceHandler((LivingEntity) player);
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
}
