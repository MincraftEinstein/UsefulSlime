package einstein.usefulslime;

import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.items.SlimeBoots;
import einstein.usefulslime.items.SlimeSlingItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UsefulSlime.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UsefulSlime.MODID);

    public static final RegistryObject<Block> SLIPPERY_SLIME_BLOCK = BLOCKS.register("slippery_slime_block", () -> new SlipperySlimeBlock(Block.Properties.of(Material.CLAY, MaterialColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Item> SLIPPERY_SLIME_BLOCK_ITEM = ITEMS.register("slippery_slime_block", () -> new BlockItem(SLIPPERY_SLIME_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SLIME_SLING = ITEMS.register("slime_sling", () -> new SlimeSlingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SLIME_BOOTS = ITEMS.register("slime_boots", () -> new SlimeBoots(new Item.Properties()));
}