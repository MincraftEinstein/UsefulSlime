package einstein.usefulslime;

import einstein.usefulslime.init.ModCommonConfigs;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.platform.NeoForgeRegistryHelper;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.LivingFallData;
import fuzs.forgeconfigapiport.neoforge.api.forge.v4.ForgeConfigRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent;

@Mod(UsefulSlime.MOD_ID)
public class UsefulSlimeNeoForge {

    public UsefulSlimeNeoForge(IEventBus eventBus) {
        UsefulSlime.init();
        eventBus.addListener(this::onBuildContents);
        eventBus.addListener(this::clientSetup);
        NeoForgeRegistryHelper.ITEMS.register(eventBus);
        NeoForgeRegistryHelper.BLOCKS.register(eventBus);
        NeoForge.EVENT_BUS.addListener(this::onFall);
        NeoForge.EVENT_BUS.addListener(this::onPlayerFlyFall);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
        ForgeConfigRegistry.INSTANCE.register(UsefulSlime.MOD_ID, ModConfig.Type.COMMON, ModCommonConfigs.SPEC);
    }

    void clientSetup(FMLClientSetupEvent event) {
        UsefulSlime.clientSetup();
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

    void onPlayerFlyFall(PlayerFlyableFallEvent event) {
        LivingFallData data = new LivingFallData(event.getEntity(), event.getDistance(), 0);
        UsefulSlime.onFall(data);
        event.setDistance(data.getDistance());
    }

    void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            if (BounceHandler.BOUNCING_ENTITIES.containsKey(player)) {
                BounceHandler.BOUNCING_ENTITIES.get(player).onPlayerTick(player);
            }
        }
    }

    void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        var entries = event.getEntries();

        if (tab == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            entries.putAfter(new ItemStack(Items.SPYGLASS), new ItemStack(ModItems.SLIME_SLING.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        else if (tab == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.SLIPPERY_SLIME_BLOCK_ITEM.get());
        }
        else if (tab == CreativeModeTabs.COMBAT) {
            entries.putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(ModItems.SLIME_HELMET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(ModItems.SLIME_HELMET.get()), new ItemStack(ModItems.SLIME_CHESTPLATE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(ModItems.SLIME_CHESTPLATE.get()), new ItemStack(ModItems.SLIME_LEGGINGS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(ModItems.SLIME_LEGGINGS.get()), new ItemStack(ModItems.SLIME_BOOTS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        else if (tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            entries.putBefore(new ItemStack(Items.MUSHROOM_STEW), new ItemStack(ModItems.JELLO.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
