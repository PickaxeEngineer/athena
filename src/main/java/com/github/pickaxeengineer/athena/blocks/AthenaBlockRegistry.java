package com.github.pickaxeengineer.athena.blocks;

import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.items.GenericItem;
import com.github.pickaxeengineer.athena.tiles.GeneratorTileEntity;
import com.github.pickaxeengineer.assetgen.BlockType;
import com.github.pickaxeengineer.assetgen.LootType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AthenaBlockRegistry {

    /* Registries */

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, AthenaMod.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AthenaMod.MODID);

    private static final List<Tuple<Supplier<Block>, RenderType>> SPECIAL_RENDERED_BLOCKS = new ArrayList<>();

    /* Mod Blocks */

    public static final RegistryObject<GenericBlock> ROUGH_METAL = GenericBlock.register("rough_metal", Block.Properties.create(Material.IRON).hardnessAndResistance(4), ItemGroup.MISC);
    public static final RegistryObject<PlattedDirtBlock> PLATTED_DIRT = PlattedDirtBlock.register("platted_dirt", ItemGroup.MISC, () -> new PlattedDirtBlock(Block.Properties.create(Material.EARTH).hardnessAndResistance(2).harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<LightFrameBlock> LIGHT_FRAME = LightFrameBlock.register("light_frame", ItemGroup.MISC, () -> new LightFrameBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2).notSolid().doesNotBlockMovement()));
    public static final RegistryObject<RotatedPillarBlock> CLAY_HAY_COMPOUND =  GenericBlock.register("clay_hay_compound", ItemGroup.MISC, () -> new RotatedPillarBlock(Block.Properties.create(Material.CLAY).hardnessAndResistance(2).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<GenericBlock> ASH_BLOCK = GenericBlock.register("ash_block", Block.Properties.create(Material.SAND).hardnessAndResistance(1).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL), ItemGroup.MISC);
    public static final RegistryObject<GenericBlock> CHARCOAL_BLOCK = GenericBlock.register("charcoal_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(4), ItemGroup.MISC);
    public static final RegistryObject<PipeBlock> PIPE_BLOCK = PipeBlock.register("pipe_block", ItemGroup.MISC, () -> new PipeBlock(Block.Properties.create(Material.ROCK).doesNotBlockMovement()));

    @com.github.pickaxeengineer.assetgen.Block(modId = AthenaMod.MODID, registryName = "test_block", blockType = BlockType.CUBE, lootType = LootType.SIMPLE_BLOCK, hasItem = true)
    public static final RegistryObject<GenericBlock> TEST_BLOCK = GenericBlock.register("test_block", Block.Properties.create(Material.ROCK), ItemGroup.MISC);

    @com.github.pickaxeengineer.assetgen.Block(modId = AthenaMod.MODID, registryName = "test_block2", blockType = BlockType.CUBE, lootType = LootType.SIMPLE_BLOCK, hasItem = true)
    public static final RegistryObject<GenericBlock> TEST_BLOCK_2 = GenericBlock.register("test_block2", Block.Properties.create(Material.EARTH), ItemGroup.MISC);
    // TE BLocks
    public static final RegistryObject<FirstTileEntityBlock> FIRST_TE_BLOCK = GenericBlock.register("first_te",ItemGroup.MISC, () -> new FirstTileEntityBlock(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<GenericTEBlock<GeneratorTileEntity>> GENERATOR_TE_BLOCK = GenericBlock.register("generator_block", ItemGroup.MISC, () -> new GenericTEBlock<>(Block.Properties.create(Material.IRON), GeneratorTileEntity::new));

    /* Additional register stuff */

    static{
        SPECIAL_RENDERED_BLOCKS.add(new Tuple<>(() -> (Block)PLATTED_DIRT.get(), RenderType.getSolid()));
        SPECIAL_RENDERED_BLOCKS.add(new Tuple<>(() -> (Block)LIGHT_FRAME.get(), RenderType.getCutout())); // fixme This block's feature doesn't work yet
        SPECIAL_RENDERED_BLOCKS.add(new Tuple<>(() -> (Block)FIRST_TE_BLOCK.get(), RenderType.getSolid()));
    }

    /* Standard Block Addition */ // TODO Refactor

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

    /* ----------------------------------------------- */

    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        SPECIAL_RENDERED_BLOCKS.forEach(t -> RenderTypeLookup.setRenderLayer(t.getA().get(), t.getB()));
    }
}
