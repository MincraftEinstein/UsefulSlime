package einstein.usefulslime.client;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.UsefulSlime.ModInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = UsefulSlime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
public class DataHandler {

    @SubscribeEvent
    public static void setupBlockRenderLayer(final FMLClientSetupEvent event) {
    	RenderTypeLookup.setRenderLayer(ModInit.SLIPPERY_SLIME_BLOCK, RenderType.getTranslucent());
    }
	
}
