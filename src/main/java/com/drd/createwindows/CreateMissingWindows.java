package com.drd.createwindows;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateMissingWindows.MODID)
public class CreateMissingWindows {
    public static final String MODID = "createwindows";

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create("createwindows");

    public CreateMissingWindows() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(modEventBus);

        ModBlocks.register();

        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BUILDING_BLOCKS_TAB.getKey())
            event.accept(ModBlocks.CHERRY_WINDOW.get());
        if (event.getTabKey() == AllCreativeModeTabs.BUILDING_BLOCKS_TAB.getKey())
            event.accept(ModBlocks.BAMBOO_WINDOW.get());
        if (event.getTabKey() == AllCreativeModeTabs.BUILDING_BLOCKS_TAB.getKey())
            event.accept(ModBlocks.CHERRY_WINDOW_PANE.get());
        if (event.getTabKey() == AllCreativeModeTabs.BUILDING_BLOCKS_TAB.getKey())
            event.accept(ModBlocks.BAMBOO_WINDOW_PANE.get());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHERRY_WINDOW.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BAMBOO_WINDOW.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHERRY_WINDOW_PANE.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BAMBOO_WINDOW_PANE.get(), RenderType.cutoutMipped());
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation("createwindows", path);
    }
}
