package io.bluebeaker.jehighlights;

import org.apache.logging.log4j.Logger;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = JEHighlights.MODID, name = JEHighlights.NAME, version = JEHighlights.VERSION)
public class JEHighlights
{
    public static final String MODID = "jehighlights";
    public static final String NAME = "JEHighlights";
    public static final String VERSION = "1.0";
    
    public MinecraftServer server;

    private static Logger logger;

    public static GuiOverlay guiOverlay;
    
    public JEHighlights() {
        MinecraftForge.EVENT_BUS.register(this);
        guiOverlay=new GuiOverlay();
        MinecraftForge.EVENT_BUS.register(guiOverlay);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    public void logInfo(String log){
        logger.info(log);
    }
}
