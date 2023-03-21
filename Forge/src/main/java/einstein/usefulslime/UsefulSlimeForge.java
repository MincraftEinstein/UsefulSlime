package einstein.usefulslime;

import einstein.usefulslime.platform.ForgeRegistryHelper;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.LivingFallData;
import einstein.usefulslime.util.PlayerTickData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod(UsefulSlime.MOD_ID)
public class UsefulSlimeForge {

    public UsefulSlimeForge() {
        UsefulSlime.init();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onBuildContents);
        ForgeRegistryHelper.ITEMS.register(modEventBus);
        ForgeRegistryHelper.BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onFall);
        MinecraftForge.EVENT_BUS.addListener(this::missingMappings);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    void onFall(LivingFallEvent event) {
        LivingFallData data = new LivingFallData(event.getEntity(), event.getDistance(), event.getDamageMultiplier());
        UsefulSlime.onFall(data);
        event.setDamageMultiplier(data.getDamageMultiplier());
        event.setDistance(data.getDistance());

        if (data.isCanceled()) {
            event.setCanceled(true);
        }
    }

    void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (BounceHandler.BOUNCING_ENTITIES.containsKey(player)) {
                BounceHandler.BOUNCING_ENTITIES.get(player).onPlayerTick(new PlayerTickData(player));
            }
        }
    }

    void onBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModInit.SLIME_SLING);
            event.accept(ModInit.SLIME_BOOTS);
            event.accept(ModInit.SLIPPERY_SLIME_BLOCK_ITEM);
        }
        else if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ModInit.SLIME_BOOTS);
            event.accept(ModInit.SLIME_LEGGINGS);
            event.accept(ModInit.SLIME_CHESTPLATE);
            event.accept(ModInit.SLIME_HELMET);
        }
        else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModInit.JELLO);
        }
    }

    void missingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), UsefulSlime.MOD_ID)) {
            if (mapping.getKey().getPath().equals("slimesling")) {
                mapping.remap(ModInit.SLIME_SLING.get());
            }
        }
    }
}
