package einstein.usefulslime.init;

import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.platform.Services;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class ModBlocks {

    public static final Supplier<Block> SLIPPERY_SLIME_BLOCK = Services.REGISTRY.registerBlock("slippery_slime_block", () -> new SlipperySlimeBlock(Block.Properties.of().mapColor(MapColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)));

    public static void init() {
    }

    private static <T extends Block> Supplier<Block> register(String name, Supplier<T> supplier) {
        return Services.REGISTRY.registerBlock(name, supplier);
    }
}
