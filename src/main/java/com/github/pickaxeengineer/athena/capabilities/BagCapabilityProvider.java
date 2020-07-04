package com.github.pickaxeengineer.athena.capabilities;

import com.github.pickaxeengineer.athena.itemstack.BagItemStackHandler;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BagCapabilityProvider implements ICapabilitySerializable<INBT> {

    private final Direction NO_SPECIFIC_SIDE = null;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap){
            return (LazyOptional<T>) lazyInitialisationSupplier;
        }else{
            return LazyOptional.empty();
        }
    }


    @Nonnull
    private BagItemStackHandler getCachedHandler(){
        if(handler == null){
            handler = new BagItemStackHandler(MAX_NO_SLOTS);
        }
        return handler;
    }

    public static final int MAX_NO_SLOTS = 16;

    private BagItemStackHandler handler;

    private final LazyOptional<IItemHandler> lazyInitialisationSupplier = LazyOptional.of(this::getCachedHandler);

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedHandler(), NO_SPECIFIC_SIDE);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedHandler(), NO_SPECIFIC_SIDE, nbt);
    }
}
