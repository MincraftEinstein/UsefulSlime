package einstein.usefulslime;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class UsefulSlimeFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        UsefulSlime.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(ModInit.SLIME_SLING.get());
            entries.accept(ModInit.SLIME_BOOTS.get());
            entries.accept(ModInit.SLIPPERY_SLIME_BLOCK_ITEM.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.accept(ModInit.SLIME_BOOTS.get());
            entries.accept(ModInit.SLIME_LEGGINGS.get());
            entries.accept(ModInit.SLIME_CHESTPLATE.get());
            entries.accept(ModInit.SLIME_HELMET.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(ModInit.JELLO.get());
        });
    }
}
