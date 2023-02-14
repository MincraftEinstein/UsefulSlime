package einstein.usefulslime.items;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class SlimeArmorMaterial implements ArmorMaterial {

    public static final ArmorMaterial instance = new SlimeArmorMaterial();

    @Override
    public int getDurabilityForSlot(final EquipmentSlot slotIn) {
        return -1;
    }

    @Override
    public int getDefenseForSlot(final EquipmentSlot slotIn) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.SLIME_BLOCK_FALL;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
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
