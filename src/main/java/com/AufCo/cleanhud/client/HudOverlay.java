package com.AufCo.cleanhud.client;

import com.AufCo.cleanhud.client.config.HudConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "cleanhud", value = {Dist.CLIENT})
public class HudOverlay {
    
    private static Vec3 lastPlayerPos = Vec3.ZERO;
    private static long lastSpeedTime = 0;
    private static double currentSpeed = 0.0;
    private static long lastMemoryFpsUpdate = 0;
    private static String cachedMemoryDisplay = "";
    private static String cachedFpsDisplay = "";
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onCustomizeGuiOverlay(CustomizeGuiOverlayEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;
        
        if (player == null || level == null) {
            return;
        }
        
        Font font = mc.font;
        
        // Collect all HUD data
        Map<String, String> hudData = new HashMap<>();
        
        if (HudConfig.SHOW_COORDINATES.get()) {
            BlockPos playerPos = player.blockPosition();
            String label = HudConfig.SHOW_COORDINATES_LABEL.get() ? HudConfig.COORDINATES_LABEL.get() + " " : "";
            hudData.put("coordinates", label + playerPos.getX() + " " + playerPos.getY() + " " + playerPos.getZ());
        }
        
        if (HudConfig.SHOW_FPS.get()) {
            long currentTime = System.currentTimeMillis();
            
            // Update FPS info every 1000ms (1 second) to sync with memory
            if (lastMemoryFpsUpdate == 0 || (currentTime - lastMemoryFpsUpdate) >= 1000) {
                int fps = mc.getFps();
                String label = HudConfig.SHOW_FPS_LABEL.get() ? HudConfig.FPS_LABEL.get() + " " : "";
                cachedFpsDisplay = label + fps;
            }
            
            hudData.put("fps", cachedFpsDisplay);
        }
        
        if (HudConfig.SHOW_CHUNK.get()) {
            BlockPos playerPos = player.blockPosition();
            int chunkX = playerPos.getX() >> 4;
            int chunkY = playerPos.getY() >> 4;
            int chunkZ = playerPos.getZ() >> 4;
            String label = HudConfig.SHOW_CHUNK_LABEL.get() ? HudConfig.CHUNK_LABEL.get() + " " : "";
            hudData.put("chunk", label + chunkX + " " + chunkY + " " + chunkZ);
        }
        
        if (HudConfig.SHOW_FACING.get()) {
            Direction facing = player.getDirection();
            String directionName = facing.getName();
            directionName = directionName.substring(0, 1).toUpperCase() + directionName.substring(1);
            String label = HudConfig.SHOW_FACING_LABEL.get() ? HudConfig.FACING_LABEL.get() + " " : "";
            hudData.put("facing", label + directionName);
        }
        
        if (HudConfig.SHOW_LIGHT.get()) {
            BlockPos playerPos = player.blockPosition();
            int lightLevel = level.getMaxLocalRawBrightness(playerPos);
            String label = HudConfig.SHOW_LIGHT_LABEL.get() ? HudConfig.LIGHT_LABEL.get() + " " : "";
            hudData.put("light", label + lightLevel);
        }
        
        if (HudConfig.SHOW_BIOME.get()) {
            BlockPos playerPos = player.blockPosition();
            try {
                var biomeHolder = level.getBiome(playerPos);
                var registryAccess = mc.getConnection().registryAccess();
                var biomeRegistry = registryAccess.lookupOrThrow(net.minecraft.core.registries.Registries.BIOME);
                ResourceLocation biomeLocation = biomeRegistry.getKey(biomeHolder.value());
                String biomeName;
                if (biomeLocation != null) {
                    String path = biomeLocation.getPath();
                    // Convert "plains" to "Plains", "dark_forest" to "Dark Forest", etc.
                    biomeName = java.util.Arrays.stream(path.split("_"))
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(java.util.stream.Collectors.joining(" "));
                } else {
                    biomeName = "Unknown";
                }
                String label = HudConfig.SHOW_BIOME_LABEL.get() ? HudConfig.BIOME_LABEL.get() + " " : "";
                hudData.put("biome", label + biomeName);
            } catch (Exception e) {
                String label = HudConfig.SHOW_BIOME_LABEL.get() ? HudConfig.BIOME_LABEL.get() + " " : "";
                hudData.put("biome", label + "Unknown");
            }
        }
        
        if (HudConfig.SHOW_MEMORY.get()) {
            long currentTime = System.currentTimeMillis();
            
            // Update memory info every 1000ms (1 second) for slower updates
            if (lastMemoryFpsUpdate == 0 || (currentTime - lastMemoryFpsUpdate) >= 1000) {
                Runtime runtime = Runtime.getRuntime();
                long usedMemory = runtime.totalMemory() - runtime.freeMemory();
                long maxMemory = runtime.maxMemory();
                double percentage = (double) usedMemory / maxMemory * 100;
                
                String label = HudConfig.SHOW_MEMORY_LABEL.get() ? HudConfig.MEMORY_LABEL.get() + " " : "";
                cachedMemoryDisplay = label + Math.round(percentage) + "%";
                lastMemoryFpsUpdate = currentTime;
            }
            
            hudData.put("memory", cachedMemoryDisplay);
        }
        
        if (HudConfig.SHOW_PING.get()) {
            try {
                if (mc.getConnection() != null && mc.getConnection().getConnection() != null) {
                    // We're on a server
                    net.minecraft.client.multiplayer.PlayerInfo playerInfo = mc.getConnection().getPlayerInfo(player.getUUID());
                    if (playerInfo != null) {
                        int ping = playerInfo.getLatency();
                        String label = HudConfig.SHOW_PING_LABEL.get() ? HudConfig.PING_LABEL.get() + " " : "";
                        hudData.put("ping", label + ping + " ms");
                    } else {
                        String label = HudConfig.SHOW_PING_LABEL.get() ? HudConfig.PING_LABEL.get() + " " : "";
                        hudData.put("ping", label + "0 ms");
                    }
                } else {
                    // Single player - don't show ping
                    String label = HudConfig.SHOW_PING_LABEL.get() ? HudConfig.PING_LABEL.get() + " " : "";
                    hudData.put("ping", label + "N/A");
                }
            } catch (Exception e) {
                String label = HudConfig.SHOW_PING_LABEL.get() ? HudConfig.PING_LABEL.get() + " " : "";
                hudData.put("ping", label + "N/A");
            }
        }
        
        if (HudConfig.SHOW_LOOKING_AT_NAME.get() || HudConfig.SHOW_LOOKING_AT_COORDS.get()) {
            HitResult hitResult = mc.hitResult;
            String lookingAt = "Air";
            String coordinates = "";
            
            if (hitResult != null) {
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hitResult;
                    BlockPos pos = blockHit.getBlockPos();
                    BlockState blockState = level.getBlockState(pos);
                    Block block = blockState.getBlock();
                    lookingAt = block.getName().getString();
                    coordinates = pos.getX() + " " + pos.getY() + " " + pos.getZ();
                } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHit = (EntityHitResult) hitResult;
                    Entity entity = entityHit.getEntity();
                    lookingAt = entity.getDisplayName().getString();
                    BlockPos pos = entity.blockPosition();
                    coordinates = pos.getX() + " " + pos.getY() + " " + pos.getZ();
                }
            }
            
            if (HudConfig.SHOW_LOOKING_AT_NAME.get()) {
                String nameLabel = HudConfig.SHOW_LOOKING_AT_NAME_LABEL.get() ? HudConfig.LOOKING_AT_NAME_LABEL.get() + " " : "";
                hudData.put("lookingAtName", nameLabel + lookingAt);
            }
            
            if (HudConfig.SHOW_LOOKING_AT_COORDS.get()) {
                String coordsLabel = HudConfig.SHOW_LOOKING_AT_COORDS_LABEL.get() ? HudConfig.LOOKING_AT_COORDS_LABEL.get() + " " : "";
                hudData.put("lookingAtCoords", coordsLabel + coordinates);
            }
        }
        
        if (HudConfig.SHOW_SPEED.get()) {
            // Calculate player speed (update every 500ms for stability)
            Vec3 currentPos = player.position();
            long currentTime = System.currentTimeMillis();
            
            if (lastSpeedTime != 0 && (currentTime - lastSpeedTime) >= 500) {
                double distance = currentPos.distanceTo(lastPlayerPos);
                double timeSeconds = (currentTime - lastSpeedTime) / 1000.0;
                if (timeSeconds > 0) {
                    double newSpeed = distance / timeSeconds;
                    // Apply smoothing to reduce fluctuations
                    currentSpeed = currentSpeed == 0.0 ? newSpeed : (currentSpeed * 0.7 + newSpeed * 0.3);
                }
                lastPlayerPos = currentPos;
                lastSpeedTime = currentTime;
            } else if (lastSpeedTime == 0) {
                lastPlayerPos = currentPos;
                lastSpeedTime = currentTime;
            }
            
            String label = HudConfig.SHOW_SPEED_LABEL.get() ? HudConfig.SPEED_LABEL.get() + " " : "";
            // Round to nearest tenth (e.g., 5.61 becomes 5.6)
            double roundedSpeed = Math.round(currentSpeed * 10.0) / 10.0;
            hudData.put("speed", label + String.format("%.1f", roundedSpeed) + " m/s");
        }
        
        // Render HUD elements in configured order
        int x = 4;
        int y = 4;
        int lineHeight = font.lineHeight + 1;
        
        List<String> elementOrder = new ArrayList<>(HudConfig.ELEMENT_ORDER.get());
        
        int textColor = HudConfig.TEXT_COLOR.get();
        for (String element : elementOrder) {
            if (hudData.containsKey(element)) {
                event.getGuiGraphics().drawString(font, hudData.get(element), x, y, textColor, false);
                y += lineHeight;
            }
        }
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() && event.getEntity() instanceof LocalPlayer player) {
            Minecraft mc = Minecraft.getInstance();
            // Check if we're on a server (not single player)
            boolean isOnServer = mc.getConnection() != null && !mc.hasSingleplayerServer();
            
            // Auto-enable ping when joining a server, disable when in single player
            HudConfig.SHOW_PING.set(isOnServer);
            HudConfig.SPEC.save();
        }
    }
}