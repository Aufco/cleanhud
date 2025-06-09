package com.AufCo.cleanhud;

import com.AufCo.cleanhud.client.config.HudConfig;
import com.AufCo.cleanhud.client.gui.HudConfigScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.client.ConfigScreenHandler;

@Mod("cleanhud")
public class CleanHud {
    public CleanHud() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, HudConfig.SPEC);
        
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new HudConfigScreen(screen))
        );
    }
}