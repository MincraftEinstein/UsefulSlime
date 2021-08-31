package einstein.usefulslime.items;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class SlimeArmorMaterial implements ArmorMaterial
{
    public static ArmorMaterial instance = new SlimeArmorMaterial();
    
    public int getDurabilityForSlot(final EquipmentSlot slotIn) {
        return -1;
    }
    
    public int getDefenseForSlot(final EquipmentSlot slotIn) {
        return 0;
    }
    
    public int getEnchantmentValue() {
        return 0;
    }
    
    public SoundEvent getEquipSound() {
        return SoundEvents.SLIME_BLOCK_FALL;
    }
    
    public Ingredient getRepairIngredient() {
        return null;
    }
    
    public String getName() {
        return UsefulSlime.MODID + ":slime";
    }
    
    public float getToughness() {
        return 0.0f;
    }

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}
