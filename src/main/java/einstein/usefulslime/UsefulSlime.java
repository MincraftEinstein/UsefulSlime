package einstein.usefulslime;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UsefulSlime.MODID)
public class UsefulSlime
{
	public static final String MODID = "usefulslime";
	
	public UsefulSlime() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
		MinecraftForge.EVENT_BUS.register(this);
		ModInit.ITEMS.register(modEventBus);
		ModInit.BLOCKS.register(modEventBus);
	}
}
