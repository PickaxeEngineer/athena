package com.github.pickaxeengineer.athena.items;

import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.blocks.GenericBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AthenaItemRegistry {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, AthenaMod.MODID);

    public static final RegistryObject<GenericItem> NET = GenericItem.register("net", new Item.Properties().group( ItemGroup.MISC));

    public static final RegistryObject<GenericItem> TEST = GenericItem.register("debug_tool",
            new Item.Properties().group(ItemGroup.MISC),
            ((worldIn, playerIn, handIn) -> {
                Vec3d look = playerIn.getLookVec();
                AthenaMod.LOGGER.info("{}", Direction.getFacingFromVector(look.x, look.y, look.z));
            }));

    public static final RegistryObject<JackhammerItem> JACKHAMMER = GenericItem.register("jackhammer", () -> new JackhammerItem(ItemGroup.MISC));

    public static final RegistryObject<GenericItem> ASH = GenericItem.register("ash", new Item.Properties().group(ItemGroup.MISC));
}
