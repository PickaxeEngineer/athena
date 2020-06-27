package com.github.pickaxeengineer.athena.blocks;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaBlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, AthenaMod.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AthenaMod.MODID);

    public static final RegistryObject<GenericBlock> ROUGH_METAL = GenericBlock.register("rough_metal", Block.Properties.create(Material.IRON).hardnessAndResistance(4), ItemGroup.MISC);
    public static final RegistryObject<PlattedDirtBlock> PLATTED_DIRT = PlattedDirtBlock.register("platted_dirt", ItemGroup.MISC, () -> new PlattedDirtBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(2).harvestTool(ToolType.SHOVEL)));

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


    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(PLATTED_DIRT.get(), RenderType.getSolid());
    }
}
