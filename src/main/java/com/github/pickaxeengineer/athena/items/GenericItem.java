package com.github.pickaxeengineer.athena.items;

import com.github.pickaxeengineer.athena.blocks.GenericBlock;
import com.github.pickaxeengineer.athena.utils.IItemRightClickHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class GenericItem extends Item {

    private final IItemRightClickHandler rightClickHandler;

    public GenericItem(Properties properties) {
        super(properties);
        this.rightClickHandler = null;
    }

    public GenericItem(@Nonnull Properties properties, @Nonnull IItemRightClickHandler handler) {
        super(properties);
        this.rightClickHandler = handler;
    }


    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
        if (rightClickHandler != null) {
            rightClickHandler.onRightClick(worldIn, playerIn, handIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static RegistryObject<GenericItem> register(@Nonnull String registryName, @Nonnull Item.Properties properties) {
        return AthenaItemRegistry.ITEMS.register(registryName, () -> new GenericItem(properties));
    }

    public static RegistryObject<GenericItem> register(@Nonnull String registryName, @Nonnull Item.Properties properties, @Nonnull IItemRightClickHandler handler) {
        return AthenaItemRegistry.ITEMS.register(registryName, () -> new GenericItem(properties, handler));
    }

    public static <T extends Item> RegistryObject<T> register(@Nonnull String registryName, @Nonnull Supplier<T> itemSupplier) {
        return AthenaItemRegistry.ITEMS.register(registryName, itemSupplier);
    }

}
