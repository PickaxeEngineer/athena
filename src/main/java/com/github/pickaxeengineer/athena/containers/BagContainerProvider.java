package com.github.pickaxeengineer.athena.containers;

import com.github.pickaxeengineer.athena.items.BagItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class BagContainerProvider implements INamedContainerProvider {
    private BagItem bagItem;
    private ItemStack stack;

    public BagContainerProvider(BagItem bagItem, ItemStack stack) {
        this.bagItem = bagItem;
        this.stack = stack;
    }

    @Override
    public ITextComponent getDisplayName() {
        return stack.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        BagContainer newContainer = BagContainer.createContainerServerSide(windowId, playerInventory, bagItem.getItemStackHandler(stack),
                stack);
        return newContainer;
    }
}
