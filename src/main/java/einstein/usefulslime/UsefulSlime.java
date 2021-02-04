package einstein.usefulslime;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(UsefulSlime.MODID)
public class UsefulSlime
{
	public static final String MODID = "usefulslime";
	
	public UsefulSlime() {
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
		MinecraftForge.EVENT_BUS.register(this);
	}
}
