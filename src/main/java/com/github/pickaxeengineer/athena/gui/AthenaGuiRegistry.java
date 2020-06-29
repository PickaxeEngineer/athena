package com.github.pickaxeengineer.athena.gui;

import com.github.pickaxeengineer.athena.containers.AthenaContainerRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class AthenaGuiRegistry {


    @SubscribeEvent
    public void onClientSetupEvent(FMLClientSetupEvent event){
        ScreenManager.registerFactory(AthenaContainerRegistry.bag, BagContainerGui::new);
    }
}
