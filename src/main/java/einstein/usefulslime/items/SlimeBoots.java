package einstein.usefulslime.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class SlimeBoots extends ArmorItem {
    public SlimeBoots(Properties properties) {
        super(SlimeArmorMaterial.instance, EquipmentSlot.FEET, properties);
    }
}
