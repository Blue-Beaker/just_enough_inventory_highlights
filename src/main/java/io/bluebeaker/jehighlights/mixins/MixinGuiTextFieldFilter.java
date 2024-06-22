package io.bluebeaker.jehighlights.mixins;

import java.awt.Rectangle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.bluebeaker.jehighlights.JEHighlights;
import mezz.jei.input.GuiTextFieldFilter;

@Mixin(GuiTextFieldFilter.class)
public class MixinGuiTextFieldFilter {
    @Inject(method = "handleMouseClicked(III)Z",at = @At("HEAD"),remap = false)
    public void onClickSearchbar(int mouseX, int mouseY, int mouseButton,CallbackInfoReturnable<Boolean> cir){
        if(mouseButton==0)
        JEHighlights.guiOverlay.onClickSearchbar();
    }
    // @Inject(method = "updateBounds(Ljava/awt/Rectangle;)V",at = @At("HEAD"),remap = false)
    // public void getRectangle(Rectangle rectangle ,CallbackInfo ci){
    //     JEHighlights.guiOverlay.searchBoxShape=new Rectangle(rectangle);
    // }
}
