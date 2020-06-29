package com.github.pickaxeengineer.athena.containers;

import com.github.pickaxeengineer.athena.items.BagItem;
import com.github.pickaxeengineer.athena.itemstack.BagItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.SlotItemHandler;


public class BagContainer extends Container {


    private final ItemStack stackInHand;
    private final BagItemStackHandler handler;

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int BAG_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int MAX_EXPECTED_BAG_SLOT_COUNT = 16;

    public static final int BAG_INVENTORY_YPOS = 26;  // the ContainerScreenFlowerBag needs to know these so it can tell where to draw the Titles
    public static final int PLAYER_INVENTORY_YPOS = 84;

    // Must be in accordance to sprite
    private final int SLOT_X_SPACING = 18;
    private final int SLOT_Y_SPACING = 18;
    private final int HOTBAR_XPOS = 8;
    private final int HOTBAR_YPOS = 142;

    private final int PLAYER_INVENTORY_XPOS = 8;

    private final int BAG_SLOTS_PER_ROW = 8;
    private final int BAG_INVENTORY_XPOS = 17;

    private BagContainer(int windowID, PlayerInventory playerInventory, BagItemStackHandler bagContents, ItemStack theBag) {
        super(AthenaContainerRegistry.bag, windowID);
        stackInHand = theBag;
        handler = bagContents;

        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            addSlot(new Slot(playerInventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slot = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(playerInventory, slot, xpos, ypos));
            }
        }

        int bagSlots = handler.getSlots();
        System.out.println("getSlots(): "+bagSlots);
        if (bagSlots < 1 || bagSlots > MAX_EXPECTED_BAG_SLOT_COUNT) {
            // actually a serious problem
            System.out.println("Had to fix bag slots");
            bagSlots = MathHelper.clamp(bagSlots, 1, MAX_EXPECTED_BAG_SLOT_COUNT);
        }

        for (int bagSlot = 0; bagSlot < bagSlots; bagSlot++) {
            int bagRow = bagSlot / BAG_SLOTS_PER_ROW;
            int bagCol = bagSlot % BAG_SLOTS_PER_ROW;
            int xpos = BAG_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
            int ypos = BAG_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
            addSlot(new SlotItemHandler(handler, bagSlot, xpos, ypos));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        ItemStack main = playerIn.getHeldItemMainhand();
        ItemStack off = playerIn.getHeldItemOffhand();

        return (!main.isEmpty() && main == stackInHand) || (!off.isEmpty() && off == stackInHand);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot sourceSlot = inventorySlots.get(index);
        if(sourceSlot == null || !sourceSlot.getHasStack()){
            return ItemStack.EMPTY;
        }
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack sourceStackCopy = sourceStack.copy();
        final int BAG_SLOT_COUNT = handler.getSlots();

        if(index >= VANILLA_FIRST_SLOT_INDEX && index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT){
            if(!mergeItemStack(sourceStack, BAG_INVENTORY_FIRST_SLOT_INDEX, BAG_INVENTORY_FIRST_SLOT_INDEX+BAG_SLOT_COUNT, false)){
                return ItemStack.EMPTY;
            }
        }else if(index >= BAG_INVENTORY_FIRST_SLOT_INDEX && index < BAG_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT){
            if(!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT ,false)){
                return ItemStack.EMPTY;
            }
        }else {
            return ItemStack.EMPTY;
        }

        if(sourceStack.getCount() == 0){
            sourceSlot.putStack(ItemStack.EMPTY);
        }else{
            sourceSlot.onSlotChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return sourceStackCopy;
    }

    @Override
    public void detectAndSendChanges() {
        if(handler.isDirty()){
            CompoundNBT nbt = stackInHand.getOrCreateTag();
            int dirtyCounter = nbt.getInt(BagItem.DIRTY_COUNTER_TAG_NAME);
            nbt.putInt(BagItem.DIRTY_COUNTER_TAG_NAME, dirtyCounter+1);
            stackInHand.setTag(nbt);
        }
        super.detectAndSendChanges();
    }

    public static BagContainer createContainerServerSide(final int windowID, final PlayerInventory playerInventory, BagItemStackHandler bagContents, ItemStack theBag) {
        return new BagContainer(windowID, playerInventory, bagContents, theBag);
    }

    public static BagContainer createContainerClientSide(final int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {
        int noSlots = extraData.readInt();

        // Unsafe, we do not ensure that noSlots is within range
        BagItemStackHandler handler = new BagItemStackHandler(noSlots);
        return new BagContainer(windowID, playerInventory, handler, ItemStack.EMPTY);
    }


}
