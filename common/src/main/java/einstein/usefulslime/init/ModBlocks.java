package einstein.usefulslime.init;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.blocks.BouncePadBlock;
import einstein.usefulslime.blocks.BouncySlimeBlock;
import einstein.usefulslime.blocks.SlipperySlimeBlock;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.util.platform.Registrar;
import me.fzzyhmstrs.fzzy_config.util.platform.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class ModBlocks {

    private static final Registrar<Block> BLOCKS = ConfigApiJava.platform().createRegistrar(UsefulSlime.MOD_ID, BuiltInRegistries.BLOCK);

    public static final RegistrySupplier<Block> SLIPPERY_SLIME_BLOCK = register("slippery_slime_block", () -> new SlipperySlimeBlock(Block.Properties.of().mapColor(MapColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final RegistrySupplier<Block> BOUNCY_SLIME_BLOCK = register("bouncy_slime_block", () -> new BouncySlimeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final RegistrySupplier<Block> BOUNCE_PAD = register("bounce_pad", () -> new BouncePadBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.3F).sound(SoundType.SLIME_BLOCK).isValidSpawn((state, getter, pos, entityType) -> false)));

    public static void init() {
        BLOCKS.init();
    }

    private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

}