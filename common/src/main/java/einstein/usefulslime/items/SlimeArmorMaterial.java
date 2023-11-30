package einstein.usefulslime.items;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class SlimeArmorMaterial implements ArmorMaterial {

    public static final ArmorMaterial INSTANCE = new SlimeArmorMaterial();

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 110;
            case CHESTPLATE -> 160;
            case LEGGINGS -> 150;
            case BOOTS -> 130;
        };
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 2;
            case CHESTPLATE -> 5;
            case LEGGINGS -> 4;
            case BOOTS -> 1;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return 9;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.SLIME_BLOCK_FALL;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.SLIME_BALL);
    }

    @Override
    public String getName() {
        return UsefulSlime.MOD_ID + ":slime";
    }

    @Override
    public float getToughness() {
        return 0.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
