package einstein.usefulslime.platform.services;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface RegistryHelper {

    <T extends Block> Supplier<Block> registerBlock(String name, Supplier<T> block);

    <T extends Item> Supplier<Item> registerItem(String name, Supplier<T> item);
}
