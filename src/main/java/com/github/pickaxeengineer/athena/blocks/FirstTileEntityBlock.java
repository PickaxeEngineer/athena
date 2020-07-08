package com.github.pickaxeengineer.athena.blocks;


import com.github.pickaxeengineer.athena.tiles.FirstTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


/**
 * Based on https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe20_tileentity_data/
 */
public class FirstTileEntityBlock extends Block {

    public FirstTileEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    private final int TIMER_COUNTDOWN_TICKS = 20 * 10; // duration of the countdown, in ticks = 10 seconds

    /**
     * Returns NEW te instance when placed. Called client sided (place or load)
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FirstTileEntity();
    }

    // Called just after the player places a block.  Start the tileEntity's timer
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof FirstTileEntity) { // prevent a crash if not the right type, or is null
            FirstTileEntity tileEntityData = (FirstTileEntity)tileentity;
            tileEntityData.setTicksLeftTillDisappear(TIMER_COUNTDOWN_TICKS);
        }
    }

}
