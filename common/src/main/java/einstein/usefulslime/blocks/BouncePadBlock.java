package einstein.usefulslime.blocks;

import einstein.usefulslime.UsefulSlime;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BouncePadBlock extends Block implements SimpleWaterloggedBlock {

    public static final IntegerProperty DIRECTION = IntegerProperty.create("direction", 0, 7);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);
    protected static final VoxelShape TOP = Block.box(2, 8, 2, 14, 10, 14);
    protected static final VoxelShape[] SHAPES = new VoxelShape[] {
            Shapes.or(BOTTOM, TOP, Block.box(6, 8, 1, 10, 11, 5)), // North
            Shapes.or(BOTTOM, TOP, Block.box(11, 8, 1, 15, 11, 5)), // North-east
            Shapes.or(BOTTOM, TOP, Block.box(11, 8, 6, 15, 11, 10)), // East
            Shapes.or(BOTTOM, TOP, Block.box(11, 8, 11, 15, 11, 15)), // South-east
            Shapes.or(BOTTOM, TOP, Block.box(6, 8, 11, 10, 11, 15)), // South
            Shapes.or(BOTTOM, TOP, Block.box(1, 8, 11, 5, 11, 15)), // South-west
            Shapes.or(BOTTOM, TOP, Block.box(1, 8, 6, 5, 11, 10)), // West
            Shapes.or(BOTTOM, TOP, Block.box(1, 8, 1, 5, 11, 5)), // North-west
    };

    public BouncePadBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(DIRECTION, 0).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(DIRECTION)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(DIRECTION, Mth.floor(((180 + context.getRotation()) * 8 / 360) + 0.5F) & 7);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(DIRECTION, rotation.rotate(state.getValue(DIRECTION), 8));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(DIRECTION, mirror.mirror(state.getValue(DIRECTION), 8));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        double moveX = 0;
        double moveZ = 0;
        double force = 0.25F;
        if (!entity.isCrouching()) {
            switch (state.getValue(DIRECTION)) {
                case 1 -> { // North-east
                    moveZ -= force;
                    moveX += force;
                }
                case 2 -> { // East
                    moveX += force;
                }
                case 3 -> { // South-east
                    moveZ += force;
                    moveX += force;
                }
                case 4 -> { // South
                    moveZ += force;
                }
                case 5 -> { // South-west
                    moveZ += force;
                    moveX -= force;
                }
                case 6 -> { // West
                    moveX -= force;
                }
                case 7 -> { // North-west
                    moveZ -= force;
                    moveX -= force;
                }
                default -> { // North
                    moveZ -= force;
                }
            }
            entity.push(moveX, 0.5F, moveZ);

            if (UsefulSlime.CONFIGS.bouncePadBounceSound) {
                level.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
                        soundType.getStepSound(), SoundSource.BLOCKS,
                        soundType.getVolume() / 2, soundType.getPitch() * 0.65F, false
                );
            }
        }

        if (entity instanceof ItemEntity) {
            ++entity.yOld;
            entity.push(moveX, 0.5F, moveZ);
        }
        entity.resetFallDistance();
    }
}
