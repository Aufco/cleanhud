package com.AufCo.cleanhud.client.gui;

import com.AufCo.cleanhud.client.config.HudConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HudConfigScreen extends Screen {
    private final Screen parent;
    private final Map<String, Checkbox> showCheckboxes = new HashMap<>();
    private final Map<String, Checkbox> labelCheckboxes = new HashMap<>();
    private final List<String> elementOrder = new ArrayList<>();
    private int selectedElement = -1;
    private final int[] colorPalette = {
        0xFFFFFF, // White
        0xFFAA00, // Orange  
        0xFF55FF, // Magenta
        0x55FFFF, // Light Blue
        0xFFFF55, // Yellow
        0x55FF55, // Light Green
        0xFFB3B3, // Pink
        0xAAAAAA, // Gray
        0x555555, // Dark Gray
        0x00AAAA, // Cyan
        0xAA00AA, // Purple
        0x0000AA, // Blue
        0xAA5500, // Brown
        0x00AA00, // Green
        0xAA0000, // Red
        0x000000  // Black
    };
    private int colorSelectorY;
    
    private static final String[] ELEMENTS = {
        "coordinates", "fps", "chunk", "facing", "light", "biome", "memory", "ping", "lookingAtName", "lookingAtCoords", "speed"
    };
    
    private static final Map<String, String> ELEMENT_NAMES = createElementNamesMap();
    
    private static Map<String, String> createElementNamesMap() {
        Map<String, String> map = new HashMap<>();
        map.put("coordinates", "Coordinates");
        map.put("fps", "FPS");
        map.put("chunk", "Chunk");
        map.put("facing", "Facing");
        map.put("light", "Light Level");
        map.put("biome", "Biome");
        map.put("memory", "Memory");
        map.put("ping", "Ping");
        map.put("lookingAtName", "Looking At (Name)");
        map.put("lookingAtCoords", "Looking At (Coords)");
        map.put("speed", "Speed");
        return map;
    }

    public HudConfigScreen(Screen parent) {
        super(Component.literal("HUD Order"));
        this.parent = parent;
        this.elementOrder.addAll(HudConfig.ELEMENT_ORDER.get());
    }

    @Override
    protected void init() {
        int startY = 15;
        int ySpacing = 20;
        int orderColumn = 5;
        int categoryColumn = 50;
        int labelColumn = 170; // Moved 30 pixels to the right from 140
        
        for (int i = 0; i < elementOrder.size(); i++) {
            String element = elementOrder.get(i);
            int y = startY + i * ySpacing;
            final int index = i;
            
            // Order buttons (leftmost)
            Button upButton = Button.builder(Component.literal("↑"), 
                button -> moveElementUp(index)).bounds(orderColumn, y, 18, 16).build();
            addRenderableWidget(upButton);
            
            Button downButton = Button.builder(Component.literal("↓"), 
                button -> moveElementDown(index)).bounds(orderColumn + 20, y, 18, 16).build();
            addRenderableWidget(downButton);
            
            // Category checkbox with immediate save
            Checkbox showCheckbox = Checkbox.builder(Component.literal(ELEMENT_NAMES.get(element)), font)
                .pos(categoryColumn, y)
                .selected(getShowValue(element))
                .build();
            showCheckboxes.put(element, showCheckbox);
            addRenderableWidget(showCheckbox);
            
            // Label checkbox with immediate save
            Checkbox labelCheckbox = Checkbox.builder(Component.literal("Label"), font)
                .pos(labelColumn, y)
                .selected(getLabelShowValue(element))
                .build();
            labelCheckboxes.put(element, labelCheckbox);
            addRenderableWidget(labelCheckbox);
        }
        
        // Add color selector at bottom left
        int colorY = height - 30; // Position at bottom of screen
        addColorSelector(colorY);
    }
    
    private void addColorSelector(int y) {
        colorSelectorY = y;
        int colorSize = 16;
        int spacing = 2;
        int startX = 5; // Further left for bottom left positioning
        
        for (int i = 0; i < colorPalette.length; i++) {
            int color = colorPalette[i];
            int x = startX + i * (colorSize + spacing);
            
            Button colorButton = Button.builder(Component.literal(""), 
                button -> {
                    HudConfig.TEXT_COLOR.set(color);
                    HudConfig.SPEC.save();
                })
                .bounds(x, y, colorSize, colorSize)
                .build();
            
            addRenderableWidget(colorButton);
        }
    }

    private boolean getShowValue(String element) {
        return switch (element) {
            case "coordinates" -> HudConfig.SHOW_COORDINATES.get();
            case "fps" -> HudConfig.SHOW_FPS.get();
            case "chunk" -> HudConfig.SHOW_CHUNK.get();
            case "facing" -> HudConfig.SHOW_FACING.get();
            case "light" -> HudConfig.SHOW_LIGHT.get();
            case "biome" -> HudConfig.SHOW_BIOME.get();
            case "memory" -> HudConfig.SHOW_MEMORY.get();
            case "ping" -> HudConfig.SHOW_PING.get();
            case "lookingAtName" -> HudConfig.SHOW_LOOKING_AT_NAME.get();
            case "lookingAtCoords" -> HudConfig.SHOW_LOOKING_AT_COORDS.get();
            case "speed" -> HudConfig.SHOW_SPEED.get();
            default -> false;
        };
    }
    
    private boolean getLabelShowValue(String element) {
        return switch (element) {
            case "coordinates" -> HudConfig.SHOW_COORDINATES_LABEL.get();
            case "fps" -> HudConfig.SHOW_FPS_LABEL.get();
            case "chunk" -> HudConfig.SHOW_CHUNK_LABEL.get();
            case "facing" -> HudConfig.SHOW_FACING_LABEL.get();
            case "light" -> HudConfig.SHOW_LIGHT_LABEL.get();
            case "biome" -> HudConfig.SHOW_BIOME_LABEL.get();
            case "memory" -> HudConfig.SHOW_MEMORY_LABEL.get();
            case "ping" -> HudConfig.SHOW_PING_LABEL.get();
            case "lookingAtName" -> HudConfig.SHOW_LOOKING_AT_NAME_LABEL.get();
            case "lookingAtCoords" -> HudConfig.SHOW_LOOKING_AT_COORDS_LABEL.get();
            case "speed" -> HudConfig.SHOW_SPEED_LABEL.get();
            default -> false;
        };
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        // Draw color swatches
        int colorSize = 16;
        int spacing = 2;
        int startX = 5;
        int currentColor = HudConfig.TEXT_COLOR.get();
        
        for (int i = 0; i < colorPalette.length; i++) {
            int color = colorPalette[i];
            int x = startX + i * (colorSize + spacing);
            
            // Draw color swatch
            guiGraphics.fill(x, colorSelectorY, x + colorSize, colorSelectorY + colorSize, 0xFF000000 | color);
            
            // Draw border (white for selected color, gray for others)
            if (color == currentColor) {
                // Selected color - draw white border
                guiGraphics.fill(x - 1, colorSelectorY - 1, x + colorSize + 1, colorSelectorY, 0xFFFFFFFF);
                guiGraphics.fill(x - 1, colorSelectorY + colorSize, x + colorSize + 1, colorSelectorY + colorSize + 1, 0xFFFFFFFF);
                guiGraphics.fill(x - 1, colorSelectorY, x, colorSelectorY + colorSize, 0xFFFFFFFF);
                guiGraphics.fill(x + colorSize, colorSelectorY, x + colorSize + 1, colorSelectorY + colorSize, 0xFFFFFFFF);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        // Save changes immediately when checkboxes change
        saveChangesImmediately();
    }
    
    private void saveChangesImmediately() {
        setShowValues();
        HudConfig.ELEMENT_ORDER.set(new ArrayList<>(elementOrder));
        HudConfig.SPEC.save();
    }

    private void moveElementUp(int index) {
        if (index > 0) {
            String element = elementOrder.remove(index);
            elementOrder.add(index - 1, element);
            clearAndInit();
            saveChangesImmediately();
        }
    }

    private void moveElementDown(int index) {
        if (index < elementOrder.size() - 1) {
            String element = elementOrder.remove(index);
            elementOrder.add(index + 1, element);
            clearAndInit();
            saveChangesImmediately();
        }
    }
    
    private void clearAndInit() {
        // Store current checkbox states before clearing
        Map<String, Boolean> currentShowStates = new HashMap<>();
        Map<String, Boolean> currentLabelStates = new HashMap<>();
        
        for (Map.Entry<String, Checkbox> entry : showCheckboxes.entrySet()) {
            currentShowStates.put(entry.getKey(), entry.getValue().selected());
        }
        for (Map.Entry<String, Checkbox> entry : labelCheckboxes.entrySet()) {
            currentLabelStates.put(entry.getKey(), entry.getValue().selected());
        }
        
        // Temporarily update config with current states
        updateConfigWithStates(currentShowStates, currentLabelStates);
        
        clearWidgets();
        showCheckboxes.clear();
        labelCheckboxes.clear();
        
        // Re-initialize - checkboxes will be created with states from config
        init();
    }
    
    private void updateConfigWithStates(Map<String, Boolean> showStates, Map<String, Boolean> labelStates) {
        for (Map.Entry<String, Boolean> entry : showStates.entrySet()) {
            String element = entry.getKey();
            boolean state = entry.getValue();
            switch (element) {
                case "coordinates" -> HudConfig.SHOW_COORDINATES.set(state);
                case "fps" -> HudConfig.SHOW_FPS.set(state);
                case "chunk" -> HudConfig.SHOW_CHUNK.set(state);
                case "facing" -> HudConfig.SHOW_FACING.set(state);
                case "light" -> HudConfig.SHOW_LIGHT.set(state);
                case "biome" -> HudConfig.SHOW_BIOME.set(state);
                case "memory" -> HudConfig.SHOW_MEMORY.set(state);
                case "ping" -> HudConfig.SHOW_PING.set(state);
                case "lookingAtName" -> HudConfig.SHOW_LOOKING_AT_NAME.set(state);
                case "lookingAtCoords" -> HudConfig.SHOW_LOOKING_AT_COORDS.set(state);
                case "speed" -> HudConfig.SHOW_SPEED.set(state);
            }
        }
        
        for (Map.Entry<String, Boolean> entry : labelStates.entrySet()) {
            String element = entry.getKey();
            boolean state = entry.getValue();
            switch (element) {
                case "coordinates" -> HudConfig.SHOW_COORDINATES_LABEL.set(state);
                case "fps" -> HudConfig.SHOW_FPS_LABEL.set(state);
                case "chunk" -> HudConfig.SHOW_CHUNK_LABEL.set(state);
                case "facing" -> HudConfig.SHOW_FACING_LABEL.set(state);
                case "light" -> HudConfig.SHOW_LIGHT_LABEL.set(state);
                case "biome" -> HudConfig.SHOW_BIOME_LABEL.set(state);
                case "memory" -> HudConfig.SHOW_MEMORY_LABEL.set(state);
                case "ping" -> HudConfig.SHOW_PING_LABEL.set(state);
                case "lookingAtName" -> HudConfig.SHOW_LOOKING_AT_NAME_LABEL.set(state);
                case "lookingAtCoords" -> HudConfig.SHOW_LOOKING_AT_COORDS_LABEL.set(state);
                case "speed" -> HudConfig.SHOW_SPEED_LABEL.set(state);
            }
        }
    }

    
    private void setShowValues() {
        // Store previous label states and handle auto-restore logic
        setPrevLabelStateAndAutoRestore("coordinates", HudConfig.SHOW_COORDINATES, HudConfig.SHOW_COORDINATES_LABEL, HudConfig.PREV_COORDINATES_LABEL);
        setPrevLabelStateAndAutoRestore("fps", HudConfig.SHOW_FPS, HudConfig.SHOW_FPS_LABEL, HudConfig.PREV_FPS_LABEL);
        setPrevLabelStateAndAutoRestore("chunk", HudConfig.SHOW_CHUNK, HudConfig.SHOW_CHUNK_LABEL, HudConfig.PREV_CHUNK_LABEL);
        setPrevLabelStateAndAutoRestore("facing", HudConfig.SHOW_FACING, HudConfig.SHOW_FACING_LABEL, HudConfig.PREV_FACING_LABEL);
        setPrevLabelStateAndAutoRestore("light", HudConfig.SHOW_LIGHT, HudConfig.SHOW_LIGHT_LABEL, HudConfig.PREV_LIGHT_LABEL);
        setPrevLabelStateAndAutoRestore("biome", HudConfig.SHOW_BIOME, HudConfig.SHOW_BIOME_LABEL, HudConfig.PREV_BIOME_LABEL);
        setPrevLabelStateAndAutoRestore("memory", HudConfig.SHOW_MEMORY, HudConfig.SHOW_MEMORY_LABEL, HudConfig.PREV_MEMORY_LABEL);
        setPrevLabelStateAndAutoRestore("ping", HudConfig.SHOW_PING, HudConfig.SHOW_PING_LABEL, HudConfig.PREV_PING_LABEL);
        setPrevLabelStateAndAutoRestore("lookingAtName", HudConfig.SHOW_LOOKING_AT_NAME, HudConfig.SHOW_LOOKING_AT_NAME_LABEL, HudConfig.PREV_LOOKING_AT_NAME_LABEL);
        setPrevLabelStateAndAutoRestore("lookingAtCoords", HudConfig.SHOW_LOOKING_AT_COORDS, HudConfig.SHOW_LOOKING_AT_COORDS_LABEL, HudConfig.PREV_LOOKING_AT_COORDS_LABEL);
        setPrevLabelStateAndAutoRestore("speed", HudConfig.SHOW_SPEED, HudConfig.SHOW_SPEED_LABEL, HudConfig.PREV_SPEED_LABEL);
    }
    
    private void setPrevLabelStateAndAutoRestore(String element, 
                                               net.minecraftforge.common.ForgeConfigSpec.BooleanValue showConfig,
                                               net.minecraftforge.common.ForgeConfigSpec.BooleanValue labelConfig,
                                               net.minecraftforge.common.ForgeConfigSpec.BooleanValue prevLabelConfig) {
        boolean wasShowing = showConfig.get();
        boolean isNowShowing = showCheckboxes.get(element).selected();
        boolean currentLabelState = labelCheckboxes.get(element).selected();
        
        // If turning the category back on, restore the previous label state
        if (!wasShowing && isNowShowing) {
            labelConfig.set(prevLabelConfig.get());
        } else if (wasShowing) {
            // Store current label state as previous state
            prevLabelConfig.set(currentLabelState);
            labelConfig.set(currentLabelState);
        } else {
            // Category is still off, just update the label config
            labelConfig.set(currentLabelState);
        }
        
        // Set the show config
        showConfig.set(isNowShowing);
    }
    

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }
}