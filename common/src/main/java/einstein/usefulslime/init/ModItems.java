package einstein.usefulslime.init;

import einstein.usefulslime.items.SlimeArmor;
import einstein.usefulslime.items.SlimeSlingItem;
import einstein.usefulslime.platform.Services;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {

    public static final Supplier<Item> SLIPPERY_SLIME_BLOCK_ITEM = register("slippery_slime_block", () -> new BlockItem(ModBlocks.SLIPPERY_SLIME_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SLIME_SLING = register("slime_sling", () -> new SlimeSlingItem(new Item.Properties().durability(64)));
    public static final Supplier<Item> SLIME_BOOTS = register("slime_boots", () -> new SlimeArmor(new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10)), ArmorItem.Type.BOOTS));
    public static final Supplier<Item> SLIME_LEGGINGS = register("slime_leggings", () -> new SlimeArmor(new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10)), ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> SLIME_CHESTPLATE = register("slime_chestplate", () -> new SlimeArmor(new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10)), ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> SLIME_HELMET = register("slime_helmet", () -> new SlimeArmor(new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10)), ArmorItem.Type.HELMET));
    public static final Supplier<Item> JELLO = register("jello", () -> new Item(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).effect(new MobEffectInstance(MobEffects.JUMP, 600, 2), 1).build())));

    public static void init() {
    }

    private static <T extends Item> Supplier<Item> register(String name, Supplier<T> supplier) {
        return Services.REGISTRY.registerItem(name, supplier);
    }
}
