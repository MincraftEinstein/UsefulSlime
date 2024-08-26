package einstein.usefulslime.init;

import einstein.usefulslime.blocks.BouncySlimeBlock;
import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class ModBlocks {

    public static final Supplier<Block> SLIPPERY_SLIME_BLOCK = register("slippery_slime_block", () -> new SlipperySlimeBlock(Block.Properties.of().mapColor(MapColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final Supplier<Block> BOUNCY_SLIME_BLOCK = register("bouncy_slime_block", () -> new BouncySlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noOcclusion().sound(SoundType.SLIME_BLOCK)));

    public static void init() {
    }

    private static <T extends Block> Supplier<Block> register(String name, Supplier<T> supplier) {
        return Services.REGISTRY.registerBlock(name, supplier);
    }
}
