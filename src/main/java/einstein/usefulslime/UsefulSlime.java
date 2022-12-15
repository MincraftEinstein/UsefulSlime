package einstein.usefulslime;

import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.ModEventHandler;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
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
//        modEventBus.addListener(this::setupBlockRenderLayer);
        modEventBus.addListener(this::onBuildContents);
        ModInit.ITEMS.register(modEventBus);
        ModInit.BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(BounceHandler.class);
        MinecraftForge.EVENT_BUS.register(this);
    }

    void onBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModInit.SLIME_SLING);
            event.accept(ModInit.SLIME_BOOTS);
            event.accept(ModInit.SLIPPERY_SLIME_BLOCK_ITEM);
        }
        else if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ModInit.SLIME_BOOTS);
        }
    }

//    public void setupBlockRenderLayer(final FMLClientSetupEvent event) {
//        ItemBlockRenderTypes.setRenderLayer(ModInit.SLIPPERY_SLIME_BLOCK.get(), RenderType.translucent());
//    }

    @SubscribeEvent
    void missingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), MODID)) {
            if (mapping.getKey().getPath().equals("slimesling")) {
                mapping.remap(ModInit.SLIME_SLING.get());
            }
        }
    }
}
