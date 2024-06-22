package io.bluebeaker.jehighlights;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = JEHighlights.MODID,type = Type.INSTANCE,category = "general")
public class JEHighlightsConfig {
    @Comment("Doubleclick interval in milliseconds, to toggle highlight mode.")
    @LangKey("config.jehighlights.doubleclickinterval.name")
    public static int doubleclickinterval = 500;
}