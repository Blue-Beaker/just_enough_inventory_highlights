package io.bluebeaker.jehighlights;

import mezz.jei.config.Config;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOverlay {
	@SubscribeEvent
	public void onDrawScreen(GuiContainerEvent event) {
		GuiContainer gui = event.getGuiContainer();
		onDrawInventory(gui);
	}
	private void onDrawInventory(GuiContainer screen) {
		String filterText = Config.getFilterText();
		if (filterText.length() == 0)
			return;
		Container inventorySlots = screen.inventorySlots;
		for (Slot slot : inventorySlots.inventorySlots) {
			int xpos = slot.xPos;
			int ypos = slot.yPos;
			if (!slot.getHasStack()) {
				drawItemHighlight(xpos, ypos);
				continue;
			}
			;
			ItemMatcher textMatcher = new ItemMatcher(filterText);
			if (!textMatcher.matchItem(slot.getStack())) {
				drawItemHighlight(xpos, ypos);
			}
		}
	}
	private void drawItemHighlight(int xpos,int ypos){
		GlStateManager.disableDepth();
		GuiContainer.drawRect(xpos, ypos, xpos + 16, ypos + 16, 0x80000000);
		GlStateManager.enableDepth();
	}
}
