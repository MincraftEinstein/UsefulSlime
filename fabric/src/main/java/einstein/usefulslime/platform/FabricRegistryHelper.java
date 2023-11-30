package einstein.usefulslime.platform;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.platform.services.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricRegistryHelper implements RegistryHelper {

    @Override
    public <T extends Block> Supplier<Block> registerBlock(String name, Supplier<T> block) {
        T t = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(UsefulSlime.MOD_ID, name), block.get());
        return () -> t;
    }

    @Override
    public <T extends Item> Supplier<Item> registerItem(String name, Supplier<T> item) {
        T t = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(UsefulSlime.MOD_ID, name), item.get());
        return () -> t;
    }
}
