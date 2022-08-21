package einstein.usefulslime;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod(UsefulSlime.MODID)
public class UsefulSlime {
    public static final String MODID = "usefulslime";

    public UsefulSlime() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setupBlockRenderLayer);
        ModInit.ITEMS.register(modEventBus);
        ModInit.BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setupBlockRenderLayer(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModInit.SLIPPERY_SLIME_BLOCK.get(), RenderType.translucent());
    }

    @SubscribeEvent
    void missingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping mapping : event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), MODID)) {
            if (mapping.getKey().getPath().equals("slimesling")) {
                mapping.remap(ModInit.SLIME_SLING.get());
            }
        }
    }
}
