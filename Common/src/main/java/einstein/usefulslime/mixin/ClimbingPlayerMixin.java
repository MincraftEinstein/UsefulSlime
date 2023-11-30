package einstein.usefulslime.mixin;

import einstein.usefulslime.init.ModItems;
import einstein.usefulslime.util.VerticalCollider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class ClimbingPlayerMixin extends LivingEntity {

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    protected ClimbingPlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean onClimbable() {
        boolean canWallClimb = getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SLIME_LEGGINGS.get())
                && getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SLIME_CHESTPLATE.get())
                && horizontalCollision;

        boolean canHangClimb = getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SLIME_HELMET.get())
                && ((VerticalCollider) this).verticalCollisionAbove() && jumping;

        setNoGravity(canHangClimb);
        return super.onClimbable() || canWallClimb || canHangClimb;
    }
}
