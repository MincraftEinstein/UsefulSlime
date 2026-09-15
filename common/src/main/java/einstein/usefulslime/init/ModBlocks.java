package einstein.usefulslime.init;

import einstein.usefulslime.blocks.BouncePadBlock;
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
    public static final Supplier<Block> BOUNCE_PAD = register("bounce_pad", () -> new BouncePadBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.3F).sound(SoundType.SLIME_BLOCK).isValidSpawn((state, getter, pos, entityType) -> false)));

    public static void init() {
    }

    private static <T extends Block> Supplier<Block> register(String name, Supplier<T> supplier) {
        return Services.REGISTRY.registerBlock(name, supplier);
    }

}