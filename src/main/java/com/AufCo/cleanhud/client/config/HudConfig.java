package com.AufCo.cleanhud.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class HudConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    // Element visibility
    public static final ForgeConfigSpec.BooleanValue SHOW_COORDINATES;
    public static final ForgeConfigSpec.BooleanValue SHOW_FPS;
    public static final ForgeConfigSpec.BooleanValue SHOW_CHUNK;
    public static final ForgeConfigSpec.BooleanValue SHOW_FACING;
    public static final ForgeConfigSpec.BooleanValue SHOW_LIGHT;
    public static final ForgeConfigSpec.BooleanValue SHOW_BIOME;
    public static final ForgeConfigSpec.BooleanValue SHOW_MEMORY;
    public static final ForgeConfigSpec.BooleanValue SHOW_PING;
    public static final ForgeConfigSpec.BooleanValue SHOW_LOOKING_AT_NAME;
    public static final ForgeConfigSpec.BooleanValue SHOW_LOOKING_AT_COORDS;
    public static final ForgeConfigSpec.BooleanValue SHOW_SPEED;
    
    // Label visibility
    public static final ForgeConfigSpec.BooleanValue SHOW_COORDINATES_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_FPS_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_CHUNK_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_FACING_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_LIGHT_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_BIOME_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_MEMORY_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_PING_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_LOOKING_AT_NAME_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_LOOKING_AT_COORDS_LABEL;
    public static final ForgeConfigSpec.BooleanValue SHOW_SPEED_LABEL;
    
    // Labels
    public static final ForgeConfigSpec.ConfigValue<String> COORDINATES_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> FPS_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> CHUNK_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> FACING_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> LIGHT_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> BIOME_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> MEMORY_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> PING_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> LOOKING_AT_NAME_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> LOOKING_AT_COORDS_LABEL;
    public static final ForgeConfigSpec.ConfigValue<String> SPEED_LABEL;
    
    // Display order
    public static final ForgeConfigSpec.ConfigValue<List<String>> ELEMENT_ORDER;
    
    // Text color
    public static final ForgeConfigSpec.IntValue TEXT_COLOR;
    
    // Previous label states (for auto-restore functionality)
    public static final ForgeConfigSpec.BooleanValue PREV_COORDINATES_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_FPS_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_CHUNK_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_FACING_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_LIGHT_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_BIOME_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_MEMORY_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_PING_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_LOOKING_AT_NAME_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_LOOKING_AT_COORDS_LABEL;
    public static final ForgeConfigSpec.BooleanValue PREV_SPEED_LABEL;
    
    static {
        BUILDER.push("HUD Elements");
        
        // Element visibility
        SHOW_COORDINATES = BUILDER.comment("Show coordinates").define("showCoordinates", true);
        SHOW_FPS = BUILDER.comment("Show FPS").define("showFPS", true);
        SHOW_CHUNK = BUILDER.comment("Show chunk coordinates").define("showChunk", true);
        SHOW_FACING = BUILDER.comment("Show facing direction").define("showFacing", true);
        SHOW_LIGHT = BUILDER.comment("Show light level").define("showLight", true);
        SHOW_BIOME = BUILDER.comment("Show biome").define("showBiome", true);
        SHOW_MEMORY = BUILDER.comment("Show memory usage").define("showMemory", true);
        SHOW_PING = BUILDER.comment("Show ping (auto-enabled on servers)").define("showPing", false);
        SHOW_LOOKING_AT_NAME = BUILDER.comment("Show name of what you're looking at").define("showLookingAtName", true);
        SHOW_LOOKING_AT_COORDS = BUILDER.comment("Show coordinates of what you're looking at").define("showLookingAtCoords", true);
        SHOW_SPEED = BUILDER.comment("Show player speed").define("showSpeed", true);
        
        // Label visibility
        SHOW_COORDINATES_LABEL = BUILDER.comment("Show coordinates label").define("showCoordinatesLabel", true);
        SHOW_FPS_LABEL = BUILDER.comment("Show FPS label").define("showFPSLabel", true);
        SHOW_CHUNK_LABEL = BUILDER.comment("Show chunk label").define("showChunkLabel", true);
        SHOW_FACING_LABEL = BUILDER.comment("Show facing label").define("showFacingLabel", true);
        SHOW_LIGHT_LABEL = BUILDER.comment("Show light label").define("showLightLabel", true);
        SHOW_BIOME_LABEL = BUILDER.comment("Show biome label").define("showBiomeLabel", true);
        SHOW_MEMORY_LABEL = BUILDER.comment("Show memory label").define("showMemoryLabel", true);
        SHOW_PING_LABEL = BUILDER.comment("Show ping label").define("showPingLabel", true);
        SHOW_LOOKING_AT_NAME_LABEL = BUILDER.comment("Show looking at name label").define("showLookingAtNameLabel", true);
        SHOW_LOOKING_AT_COORDS_LABEL = BUILDER.comment("Show looking at coords label").define("showLookingAtCoordsLabel", true);
        SHOW_SPEED_LABEL = BUILDER.comment("Show speed label").define("showSpeedLabel", true);
        
        BUILDER.pop();
        BUILDER.push("Labels");
        
        // Labels
        COORDINATES_LABEL = BUILDER.comment("Label for coordinates").define("coordinatesLabel", "Block:");
        FPS_LABEL = BUILDER.comment("Label for FPS").define("fpsLabel", "FPS:");
        CHUNK_LABEL = BUILDER.comment("Label for chunk").define("chunkLabel", "Chunk:");
        FACING_LABEL = BUILDER.comment("Label for facing").define("facingLabel", "Facing:");
        LIGHT_LABEL = BUILDER.comment("Label for light").define("lightLabel", "Light:");
        BIOME_LABEL = BUILDER.comment("Label for biome").define("biomeLabel", "Biome:");
        MEMORY_LABEL = BUILDER.comment("Label for memory").define("memoryLabel", "Mem:");
        PING_LABEL = BUILDER.comment("Label for ping").define("pingLabel", "Ping:");
        LOOKING_AT_NAME_LABEL = BUILDER.comment("Label for looking at name").define("lookingAtNameLabel", "Looking at:");
        LOOKING_AT_COORDS_LABEL = BUILDER.comment("Label for looking at coords").define("lookingAtCoordsLabel", "Looking at:");
        SPEED_LABEL = BUILDER.comment("Label for speed").define("speedLabel", "Speed:");
        
        BUILDER.pop();
        BUILDER.push("Display Order");
        
        // Element order
        ELEMENT_ORDER = BUILDER.comment("Order of HUD elements (coordinates, facing, chunk, fps, memory, ping, light, biome, lookingAtName, lookingAtCoords, speed)")
                .define("elementOrder", Arrays.asList("coordinates", "facing", "chunk", "fps", "memory", "ping", "light", "biome", "lookingAtName", "lookingAtCoords", "speed"));
        
        BUILDER.pop();
        BUILDER.push("Appearance");
        
        // Text color (default white = 0xFFFFFF)
        TEXT_COLOR = BUILDER.comment("HUD text color (hex format)")
                .defineInRange("textColor", 0xFFFFFF, 0x000000, 0xFFFFFF);
        
        BUILDER.pop();
        BUILDER.push("Previous Label States");
        
        // Previous label states for auto-restore
        PREV_COORDINATES_LABEL = BUILDER.comment("Previous coordinates label state").define("prevCoordinatesLabel", true);
        PREV_FPS_LABEL = BUILDER.comment("Previous FPS label state").define("prevFPSLabel", true);
        PREV_CHUNK_LABEL = BUILDER.comment("Previous chunk label state").define("prevChunkLabel", true);
        PREV_FACING_LABEL = BUILDER.comment("Previous facing label state").define("prevFacingLabel", true);
        PREV_LIGHT_LABEL = BUILDER.comment("Previous light label state").define("prevLightLabel", true);
        PREV_BIOME_LABEL = BUILDER.comment("Previous biome label state").define("prevBiomeLabel", true);
        PREV_MEMORY_LABEL = BUILDER.comment("Previous memory label state").define("prevMemoryLabel", true);
        PREV_PING_LABEL = BUILDER.comment("Previous ping label state").define("prevPingLabel", true);
        PREV_LOOKING_AT_NAME_LABEL = BUILDER.comment("Previous looking at name label state").define("prevLookingAtNameLabel", true);
        PREV_LOOKING_AT_COORDS_LABEL = BUILDER.comment("Previous looking at coords label state").define("prevLookingAtCoordsLabel", true);
        PREV_SPEED_LABEL = BUILDER.comment("Previous speed label state").define("prevSpeedLabel", true);
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}