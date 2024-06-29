package com.drd.createwindows;

import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;

public class ModSpriteShifts {
    private static final Map<WoodType, CTSpriteShiftEntry> MOD_WOODEN_WINDOWS = new IdentityHashMap();
    
    public ModSpriteShifts() {
    }

    private static void populateMaps() {
        WoodType[] supportedWoodTypes = new WoodType[]{WoodType.CHERRY, WoodType.BAMBOO};
        Arrays.stream(supportedWoodTypes).forEach((woodType) -> {
            MOD_WOODEN_WINDOWS.put(woodType, vertical("palettes/" + woodType.name() + "_window"));
        });
    }

    private static CTSpriteShiftEntry vertical(String name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }

    private static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(CreateMissingWindows.asResource(originalLocation), CreateMissingWindows.asResource(targetLocation));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
        return CTSpriteShifter.getCT(type, CreateMissingWindows.asResource("block/" + blockTextureName), CreateMissingWindows.asResource("block/" + connectedTextureName + "_connected"));
    }

    private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName);
    }

    public static CTSpriteShiftEntry getModWoodenWindow(WoodType woodType) {
        return (CTSpriteShiftEntry)MOD_WOODEN_WINDOWS.get(woodType);
    }
    
    static {
        populateMaps();
    }
}
