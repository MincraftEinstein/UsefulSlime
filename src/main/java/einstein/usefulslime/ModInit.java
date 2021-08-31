package einstein.usefulslime;

import einstein.einsteins_library.util.RegistryHandler;
import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.items.SlimeBoots;
import einstein.usefulslime.items.Slimesling;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UsefulSlime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModInit {
	public static final Block SLIPPERY_SLIME_BLOCK = RegistryHandler.registerBlock(UsefulSlime.MODID, "slippery_slime_block", new SlipperySlimeBlock(Block.Properties.of(Material.CLAY, MaterialColor.GRASS).friction(1.5F).noOcclusion().sound(SoundType.SLIME_BLOCK)), CreativeModeTab.TAB_TRANSPORTATION);
	public static final Item SLIMESLING = RegistryHandler.registerItem(UsefulSlime.MODID, "slimesling", new Slimesling(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS)));
	public static final Item SLIME_BOOTS = RegistryHandler.registerItem(UsefulSlime.MODID, "slime_boots", new SlimeBoots(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
}