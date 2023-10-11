package einstein.usefulslime.items;

import net.minecraft.world.item.ArmorItem;

public class SlimeArmor extends ArmorItem {

    public SlimeArmor(Properties properties, Type type) {
        super(SlimeArmorMaterial.INSTANCE, type, properties);
    }
}
