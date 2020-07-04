package com.github.pickaxeengineer.athena.containers;

import com.github.pickaxeengineer.athena.AthenaMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AthenaContainerRegistry {

    public static ContainerType<BagContainer> bag;

    @SubscribeEvent
    public void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        if (event.getGenericType().getTypeName().endsWith("ContainerType")) {
            // not in example and otherwise is crashing
            bag = IForgeContainerType.create(BagContainer::createContainerClientSide);
            bag.setRegistryName("bag_container");
            event.getRegistry().register(bag);
            AthenaMod.LOGGER.info("Registered bag container");
        }
    }
}
