package einstein.usefulslime.items;

import javax.annotation.Nonnull;

import einstein.usefulslime.util.BounceHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Slimesling extends Item 
{
	public Slimesling(Properties properties) {
		super(properties);
	}

	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(final World worldIn, final PlayerEntity playerIn, final Hand hand) {
		final ItemStack itemStackIn = playerIn.getHeldItem(hand);
		playerIn.setActiveHand(hand);
		return (ActionResult<ItemStack>) new ActionResult(ActionResultType.SUCCESS, (Object) itemStackIn);
	}

	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final LivingEntity entityLiving,
			final int timeLeft) {
		if (!(entityLiving instanceof PlayerEntity)) {
			return;
		}
		final PlayerEntity player = (PlayerEntity) entityLiving;
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
		final RayTraceResult mop = rayTrace(worldIn, player, RayTraceContext.FluidMode.NONE);
		if (mop != null && mop.getType() == RayTraceResult.Type.BLOCK) {
			final Vector3d vec = player.getLookVec().normalize();
			player.addVelocity(vec.x * -f, vec.y * -f / 3.0, vec.z * -f);
			player.playSound(SoundEvents.ENTITY_SLIME_JUMP_SMALL, 1.0f, 1.0f);
			BounceHandler.addBounceHandler((LivingEntity) player);
		}
	}

	public UseAction getUseAction(final ItemStack stack) {
		return UseAction.BOW;
	}

	public int getUseDuration(final ItemStack stack) {
		return 72000;
	}
}
