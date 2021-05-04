package einstein.usefulslime.init;

import java.util.ArrayList;
import java.util.List;

import einstein.usefulslime.UsefulSlime;
import einstein.usefulslime.items.SlimeBoots;
import einstein.usefulslime.items.Slimesling;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final ArmorMaterial SLIME_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("slime_armor_material", UsefulSlime.MODID + ":slime", -1, new int[] {0, 0, 0, 0}, 0, SoundEvents.BLOCK_SLIME_FALL, 0);
	public static final Item SLIMESLING = new Slimesling("slimesling");
	public static final Item SLIME_BOOTS = new SlimeBoots("slime_boots", SLIME_ARMOR_MATERIAL, 1);
}
