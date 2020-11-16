package einstein.usefulslime.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;

public class SlimeBoots extends ArmorItem
{
    public SlimeBoots(Properties properties) {
        super(SlimeArmorMaterial.instance, EquipmentSlotType.FEET, properties);
    }
}
