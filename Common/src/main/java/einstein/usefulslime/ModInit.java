package einstein.usefulslime;

import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.items.SlimeArmor;
import einstein.usefulslime.items.SlimeSlingItem;
import einstein.usefulslime.platform.Services;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class ModInit {

    public static final Supplier<Block> SLIPPERY_SLIME_BLOCK = Services.REGISTRY.registerBlock("slippery_slime_block", () -> new SlipperySlimeBlock(Block.Properties.of(Material.CLAY, MaterialColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)));
    public static final Supplier<Item> SLIPPERY_SLIME_BLOCK_ITEM = Services.REGISTRY.registerItem("slippery_slime_block", () -> new BlockItem(SLIPPERY_SLIME_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SLIME_SLING = Services.REGISTRY.registerItem("slime_sling", () -> new SlimeSlingItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SLIME_BOOTS = Services.REGISTRY.registerItem("slime_boots", () -> new SlimeArmor(new Item.Properties(), ArmorItem.Type.BOOTS));
    public static final Supplier<Item> SLIME_LEGGINGS = Services.REGISTRY.registerItem("slime_leggings", () -> new SlimeArmor(new Item.Properties(), ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> SLIME_CHESTPLATE = Services.REGISTRY.registerItem("slime_chestplate", () -> new SlimeArmor(new Item.Properties(), ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> SLIME_HELMET = Services.REGISTRY.registerItem("slime_helmet", () -> new SlimeArmor(new Item.Properties(), ArmorItem.Type.HELMET));

    public static void init() {}
}
