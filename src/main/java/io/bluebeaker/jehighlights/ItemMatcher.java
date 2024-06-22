package io.bluebeaker.jehighlights;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.config.Config;
import mezz.jei.config.Config.SearchMode;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMatcher {
    public String[] texts;

    public ItemMatcher(String filterText) {
        texts = filterText.toLowerCase().split(" ");
    }

    public boolean matchSingleText(String matcher, @Nonnull ItemStack stack) {
        SingleMatcher singleMatcher = new SingleMatcher(matcher, stack);
        if (matcher.startsWith("@", 0)) { // Modid
            return singleMatcher.matchModID();
        } else if (matcher.startsWith("#", 0)) { // Tooltip
            return singleMatcher.matchTooltips();
        } else if (matcher.startsWith("$", 0)) { // OreDict
            return singleMatcher.matchOreDict();
        } else if (matcher.startsWith("%", 0)) { // CreativeTab
            return singleMatcher.matchCreativeTab();
        } else if (matcher.startsWith("&", 0)) { // ItemID
            return singleMatcher.matchItemID();
        } else { // ItemName
            return singleMatcher.matchName() ||
            (Config.getModNameSearchMode()==SearchMode.ENABLED&&singleMatcher.matchModID())||
            (Config.getResourceIdSearchMode()==SearchMode.ENABLED&&singleMatcher.matchItemID())||
            (Config.getOreDictSearchMode()==SearchMode.ENABLED&&singleMatcher.matchOreDict())||
            (Config.getCreativeTabSearchMode()==SearchMode.ENABLED&&singleMatcher.matchCreativeTab())||
            (Config.getTooltipSearchMode()==SearchMode.ENABLED&&singleMatcher.matchTooltips());
        }
    }

    private class SingleMatcher{
        String matcher;
        @Nonnull ItemStack stack;
        SingleMatcher(String matcher, @Nonnull ItemStack stack){
            if("@#$%^&".indexOf(matcher.charAt(0))>-1){
                matcher=matcher.substring(1);
            }
            this.matcher=matcher;
            this.stack=stack;
        }
        boolean matchName(){
            return stack.getDisplayName().toLowerCase().contains(matcher);
        }
        boolean matchModID() {
            ResourceLocation id = stack.getItem().getRegistryName();
            return id != null && id.getNamespace().toLowerCase().contains(matcher);
        }

        boolean matchTooltips() {
            ITooltipFlag.TooltipFlags tooltipFlag = Config.getSearchAdvancedTooltips() ? ITooltipFlag.TooltipFlags.ADVANCED
                    : ITooltipFlag.TooltipFlags.NORMAL;
            List<String> tooltip = stack.getTooltip(Minecraft.getMinecraft().player, tooltipFlag);
            for (String line : tooltip) {
                if (line.toLowerCase().contains(matcher)) {
                    return true;
                }
            }
            return false;
        }
        boolean matchOreDict(){
            for (int oreID : OreDictionary.getOreIDs(stack)) {
                if (OreDictionary.getOreName(oreID).toLowerCase().contains(matcher)) {
                    return true;
                }
            }
            return false;
        }
        boolean matchCreativeTab(){
            for (CreativeTabs tab : stack.getItem().getCreativeTabs()) {
                if (Translator.translateToLocal(tab.getTranslationKey()).toLowerCase().contains(matcher)) {
                    return true;
                }
            }
            return false;
        }
        boolean matchItemID(){
            ResourceLocation id = stack.getItem().getRegistryName();
            return id != null && id.toString().toLowerCase().contains(matcher);
        }
    }
    public boolean matchItem(ItemStack stack) {
        if(stack==null) return false;
        for (String text : texts) {
            if (!matchSingleText(text, stack)) {
                return false;
            }
        }
        return true;
    }
}
