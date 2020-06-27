package com.github.pickaxeengineer.athena.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class GenericItem extends Item {


    public GenericItem(Properties properties) {
        super(properties);
    }

    public static RegistryObject<GenericItem> register(String registryName, Item.Properties properties){
        return AthenaItemRegistry.ITEMS.register(registryName, () -> new GenericItem(properties));
    }
}
