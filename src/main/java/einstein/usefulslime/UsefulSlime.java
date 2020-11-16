package einstein.usefulslime;

import einstein.einsteins_library.util.RegistryHandler;
import einstein.usefulslime.blocks.SlipperySlimeBlock;
import einstein.usefulslime.items.SlimeBoots;
import einstein.usefulslime.items.Slimesling;
import einstein.usefulslime.util.BounceEvent;
import einstein.usefulslime.util.BounceHandler;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(UsefulSlime.MODID)
public class UsefulSlime {

	public static final String MODID = "usefulslime";
	
	public UsefulSlime() {
        MinecraftForge.EVENT_BUS.register(new BounceEvent());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Mod.EventBusSubscriber(modid = UsefulSlime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModInit {
		public static final Block SLIPPERY_SLIME_BLOCK = RegistryHandler.registerBlock(UsefulSlime.MODID, "slippery_slime_block", new SlipperySlimeBlock(Block.Properties.create(Material.CLAY, MaterialColor.GRASS).slipperiness(1.5F).notSolid().sound(SoundType.SLIME)), ItemGroup.TRANSPORTATION);
		public static final Item SLIMESLING = RegistryHandler.registerItem(UsefulSlime.MODID, "slimesling", new Slimesling(new Item.Properties().maxStackSize(1).group(ItemGroup.TOOLS)));
		public static final Item SLIME_BOOTS = RegistryHandler.registerItem(UsefulSlime.MODID, "slime_boots", new SlimeBoots(new Item.Properties().group(ItemGroup.COMBAT)));
	}
	
}
