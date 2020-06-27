package com.github.pickaxeengineer.athena.items;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaItemRegistry {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AthenaMod.MODID);

    public static final RegistryObject<GenericItem> NET = GenericItem.register("net", new Item.Properties().group( ItemGroup.MISC));

}
