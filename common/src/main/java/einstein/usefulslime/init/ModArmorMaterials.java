package einstein.usefulslime.init;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.platform.Services;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final Supplier<Holder<ArmorMaterial>> SLIME_MATERIAL = Services.REGISTRY.registerArmorMaterial("slime", () -> new ArmorMaterial(Util.make(new HashMap<>(), map -> {
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.BOOTS, 1);
    }), 9, BuiltInRegistries.SOUND_EVENT.getHolder(ResourceLocation.withDefaultNamespace("block.slime_block.fall")).orElseThrow(),
            () -> Ingredient.of(Items.SLIME_BALL), List.of(new ArmorMaterial.Layer(UsefulSlime.loc("slime"))), 0, 0));

    public static void init() {
    }
}
