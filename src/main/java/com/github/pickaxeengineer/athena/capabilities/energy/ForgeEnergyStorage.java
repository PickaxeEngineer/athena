package com.github.pickaxeengineer.athena.capabilities.energy;

import net.minecraftforge.energy.EnergyStorage;

public class ForgeEnergyStorage extends EnergyStorage {
    public ForgeEnergyStorage(int capacity) {
        super(capacity);
    }

    public ForgeEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ForgeEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public ForgeEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    /**
     * Sets the energy to the desired level (but not more than the storage can contain)
     * @param maxSet
     * @return The amount that was set
     */
    public int setEnergy(final int maxSet){
        return this.energy = Math.min(this.capacity, maxSet);
    }
}
