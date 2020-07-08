package com.github.pickaxeengineer.athena.tiles;

import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.blocks.AthenaBlockRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaTERegistry {

    private AthenaTERegistry(){}

    public static final DeferredRegister<TileEntityType<?>> TE_REGISTRY = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, AthenaMod.MODID);

    // name: registryName of block (must match exactly)
    public static final RegistryObject<TileEntityType<FirstTileEntity>> FIRST_TE = TE_REGISTRY.register("first_te", () -> TileEntityType.Builder.create(FirstTileEntity::new, AthenaBlockRegistry.FIRST_TE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> GENERATOR_TE = TE_REGISTRY.register("generator_block", () -> TileEntityType.Builder.create(GeneratorTileEntity::new, AthenaBlockRegistry.GENERATOR_TE_BLOCK.get()).build(null));
}
