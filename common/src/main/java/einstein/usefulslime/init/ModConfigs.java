package einstein.usefulslime.init;

import einstein.usefulslime.UsefulSlime;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@ConvertFrom(fileName = "usefulslime-common.toml")
@Translation(prefix = "config." + UsefulSlime.MOD_ID)
public class ModConfigs extends Config {

    public boolean hangClimbingDamagesSlimeHelmet = true;
    public boolean wallClimbingDamagesSlimeChestplateAndLeggings = true;
    public boolean bouncingDamagesSlimeBoots = true;
    @ValidatedInt.Restrict(max = 100, min = 0)
    public int maxSlipperySlimeBlockSpeed = 8;
    public boolean slimeBootSurfing = false;

    public ModConfigs() {
        super(UsefulSlime.loc("main"));
    }
}
