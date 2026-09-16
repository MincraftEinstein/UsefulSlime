package einstein.usefulslime.platform;

import einstein.usefulslime.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.LivingEntity;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isFakePlayer(LivingEntity entity) {
        return entity instanceof FakePlayer;
    }

}