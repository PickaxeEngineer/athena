package com.github.pickaxeengineer.athena.items;

import com.github.pickaxeengineer.athena.capabilities.BagCapabilityProvider;
import com.github.pickaxeengineer.athena.containers.BagContainerProvider;
import com.github.pickaxeengineer.athena.itemstack.BagItemStackHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BagItem extends Item {

    public static final int NO_SLOTS = 16;
    public static final String DIRTY_COUNTER_TAG_NAME = "dirtyCounter";

    public BagItem(ItemGroup misc) {
        super(new Properties().group(misc).maxStackSize(1));

        addPropertyOverride(new ResourceLocation("fullness"), BagItem::getFullnessPropertyOverride);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            INamedContainerProvider containerProvider = new BagContainerProvider(this, stack);
            NetworkHooks.openGui((ServerPlayerEntity)playerIn, containerProvider, (packetBuffer -> packetBuffer.writeInt(NO_SLOTS)));
            // We use the packetBuffer to send the bag size; not necessary since it's always 16, but just for illustration purposes
        }

        return ActionResult.resultSuccess(stack);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote()) {
            // We're on client --> do nothing
            return ActionResultType.PASS;
        }

        BlockPos pos = context.getPos();
        Direction side = context.getFace();
        ItemStack itemStack = context.getItem(); // Not sure why stack is not used here

        if(!(itemStack.getItem() instanceof BagItem)){
            throw new AssertionError("Unepxted type");
        }
        BagItem bag = (BagItem)itemStack.getItem();

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity == null) {
            return ActionResultType.PASS;
        }

        IItemHandler tileInv;
        LazyOptional<IItemHandler> cap = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
        if(cap.isPresent()){
            tileInv = cap.orElseThrow(AssertionError::new);
        }else if(tileEntity instanceof IInventory){
            tileInv = new InvWrapper((IInventory)tileEntity);
        }else{
            return ActionResultType.FAIL; // No inventory
        }


        // try to place contents
        BagItemStackHandler itemStackHandler = bag.getItemStackHandler(itemStack);

        for(int i=0; i<itemStackHandler.getSlots();i++){
            ItemStack is = itemStackHandler.getStackInSlot(i);
            ItemStack remaining = ItemHandlerHelper.insertItemStacked(tileInv,is,false);
            itemStackHandler.setStackInSlot(i, remaining);
        }
        tileEntity.markDirty();

        CompoundNBT nbt = itemStack.getOrCreateTag();
        int dirtyCounter = nbt.getInt(DIRTY_COUNTER_TAG_NAME);
        nbt.putInt(DIRTY_COUNTER_TAG_NAME, dirtyCounter+1);
        itemStack.setTag(nbt);

        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BagCapabilityProvider();
    }

    public static BagItemStackHandler getItemStackHandler(ItemStack itemStack) {
        IItemHandler bag = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if(bag == null || !(bag instanceof BagItemStackHandler)){
            // Would be a logging statement here
            return new BagItemStackHandler(BagItemStackHandler.MAX_SLOTS);
        }
        return (BagItemStackHandler)bag;
    }

    private static float getFullnessPropertyOverride(ItemStack itemStack, World world, LivingEntity livingEntity) {
        BagItemStackHandler bag = getItemStackHandler(itemStack);
        float fractionEmpty = bag.getNumberOfEmptySlots() / (float)bag.getSlots();
        return 1f-fractionEmpty;
    }


}
