package com.github.pickaxeengineer.athena.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class GenericTEBlock<T extends TileEntity> extends GenericBlock {

    private final Supplier<T> teSupplier;

    public GenericTEBlock(Properties properties, Supplier<T> teSupplier) {
        super(properties);
        this.teSupplier = teSupplier;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return teSupplier.get();
    }
}
