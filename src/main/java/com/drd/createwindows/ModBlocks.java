package com.drd.createwindows;

import com.drd.createwindows.foundation.data.ModBuilderTransformers;
import com.simibubi.create.content.decoration.palettes.ConnectedGlassPaneBlock;
import com.simibubi.create.content.decoration.palettes.WindowBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModBlocks {
    public static final BlockEntry<WindowBlock> CHERRY_WINDOW;
    public static final BlockEntry<WindowBlock> BAMBOO_WINDOW;
    public static final BlockEntry<ConnectedGlassPaneBlock> CHERRY_WINDOW_PANE;
    public static final BlockEntry<ConnectedGlassPaneBlock> BAMBOO_WINDOW_PANE;

    public ModBlocks() {
    }

    public static void register() {
    }

    static {
        CHERRY_WINDOW = ModBuilderTransformers.modWoodenWindowBlock(WoodType.CHERRY, Blocks.CHERRY_PLANKS);
        BAMBOO_WINDOW = ModBuilderTransformers.modWoodenWindowBlock(WoodType.BAMBOO, Blocks.BAMBOO_PLANKS);
        CHERRY_WINDOW_PANE = ModBuilderTransformers.modWoodenWindowPane(WoodType.CHERRY, CHERRY_WINDOW);
        BAMBOO_WINDOW_PANE = ModBuilderTransformers.modWoodenWindowPane(WoodType.BAMBOO, BAMBOO_WINDOW);
    }
}
