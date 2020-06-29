package com.github.pickaxeengineer.athena.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * Generic basic block. Class usable for all blocks that are not special and are cuboid
 */
public class GenericBlock extends Block {

    public GenericBlock(Properties properties) {
        super(properties);
    }

    public static RegistryObject<GenericBlock> register(String registryName, Properties blockProperties, ItemGroup group) {
        RegistryObject<GenericBlock> block = AthenaBlockRegistry.BLOCKS.register(registryName, () -> new GenericBlock(blockProperties));
        AthenaBlockRegistry.BLOCK_ITEMS.register(registryName, () -> {
            Item.Properties props = new Item.Properties().group(group);
            return new BlockItem(block.get(), props);
        });
        return block;
    }

    public static <T extends Block> RegistryObject<T> register(String registryName, ItemGroup group, Supplier<T> blockSupplier) {
        RegistryObject<T> block = AthenaBlockRegistry.BLOCKS.register(registryName, blockSupplier);
        AthenaBlockRegistry.BLOCK_ITEMS.register(registryName, () -> {
            Item.Properties props = new Item.Properties().group(group);
            return new BlockItem(block.get(), props);
        });
        return block;
    }

}
