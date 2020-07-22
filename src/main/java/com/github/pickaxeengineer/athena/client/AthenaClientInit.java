package com.github.pickaxeengineer.athena.client;


import com.github.pickaxeengineer.athena.AthenaMod;
import com.github.pickaxeengineer.athena.client.render.entity.BasicBulldozerRenderer;
import com.github.pickaxeengineer.athena.entity.AthenaEntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AthenaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class AthenaClientInit {

    @SubscribeEvent
    public static void onClientSetupEvent(final FMLClientSetupEvent evt){

        RenderingRegistry.registerEntityRenderingHandler(AthenaEntityRegistry.BULLDOZER_TYPE.get(), BasicBulldozerRenderer::new);
    }


}
