package einstein.usefulslime.blocks;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.init.ModBlocks;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.IHasModel;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class SlipperySlimeBlock extends BlockBreakable implements IHasModel {

	public SlipperySlimeBlock(String name, Material material, MapColor color) {
		super(material, false, color);
		setUnlocalizedName(UsefulSlime.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.TRANSPORTATION);
		slipperiness = 1.5F;
		setSoundType(SoundType.SLIME);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		UsefulSlime.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		entityIn.fall(fallDistance, 0);
	}
}
