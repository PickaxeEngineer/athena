package com.github.pickaxeengineer.athena.itemstack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BagItemStackHandler extends ItemStackHandler {

    public static final int MIN_SLOTS = 1;
    public static final int MAX_SLOTS = 16;
    private boolean isDirty = true;

    public BagItemStackHandler(int size) {
        super(MathHelper.clamp(size, MIN_SLOTS, MAX_SLOTS));
        // sanity check for size really in range
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= MAX_SLOTS) {
            throw new IllegalArgumentException("Invalid slot number:" + slot);
        }
        if (stack.isEmpty()) {
            return false;
        } else {
            return true; // we allow all types of items to be in this bag
        }
    }

    public int getNumberOfEmptySlots() {
        int emptySlots = 0;
        for (int i = 0; i < getSlots(); i++) {
            if (getStackInSlot(i) == ItemStack.EMPTY) {
                emptySlots++;
            }
        }
        return emptySlots;
    }

    public boolean isDirty() {
        boolean cur = isDirty;
        isDirty = false;
        return cur;
    }

    @Override
    protected void onContentsChanged(int slot) {
        isDirty = true;
    }
}
