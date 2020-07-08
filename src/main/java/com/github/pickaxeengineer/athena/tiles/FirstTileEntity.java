package com.github.pickaxeengineer.athena.tiles;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NBTTypes;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FirstTileEntity extends TileEntity implements ITickableTileEntity {


    public FirstTileEntity() {
        super(AthenaTERegistry.FIRST_TE.get());
    }

    private final int INVALID_VALUE = -1;
    private int ticksLeftTillDisappear = INVALID_VALUE;

    public void setTicksLeftTillDisappear(int ticks){
        ticksLeftTillDisappear = ticks;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 42, nbt); // 42 arbitrary number, not required for modded
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound); // Required for default stuff
        // feature
        compound.putInt("ticksLeft", ticksLeftTillDisappear);
        // return
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound); // required
        // Never trust the NBT
        int ticks = INVALID_VALUE;
        final byte INT_NBT = IntNBT.valueOf(0).getId();
        if(compound.contains("ticksLeft", INT_NBT)){
            ticks = compound.getInt("ticksLeft");
            // sanity check
            if( ticks < 0){
                ticks = INVALID_VALUE;
            }
        }
        ticksLeftTillDisappear = ticks;
    }


    @Override
    public void tick() {
        if(!hasWorld() || getWorld().isRemote() ){
            return; // Nothing clientside
        }
        if(ticksLeftTillDisappear == INVALID_VALUE){
            return; // do nothing in case of invalidiness
        }
        ServerWorld world = (ServerWorld)getWorld();
        --ticksLeftTillDisappear;
        // would use markDirty() in case for updates to client
        if(ticksLeftTillDisappear > 0){
            return;
        }

        world.setBlockState(this.pos, Blocks.DIAMOND_ORE.getDefaultState());
    }
}
