package einstein.usefulslime.init;

import java.util.ArrayList;
import java.util.List;

import einstein.usefulslime.blocks.SlipperySlimeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block SLIPPERY_SLIME_BLOCK = new SlipperySlimeBlock("slippery_slime_block", Material.CLAY, MapColor.GRASS);
}
