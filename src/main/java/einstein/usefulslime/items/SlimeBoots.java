package einstein.usefulslime.items;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class SlimeBoots extends ItemArmor implements IHasModel {

	public SlimeBoots(String name, ArmorMaterial materialIn, int renderIndexIn) {
		super(materialIn, renderIndexIn, EntityEquipmentSlot.FEET);
		setUnlocalizedName(UsefulSlime.MODID + "." + name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		UsefulSlime.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
