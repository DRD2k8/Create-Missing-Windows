package com.drd.createwindows.foundation.data;

import com.drd.createwindows.CreateMissingWindows;
import com.drd.createwindows.ModSpriteShifts;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.GlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.GlassPaneCTBehaviour;
import com.simibubi.create.foundation.block.connected.HorizontalCTBehaviour;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.CreateRegistrate.connectedTextures;

public class ModBuilderTransformers {
    private static BlockBehaviour.Properties glassProperties(BlockBehaviour.Properties p) {
        return p.isValidSpawn(ModBuilderTransformers::never).isRedstoneConductor(ModBuilderTransformers::never).isSuffocating(ModBuilderTransformers::never).isViewBlocking(ModBuilderTransformers::never);
    }

    private static boolean never(BlockState p_235436_0_, BlockGetter p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    private static Boolean never(BlockState p_235427_0_, BlockGetter p_235427_1_, BlockPos p_235427_2_, EntityType<?> p_235427_3_) {
        return false;
    }

    public static BlockEntry<WindowBlock> modWoodenWindowBlock(WoodType woodType, Block planksBlock) {
        return modWoodenWindowBlock(woodType, planksBlock, () -> {
            return RenderType::cutoutMipped;
        }, false);
    }

    public static BlockEntry<WindowBlock> modWoodenWindowBlock(WoodType woodType, Block planksBlock, Supplier<Supplier<RenderType>> renderType, boolean translucent) {
        String woodName = woodType.name();
        String name = woodName + "_window";
        NonNullFunction<String, ResourceLocation> end_texture = ($) -> {
            return new ResourceLocation("block/" + woodName + "_planks");
        };
        NonNullFunction<String, ResourceLocation> side_texture = (n) -> {
            String var10000 = palettesDir();
            return Create.asResource(var10000 + n);
        };
        Supplier var10001 = () -> {
            return planksBlock;
        };
        Supplier var10002 = () -> {
            return ModSpriteShifts.getModWoodenWindow(woodType);
        };
        Objects.requireNonNull(planksBlock);
        return windowBlock(name, var10001, var10002, renderType, translucent, end_texture, side_texture, planksBlock::defaultMapColor);
    }

    public static BlockEntry<WindowBlock> windowBlock(String name, Supplier<? extends ItemLike> ingredient, Supplier<CTSpriteShiftEntry> ct, Supplier<Supplier<RenderType>> renderType, boolean translucent, NonNullFunction<String, ResourceLocation> endTexture, NonNullFunction<String, ResourceLocation> sideTexture, Supplier<MapColor> color) {
        return CreateMissingWindows.REGISTRATE.block(name, (p) -> {
                    return new WindowBlock(p, translucent);
                }).onRegister(connectedTextures(() -> {
                    return new HorizontalCTBehaviour((CTSpriteShiftEntry)ct.get());
                })).initialProperties(() -> {
                    return Blocks.GLASS;
                }).properties(ModBuilderTransformers::glassProperties).properties((p) -> {
                    return p.mapColor((MapColor)color.get());
                }).loot((t, g) -> {
                    t.dropWhenSilkTouch(g);
                }).blockstate((c, p) -> {
                    p.simpleBlock((Block)c.get(), p.models().cubeColumn(c.getName(), (ResourceLocation)sideTexture.apply(c.getName()), (ResourceLocation)endTexture.apply(c.getName())));
                })
                .tag(new TagKey[]{BlockTags.IMPERMEABLE})
                .simpleItem()
                .register();
    }

    public static BlockEntry<ConnectedGlassPaneBlock> modWoodenWindowPane(WoodType woodType, Supplier<? extends Block> parent) {
        return modWoodenWindowPane(woodType, parent, () -> {
            return RenderType::cutoutMipped;
        });
    }

    public static BlockEntry<ConnectedGlassPaneBlock> modWoodenWindowPane(WoodType woodType, Supplier<? extends Block> parent, Supplier<Supplier<RenderType>> renderType) {
        String woodName = woodType.name();
        String name = woodName + "_window";
        ResourceLocation topTexture = new ResourceLocation("block/" + woodName + "_planks");
        String var10000 = palettesDir();
        ResourceLocation sideTexture = Create.asResource(var10000 + name);
        return connectedGlassPane(name, parent, () -> {
            return ModSpriteShifts.getModWoodenWindow(woodType);
        }, sideTexture, sideTexture, topTexture, renderType);
    }

    private static BlockEntry<ConnectedGlassPaneBlock> connectedGlassPane(String name, Supplier<? extends Block> parent,
                                                                          Supplier<CTSpriteShiftEntry> ctshift, ResourceLocation sideTexture, ResourceLocation itemSideTexture,
                                                                          ResourceLocation topTexture, Supplier<Supplier<RenderType>> renderType) {
        NonNullConsumer<? super ConnectedGlassPaneBlock> connectedTextures =
                connectedTextures(() -> new GlassPaneCTBehaviour(ctshift.get()));
        String CGPparents = "block/connected_glass_pane/";
        String prefix = name + "_pane_";

        Function<RegistrateBlockstateProvider, ModelFile> post =
                getPaneModelProvider(CGPparents, prefix, "post", sideTexture, topTexture),
                side = getPaneModelProvider(CGPparents, prefix, "side", sideTexture, topTexture),
                sideAlt = getPaneModelProvider(CGPparents, prefix, "side_alt", sideTexture, topTexture),
                noSide = getPaneModelProvider(CGPparents, prefix, "noside", sideTexture, topTexture),
                noSideAlt = getPaneModelProvider(CGPparents, prefix, "noside_alt", sideTexture, topTexture);

        NonNullBiConsumer<DataGenContext<Block, ConnectedGlassPaneBlock>, RegistrateBlockstateProvider> stateProvider =
                (c, p) -> p.paneBlock(c.get(), post.apply(p), side.apply(p), sideAlt.apply(p), noSide.apply(p),
                        noSideAlt.apply(p));

        return glassPane(name, parent, itemSideTexture, topTexture, ConnectedGlassPaneBlock::new, renderType,
                connectedTextures, stateProvider);
    }

    private static Function<RegistrateBlockstateProvider, ModelFile> getPaneModelProvider(String CGPparents,
                                                                                          String prefix, String partial,
                                                                                          ResourceLocation sideTexture,
                                                                                          ResourceLocation topTexture) {
        return p -> p.models()
                .withExistingParent(prefix + partial, CreateMissingWindows.asResource(CGPparents + partial))
                .texture("pane", sideTexture)
                .texture("edge", topTexture);
    }

    private static <G extends GlassPaneBlock> BlockEntry<G> glassPane(String name, Supplier<? extends Block> parent,
                                                                      ResourceLocation sideTexture, ResourceLocation topTexture, NonNullFunction<BlockBehaviour.Properties, G> factory,
                                                                      Supplier<Supplier<RenderType>> renderType, NonNullConsumer<? super G> connectedTextures,
                                                                      NonNullBiConsumer<DataGenContext<Block, G>, RegistrateBlockstateProvider> stateProvider) {
        name += "_pane";

        return CreateMissingWindows.REGISTRATE.block(name, factory)
                .onRegister(connectedTextures)
                .initialProperties(() -> Blocks.GLASS_PANE)
                .properties(p -> p.mapColor(parent.get()
                        .defaultMapColor()))
                .blockstate(stateProvider)
                .item()
                .build()
                .register();
    }

    private static String palettesDir() {
        return "block/palettes/";
    }
}
