package com.github.pickaxeengineer.athena.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeCube;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;

import static net.minecraft.util.Direction.UP;

public class PipeBlock extends GenericBlock {


    public PipeBlock(Properties properties) {
        super(properties);
        BlockState defaultBlockState = getStateContainer().getBaseState()
                .with(BlockStateProperties.UP, false)
                .with(BlockStateProperties.DOWN, false)
                .with(BlockStateProperties.NORTH, false)
                .with(BlockStateProperties.EAST, false)
                .with(BlockStateProperties.SOUTH, false)
                .with(BlockStateProperties.WEST, false);

        this.setDefaultState(defaultBlockState);
        this.initShapeCache();
    }

    public static final VoxelShape CORE_SHAPE = Block.makeCuboidShape(5.5, 5.5, 5.5, 10.5, 10.5, 10.5);
    private static final VoxelShape LINK_UP_SHAPE =
            Block.makeCuboidShape(7.5, 10.5, 7.5, 8.5, 16, 8.5);
    private static final VoxelShape LINK_DOWN_SHAPE =
            Block.makeCuboidShape(7.5, 0.0, 7.5, 8.5, 5.5, 8.5);
    private static final VoxelShape LINK_WEST_SHAPE =
            Block.makeCuboidShape(0, 7.5, 7.5, 5.5, 8.5, 8.5);
    private static final VoxelShape LINK_EAST_SHAPE =
            Block.makeCuboidShape(10.5, 7.5, 7.5, 16.0, 8.5, 8.5);
    private static final VoxelShape LINK_NORTH_SHAPE =
            Block.makeCuboidShape(7.5, 7.5, 0.0, 8.5, 8.5, 5.5);
    private static final VoxelShape LINK_SOUTH_SHAPE =
            Block.makeCuboidShape(7.5, 7.5, 10.5, 8.5, 8.5, 16.0);

    private void initShapeCache() {
        for (BlockState bs : getStateContainer().getValidStates()) {
            VoxelShape combinedShape = CORE_SHAPE;
            if (bs.get(BlockStateProperties.UP)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_UP_SHAPE);
            }
            if (bs.get(BlockStateProperties.DOWN)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_DOWN_SHAPE);
            }
            if (bs.get(BlockStateProperties.WEST)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_WEST_SHAPE);
            }
            if (bs.get(BlockStateProperties.EAST)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_EAST_SHAPE);
            }
            if (bs.get(BlockStateProperties.NORTH)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_NORTH_SHAPE);
            }
            if (bs.get(BlockStateProperties.SOUTH)) {
                combinedShape = VoxelShapes.or(combinedShape, LINK_SOUTH_SHAPE);
            }
            SHAPE_CACHE.put(bs, combinedShape);
        }

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World w = context.getWorld();
        BlockPos p = context.getPos();

        BlockState state = getDefaultState();
        state = setConnections(w, p, state);
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {

        switch (facing) {
            case UP:
                return stateIn.with(BlockStateProperties.UP, doesConnect(worldIn, currentPos, facing));
            case DOWN:
                return stateIn.with(BlockStateProperties.DOWN, doesConnect(worldIn, currentPos, facing));
            case NORTH:
                return stateIn.with(BlockStateProperties.NORTH, doesConnect(worldIn, currentPos, facing));
            case EAST:
                return stateIn.with(BlockStateProperties.EAST, doesConnect(worldIn, currentPos, facing));
            case SOUTH:
                return stateIn.with(BlockStateProperties.SOUTH, doesConnect(worldIn, currentPos, facing));
            case WEST:
                return stateIn.with(BlockStateProperties.WEST, doesConnect(worldIn, currentPos, facing));
            default:
                return stateIn;
        }
    }

    private BlockState setConnections(World w, BlockPos p, BlockState s) {
        return s.with(BlockStateProperties.UP, doesConnect(w, p, UP))
                .with(BlockStateProperties.DOWN, doesConnect(w, p, Direction.DOWN))
                .with(BlockStateProperties.NORTH, doesConnect(w, p, Direction.NORTH))
                .with(BlockStateProperties.EAST, doesConnect(w, p, Direction.EAST))
                .with(BlockStateProperties.SOUTH, doesConnect(w, p, Direction.SOUTH))
                .with(BlockStateProperties.WEST, doesConnect(w, p, Direction.WEST));
    }

    private boolean doesConnect(IBlockReader reader, BlockPos pos, Direction dir) {
        BlockPos nPos = pos.offset(dir);
        BlockState nState = reader.getBlockState(nPos);
        Block nBlock = nState.getBlock();

        if (nBlock == Blocks.BARRIER) {
            return false;
        } else if (nBlock instanceof PipeBlock) {
            return true;
        } else if (cannotAttach(nBlock)) {
            return false;
        } else {
            return nState.isSolidSide(reader, nPos, dir.getOpposite());
        }

    }

    private static final HashMap<BlockState, VoxelShape> SHAPE_CACHE = new HashMap<>();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_CACHE.get(state);
        return shape != null ? shape : VoxelShapes.fullCube(); // being very cautious, shouldn't being null
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = SHAPE_CACHE.get(state);
        return shape != null ? shape : VoxelShapes.fullCube(); // being very cautious, shouldn't being null
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(
                BlockStateProperties.UP,
                BlockStateProperties.DOWN,
                BlockStateProperties.NORTH,
                BlockStateProperties.EAST,
                BlockStateProperties.SOUTH,
                BlockStateProperties.WEST
        );
    }
}
