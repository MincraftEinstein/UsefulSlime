package einstein.usefulslime;


import einstein.usefulslime.proxy.CommonProxy;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = UsefulSlime.MODID, name = UsefulSlime.NAME, version = UsefulSlime.VERSION)
public class UsefulSlime
{
    public static final String MODID = "usefulslime";
    public static final String NAME = "Useful Slime";
    public static final String VERSION = "1.2";

    @SidedProxy(clientSide = "einstein.usefulslime.proxy.ClientProxy", serverSide = "einstein.usefulslime.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public UsefulSlime() {
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(BounceHandler.class);
		MinecraftForge.EVENT_BUS.register(this);
	}
}
