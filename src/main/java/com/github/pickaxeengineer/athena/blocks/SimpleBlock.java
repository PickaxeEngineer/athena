package com.github.pickaxeengineer.athena.blocks;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

/**
 * A very basic solid cube with nothing special.
 * <p>
 *     This block's name is set during registration, see {@link AthenaBlockRegistry},
 *     however for convenience, it's a constant {@link #REGISTRY_NAME}.
 * </p>
 * <h2>Externally required files</h2>
 * <ul>
 *     <li>The blockstate JSON: {@code resources/assets/}{@link AthenaMod#MODID}{@code /blockstates/}{@link #REGISTRY_NAME}{@code .json}</li>
 *     <li>The model JSON: {@code resources/assets/}{@link AthenaMod#MODID}{@code /models/block/}{@link #REGISTRY_NAME}{@code .json}</li>
 *     <li>The texture JSON: {@code resources/assets/}{@link AthenaMod#MODID}{@code /textures/block/}{@link #REGISTRY_NAME}{@code .json} (in this particular instance, the model specifies a minecraft vanilla texture</li>
 *     <li>A localisation entry in {@code resources/assets/}{@link AthenaMod#MODID}{@code /lang/en_us.json} with the key {@code block.}{@link AthenaMod#MODID}{@code .}{@link #REGISTRY_NAME}</li>
 *     <li>The loot table: {@code resources/data/}{@link AthenaMod#MODID}{@code /loot_tables/blocks/}{@link #REGISTRY_NAME}{@code .json}</li>
 * </ul>
 * <h2>Each Block requires a {@link net.minecraft.item.BlockItem}</h2>
 * See {@link AthenaBlockRegistry}
 * <ul>
 *     <li>The item model JSON: {@code resources/assets/}{@link AthenaMod#MODID}{@code /models/item/}{@link #REGISTRY_NAME}{@code .json}</li>
 * </ul>
 *
 * @author pickaxe_engineer
 */
public class SimpleBlock extends Block {

    public static final String REGISTRY_NAME = "simple_block";

    public SimpleBlock(){
        super(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1f));
    }

}
