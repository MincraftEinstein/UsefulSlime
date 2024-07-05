package einstein.usefulslime.items;

import einstein.usefulslime.init.ModArmorMaterials;
import net.minecraft.world.item.ArmorItem;

public class SlimeArmor extends ArmorItem {

    public SlimeArmor(Properties properties, Type type) {
        super(ModArmorMaterials.SLIME_MATERIAL.get(), type, properties);
    }
}
