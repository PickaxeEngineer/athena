package com.github.pickaxeengineer.athena;

import com.github.pickaxeengineer.athena.blocks.AthenaBlockRegistry;
import com.github.pickaxeengineer.athena.items.AthenaItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AthenaMod.MODID)
public class AthenaMod
{
    public static final String MODID = "athena";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public AthenaMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AthenaBlockRegistry.BLOCKS.register(modEventBus);
        AthenaBlockRegistry.BLOCK_ITEMS.register(modEventBus);
        modEventBus.addListener(AthenaBlockRegistry::onClientSetupEvent);
        AthenaItemRegistry.ITEMS.register(modEventBus);
        // Recipes are handled by json files. see src/main/resources/data/athena/recipes
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // TODO Refactor into something better (e.g. custom recipe?)
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock evt){
        // Only on server
        if(evt.getWorld().isRemote){
            return;
        }
        if(evt.getWorld().getBlockState(evt.getPos()).getBlock() instanceof CauldronBlock){
            // "Crafting" of clay using ash blocks
            LOGGER.info("Cauldron right clicked!");
            if(evt.getItemStack().getItem() instanceof  BlockItem && ((BlockItem)evt.getItemStack().getItem()).getBlock() == AthenaBlockRegistry.ASH_BLOCK.get()){
                LOGGER.info("Ash meets cauldron");
                // its what we want
                int lvl = evt.getWorld().getBlockState(evt.getPos()).get(CauldronBlock.LEVEL);
                if(lvl > 0){
                    evt.setUseItem(Event.Result.DENY);
                    // Remove one item
                    evt.getItemStack().setCount(evt.getItemStack().getCount()-1);
                    // Spawn one above
                    ItemEntity itemEntity = new ItemEntity(evt.getWorld(), evt.getPos().getX(), evt.getPos().getY()+1, evt.getPos().getZ(), new ItemStack(Blocks.CLAY));
                    evt.getWorld().addEntity(itemEntity);
                    // Water level one minus
                    ((CauldronBlock) evt.getWorld().getBlockState(evt.getPos()).getBlock()).setWaterLevel(evt.getWorld(),evt.getPos(),evt.getWorld().getBlockState(evt.getPos()), lvl-1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onFurnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent evt){
        // Dedicated handler for fuel (to use when the item version is not available as class.
        // if the item is available as class, override getBurnTime for it!
        // TODO Refactor to dedicated handler class / include in GenericItem
        ItemStack fuel = evt.getItemStack();
        if(fuel.getItem() instanceof BlockItem && ((BlockItem)fuel.getItem()).getBlock() == AthenaBlockRegistry.CHARCOAL_BLOCK.get()){
            evt.setBurnTime(16000); // Hardcoded like coal block
        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
