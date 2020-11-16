package einstein.usefulslime.blocks;

import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlipperySlimeBlock extends BreakableBlock {

	public SlipperySlimeBlock(Properties properties) {
		super(properties);

	}

	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		entityIn.onLivingFall(fallDistance, 0.0F);
	}

}
