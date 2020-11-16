package einstein.usefulslime.items;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class SlimeArmorMaterial implements IArmorMaterial
{
    public static IArmorMaterial instance = new SlimeArmorMaterial();
    
    public int getDurability(final EquipmentSlotType slotIn) {
        return -1;
    }
    
    public int getDamageReductionAmount(final EquipmentSlotType slotIn) {
        return 0;
    }
    
    public int getEnchantability() {
        return 0;
    }
    
    public SoundEvent getSoundEvent() {
        return SoundEvents.BLOCK_SLIME_BLOCK_FALL;
    }
    
    public Ingredient getRepairMaterial() {
        return null;
    }
    
    public String getName() {
        return UsefulSlime.MODID + ":slime";
    }
    
    public float getToughness() {
        return 0.0f;
    }
    
	@Override
	public float func_230304_f_() {
		// TODO Auto-generated method stub
		return 0;
	}
}
