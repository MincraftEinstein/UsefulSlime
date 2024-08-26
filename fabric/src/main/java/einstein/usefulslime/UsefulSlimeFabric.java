package einstein.usefulslime;

import einstein.usefulslime.init.ModBlocks;
import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import fuzs.forgeconfigapiport.fabric.api.forge.v4.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.fml.config.ModConfig;

public class UsefulSlimeFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        UsefulSlime.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.SPYGLASS, ModItems.SLIME_SLING.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.accept(ModItems.SLIPPERY_SLIME_BLOCK_ITEM.get());
            entries.accept(ModItems.BOUNCY_SLIME_BLOCK_ITEM.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addAfter(Items.TURTLE_HELMET, ModItems.SLIME_HELMET.get(), ModItems.SLIME_CHESTPLATE.get(), ModItems.SLIME_LEGGINGS.get(), ModItems.SLIME_BOOTS.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addBefore(Items.MUSHROOM_STEW, ModItems.JELLO.get());
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(UsefulSlime::onServerStopped);
        ForgeConfigRegistry.INSTANCE.register(UsefulSlime.MOD_ID, ModConfig.Type.COMMON, ModCommonConfigs.SPEC);
    }

    @Override
    public void onInitializeClient() {
        UsefulSlime.clientSetup();
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIPPERY_SLIME_BLOCK.get(), RenderType.translucent());
    }
}
