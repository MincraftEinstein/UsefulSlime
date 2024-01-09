package einstein.usefulslime.init;

import einstein.usefulslime.UsefulSlime;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModCommonConfigs {

    private static final Pair<ModCommonConfigs, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(ModCommonConfigs::new);
    public static final ModCommonConfigs INSTANCE = SPEC_PAIR.getLeft();
    public static final ForgeConfigSpec SPEC = SPEC_PAIR.getRight();

    public final ForgeConfigSpec.BooleanValue hangClimbingDamagesSlimeHelmet;
    public final ForgeConfigSpec.BooleanValue wallClimbingDamagesSlimeChestplateAndLeggings;
    public final ForgeConfigSpec.BooleanValue bouncingDamagesSlimeBoots;

    public ModCommonConfigs(ForgeConfigSpec.Builder builder) {
        hangClimbingDamagesSlimeHelmet = builder
                .comment("Should the Slime Helmet be damaged by climbing on the ceiling")
                .translation(key("hang_climbing_damages_slime_helmet"))
                .define("hangClimbingDamagesSlimeHelmet", true);

        wallClimbingDamagesSlimeChestplateAndLeggings = builder
                .comment("Should Slime Chestplates and Slime Leggings be damaged by wall climbing (not inculding including already climbable blocks)")
                .translation(key("wall_climbing_damages_slime_chestplate_and_leggings"))
                .define("wallClimbingDamagesSlimeChestplateAndLeggings", true);

        bouncingDamagesSlimeBoots = builder
                .comment("Should Slime Boots be damaged by bouncing")
                .translation(key("bouncing_damages_slime_boots"))
                .define("bouncingDamagesSlimeBoots", true);
    }

    private static String key(String path) {
        return "config." + UsefulSlime.MOD_ID + "." + path;
    }
}
