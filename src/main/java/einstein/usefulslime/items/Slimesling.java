package einstein.usefulslime.items;

import javax.annotation.Nonnull;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Slimesling extends Item implements IHasModel
{

	public Slimesling(String name) {
		setUnlocalizedName(UsefulSlime.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		setMaxStackSize(1);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		UsefulSlime.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		final ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return (ActionResult<ItemStack>) new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final int timeLeft) {
		if (!(entityLiving instanceof EntityPlayer)) {
			return;
		}
		final EntityPlayer player = (EntityPlayer)entityLiving;
		if (!player.onGround) {
			return;
		}
		final int i = getMaxItemUseDuration(stack) - timeLeft;
		float f = i / 20.0f;
		f = (f * f + f * 2.0f) / 3.0f;
		f *= 4.0f;
		if (f > 6.0f) {
			f = 6.0f;
		}
		f *= 1.5;
		final RayTraceResult mop = rayTrace(worldIn, player, false);
		if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
			final Vec3d vec = player.getLookVec().normalize();
			player.addVelocity(vec.x * -f, vec.y * -f / 3.0, vec.z * -f);
			player.playSound(SoundEvents.ENTITY_SMALL_SLIME_JUMP, 1.0f, 1.0f);
			BounceHandler.addBounceHandler(player);
		}
	}
	
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 72000;
	}
}
