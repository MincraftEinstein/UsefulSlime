package einstein.usefulslime;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UsefulSlime.MODID)
public class UsefulSlime {
    public static final String MODID = "usefulslime";

    public UsefulSlime() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupBlockRenderLayer);
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
        ModInit.ITEMS.register(modEventBus);
        ModInit.BLOCKS.register(modEventBus);
    }

    public void setupBlockRenderLayer(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModInit.SLIPPERY_SLIME_BLOCK.get(), RenderType.translucent());
    }
}
