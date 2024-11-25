package einstein.usefulslime;

import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.platform.NeoForgeRegistryHelper;
import einstein.usefulslime.util.BounceHandler;
import einstein.usefulslime.util.LivingFallData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerFlyableFallEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@Mod(UsefulSlime.MOD_ID)
public class UsefulSlimeNeoForge {

    public UsefulSlimeNeoForge(IEventBus eventBus) {
        UsefulSlime.init();
        eventBus.addListener(this::onBuildContents);
        eventBus.addListener(this::clientSetup);
        NeoForgeRegistryHelper.ITEMS.register(eventBus);
        NeoForgeRegistryHelper.BLOCKS.register(eventBus);
        NeoForgeRegistryHelper.ARMOR_MATERIAL.register(eventBus);
        NeoForge.EVENT_BUS.addListener(this::onFall);
        NeoForge.EVENT_BUS.addListener(this::onPlayerFlyFall);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(this::onServerStopped);
    }

    void clientSetup(FMLClientSetupEvent event) {
        UsefulSlime.clientSetup();
    }

    void onServerStopped(ServerStoppedEvent event) {
        UsefulSlime.onServerStopped(event.getServer());
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

    void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (BounceHandler.BOUNCING_ENTITIES.containsKey(player)) {
            BounceHandler.BOUNCING_ENTITIES.get(player).onPlayerTick(player);
        }
    }

    void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();

        if (tab.equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
            event.insertAfter(new ItemStack(Items.SPYGLASS), new ItemStack(ModItems.SLIME_SLING.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        else if (tab.equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.accept(ModItems.SLIPPERY_SLIME_BLOCK_ITEM.get());
            event.accept(ModItems.BOUNCY_SLIME_BLOCK_ITEM.get());
        }
        else if (tab.equals(CreativeModeTabs.COMBAT)) {
            event.insertAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(ModItems.SLIME_HELMET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ModItems.SLIME_HELMET.get()), new ItemStack(ModItems.SLIME_CHESTPLATE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ModItems.SLIME_CHESTPLATE.get()), new ItemStack(ModItems.SLIME_LEGGINGS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ModItems.SLIME_LEGGINGS.get()), new ItemStack(ModItems.SLIME_BOOTS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        else if (tab.equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            event.insertBefore(new ItemStack(Items.MUSHROOM_STEW), new ItemStack(ModItems.JELLO.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
