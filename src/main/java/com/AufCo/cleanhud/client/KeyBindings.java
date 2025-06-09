package com.AufCo.cleanhud.client;

import com.AufCo.cleanhud.client.gui.HudConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "cleanhud", value = Dist.CLIENT)
public class KeyBindings {
    
    public static final KeyMapping OPEN_HUD_CONFIG = new KeyMapping(
        "key.cleanhud.open_config",
        GLFW.GLFW_KEY_UNKNOWN,
        "key.categories.cleanhud"
    );

    @Mod.EventBusSubscriber(modid = "cleanhud", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_HUD_CONFIG);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null && OPEN_HUD_CONFIG.consumeClick()) {
            mc.setScreen(new HudConfigScreen(null));
        }
    }
}