package einstein.usefulslime.platform;

import einstein.usefulslime.platform.services.IPlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.FakePlayer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isFakePlayer(LivingEntity entity) {
        return entity instanceof FakePlayer;
    }

}