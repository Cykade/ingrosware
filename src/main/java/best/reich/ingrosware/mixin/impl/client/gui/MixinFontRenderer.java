package best.reich.ingrosware.mixin.impl.client.gui;

import best.reich.ingrosware.IngrosWare;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/18/2020
 **/
@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {

    @Shadow
    protected abstract void renderStringAtPos(String text, boolean shadow);

    /**
     * Thanks TBM for doing the Redirect
     *
     * @param fontRenderer
     * @param text
     * @param shadow
     * @param targetmethodtext
     * @param x
     * @param y
     * @param color
     * @param dropShadow
     */
    @Redirect(method = "renderString", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V", ordinal = 0))
    public void renderStringAtPos(FontRenderer fontRenderer, String text, boolean shadow, String targetmethodtext, float x, float y, int color, boolean dropShadow) {
        if (IngrosWare.INSTANCE.getFontManager() != null && IngrosWare.INSTANCE.getFontManager().isUsingCustomFont()) {
            IngrosWare.INSTANCE.getFontManager().getCurrentFont().drawString(text, x, y, color, shadow);
        } else {
            this.renderStringAtPos(text, shadow);
        }
    }

}
