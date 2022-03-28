package einstein.usefulslime.client;

import einstein.usefulslime.ModInit;
import einstein.usefulslime.UsefulSlime;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = UsefulSlime.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = { Dist.CLIENT })
public class DataHandler 
{
    @SubscribeEvent
    public static void setupBlockRenderLayer(final FMLClientSetupEvent event) {
    	ItemBlockRenderTypes.setRenderLayer(ModInit.SLIPPERY_SLIME_BLOCK.get(), RenderType.translucent());
    }
}
