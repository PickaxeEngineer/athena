package com.github.pickaxeengineer.athena.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Functional interface for lazy coding / lambda usage
 */
@FunctionalInterface
public interface IItemRightClickHandler {

    void onRightClick(@Nonnull  World worldIn, @Nonnull PlayerEntity playerIn,@Nonnull Hand handIn);
}
