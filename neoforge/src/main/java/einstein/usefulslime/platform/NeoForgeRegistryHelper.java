package einstein.usefulslime.platform;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.platform.services.RegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(UsefulSlime.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(UsefulSlime.MOD_ID);

    @Override
    public <T extends Block> Supplier<Block> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    @Override
    public <T extends Item> Supplier<Item> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
