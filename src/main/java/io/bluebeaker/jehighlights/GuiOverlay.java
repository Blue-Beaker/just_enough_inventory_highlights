package io.bluebeaker.jehighlights;

import java.awt.Rectangle;

import mezz.jei.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOverlay {
	boolean enableOverlay = false;
	private long lastClickTime = 0;
	public Rectangle searchBoxShape;

	public void onClickSearchbar() {
		long thisClickTime = Minecraft.getSystemTime();
		if (lastClickTime > 0 && thisClickTime - lastClickTime <= JEHighlightsConfig.doubleclickinterval) {
			lastClickTime = 0;
			this.enableOverlay = !enableOverlay;
		} else {
			lastClickTime = thisClickTime;
		}
	}

	@SubscribeEvent
	public void onDrawScreen(GuiContainerEvent.DrawForeground event) {
		GuiContainer gui = event.getGuiContainer();
		onDrawInventory(gui);
	}

	private void onDrawInventory(GuiContainer screen) {
		if (!enableOverlay)
			return;
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

	private void drawItemHighlight(int xpos, int ypos) {
		GlStateManager.disableDepth();
		GuiContainer.drawRect(xpos, ypos, xpos + 16, ypos + 16, 0x80000000);
		GlStateManager.enableDepth();
	}

	// @SubscribeEvent
	// public void drawSearchBarOverlay(GuiScreenEvent event) {
	// 	if (!enableOverlay)
	// 		return;
	// 	if (searchBoxShape == null)
	// 		return;
	// 	int x = searchBoxShape.x;
	// 	int y = searchBoxShape.y;
	// 	int w = searchBoxShape.width;
	// 	int h = searchBoxShape.height;
	// 	GlStateManager.disableDepth();
	// 	GuiContainer.drawRect(x, y, x + w, y + 1, 0xFFFFFF00);
	// 	GuiContainer.drawRect(x, y, x + 1, y + h, 0xFFFFFF00);
	// 	GuiContainer.drawRect(x, y + h - 1, x + w, y + h, 0xFFFFFF00);
	// 	GuiContainer.drawRect(x + w - 1, y, x + w, y + h, 0xFFFFFF00);
	// 	GlStateManager.enableDepth();
	// }
}
