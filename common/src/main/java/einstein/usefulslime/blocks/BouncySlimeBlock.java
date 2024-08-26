package einstein.usefulslime.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BouncySlimeBlock extends HalfTransparentBlock {

    public BouncySlimeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".warning").withStyle(ChatFormatting.RED));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, f);
            return;
        }
        entity.causeFallDamage(f, 0, level.damageSources().fall());
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter getter, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(getter, entity);
            return;
        }
        bounceUp(entity);
    }

    private void bounceUp(Entity entity) {
        Vec3 movement = entity.getDeltaMovement();
        if (movement.y < -0.08) {
            double force = entity instanceof LivingEntity ? 1 : 0.8;
            entity.setDeltaMovement(movement.x, -movement.y * force * 1.5, movement.z);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        double ySpeed = Math.abs(entity.getDeltaMovement().y);
        if (ySpeed < 0.1 && !entity.isSteppingCarefully()) {
            double speedModifier = 0.4 + ySpeed * 0.2;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(speedModifier, 1, speedModifier));
        }

        super.stepOn(level, pos, state, entity);
    }
}
