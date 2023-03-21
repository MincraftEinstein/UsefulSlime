package einstein.usefulslime.items;

import net.minecraft.world.item.ArmorItem;

public class SlimeArmor extends ArmorItem {

    public SlimeArmor(Properties properties, Type slot) {
        super(SlimeArmorMaterial.INSTANCE, slot, properties);
    }
}
