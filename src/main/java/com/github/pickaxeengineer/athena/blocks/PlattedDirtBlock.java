package com.github.pickaxeengineer.athena.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PlattedDirtBlock extends GenericBlock {


    public PlattedDirtBlock(Properties properties) {
        super(properties);
    }


    private static final Vec3d SHAPE_MIN = new Vec3d(0,0,0);
    private static final Vec3d SHAPE_MAX = new Vec3d(16,12,16);

    private static final VoxelShape SHAPE = Block.makeCuboidShape(SHAPE_MIN.getX(), SHAPE_MIN.getY(), SHAPE_MIN.getZ(),
            SHAPE_MAX.getX(), SHAPE_MAX.getY(), SHAPE_MAX.getZ());

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
