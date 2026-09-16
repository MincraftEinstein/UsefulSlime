package einstein.usefulslime.mixin;

import einstein.usefulslime.util.RegistryAliases;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;

// The fact that this actually works is so messed up
@Mixin(Registry.class)
public interface RegistryMixin extends RegistryAliases {
}
