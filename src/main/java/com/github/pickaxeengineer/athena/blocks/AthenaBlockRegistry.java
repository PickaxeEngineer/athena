package com.github.pickaxeengineer.athena.blocks;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, AthenaMod.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AthenaMod.MODID);

    public static final RegistryObject<Block> SIMPLE_BLOCK_REGISTER = BLOCKS.register(SimpleBlock.REGISTRY_NAME, SimpleBlock::new);
    public static final RegistryObject<Block> GROUND = BLOCKS.register("ground", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3)));

    public static final RegistryObject<Item> SIMPLE_BLOCK_ITEM_REGISTER = BLOCK_ITEMS.register(SimpleBlock.REGISTRY_NAME, () -> {
        Item.Properties porps = new Item.Properties().group(ItemGroup.MISC);
        return new BlockItem(SIMPLE_BLOCK_REGISTER.get(), porps);
    });

    public static final RegistryObject<Item> GROUND_ITEM = BLOCK_ITEMS.register("ground", () -> {
        Item.Properties porps = new Item.Properties().group(ItemGroup.MISC);
        return new BlockItem(GROUND.get(), porps);
    });

}