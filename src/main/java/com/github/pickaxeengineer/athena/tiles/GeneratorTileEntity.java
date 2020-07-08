package com.github.pickaxeengineer.athena.tiles;

import com.github.pickaxeengineer.athena.capabilities.energy.ForgeEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GeneratorTileEntity extends TileEntity implements ITickableTileEntity {

    public static final int TRANSFER_FE = 40;
    public static final int MAX_ENERGY = 100_000;
    public final ForgeEnergyStorage energy = new ForgeEnergyStorage(MAX_ENERGY, 0, 100);

    private static final String ENERGY_TAG = "energy";

    private final LazyOptional<EnergyStorage> energyCapability = LazyOptional.of(() -> this.energy);

    private int lastEnergy = -1;

    public GeneratorTileEntity() {
        super(AthenaTERegistry.GENERATOR_TE.get());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) {
            return;
        }
        // generate energy out of thin air (solar? Deep Convolutional Energy (DCE))
        // Allways fill up to the brim
        energy.setEnergy(MAX_ENERGY);
        // check for each side whether we can give energy
        if (energy.getEnergyStored() >= TRANSFER_FE) {
            // can give energy on every side
            for (Direction dir : Direction.values()) {
                final TileEntity neighborTe = world.getTileEntity(pos.offset(dir));
                if (neighborTe == null) {
                    continue;
                }
                neighborTe.getCapability(CapabilityEnergy.ENERGY, dir).ifPresent(otherEnergy -> {
                    if (!otherEnergy.canReceive()) {
                        // cannot receive energy, how sad
                        return;
                    }
                    // try to give as much energy as the other can handle receive
                    energy.extractEnergy(otherEnergy.receiveEnergy(energy.extractEnergy(TRANSFER_FE, true), false), false);
                });
            }
        }
        // send packages and sync when energy changed
        if (lastEnergy != energy.getEnergyStored()) {
            this.markDirty();
            // notify client
            final BlockState state = this.getBlockState();
            // don't know what flag 2 does
            world.notifyBlockUpdate(pos, state, state, 2);
            lastEnergy = energy.getEnergyStored();
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        energy.setEnergy(pkt.getNbtCompound().getInt(ENERGY_TAG));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        // sets the energy level based on our internal storage, read from nbt
        if (world != null && !world.isRemote) {
            lastEnergy = energy.getEnergyStored();
        }
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.energy.setEnergy(compound.getInt(ENERGY_TAG));
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt(ENERGY_TAG, energy.getEnergyStored());
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        final CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(ENERGY_TAG, energy.getEnergyStored());
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void remove() {
        super.remove();

        // invalidate capabilites to invalidate cached references (by other mods)
        energyCapability.invalidate();
    }


}
