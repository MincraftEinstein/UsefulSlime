package einstein.usefulslime.platform;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.platform.services.RegistryHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ForgeRegistryHelper implements RegistryHelper {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UsefulSlime.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UsefulSlime.MOD_ID);
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIAL = DeferredRegister.create(Registries.ARMOR_MATERIAL, UsefulSlime.MOD_ID);

    @Override
    public <T extends Block> Supplier<Block> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    @Override
    public <T extends Item> Supplier<Item> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    @Override
    public Supplier<Holder<ArmorMaterial>> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        RegistryObject<ArmorMaterial> registeredMaterial = ARMOR_MATERIAL.register(name, material);
        return () -> registeredMaterial.getHolder().orElseThrow();
    }
}
