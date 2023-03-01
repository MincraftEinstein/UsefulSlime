package einstein.usefulslime.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class SlimeArmor extends ArmorItem {

    public SlimeArmor(Properties properties, EquipmentSlot slot) {
        super(SlimeArmorMaterial.instance, slot, properties);
    }
}
