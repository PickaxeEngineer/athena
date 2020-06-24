package com.example.examplemod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

/**
 * A very basic solid cube with nothing special.
 *
 * @author pickaxe_engineer
 */
public class SimpleBlock extends Block {

    public static final String REGISTRY_NAME = "simple_block";

    public SimpleBlock(){
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1f));
    }

}
