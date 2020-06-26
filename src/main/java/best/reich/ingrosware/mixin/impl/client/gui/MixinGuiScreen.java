package best.reich.ingrosware.mixin.impl.client.gui;

import best.reich.ingrosware.IngrosWare;
import best.reich.ingrosware.event.impl.render.DrawDefaultBackgroundEvent;
import best.reich.ingrosware.event.impl.render.RenderToolTipEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    @Inject(method = "drawDefaultBackground", at = @At("HEAD"), cancellable = true)
    public void onGuiDrawBackground(CallbackInfo ci) {
        DrawDefaultBackgroundEvent drawDefaultBackgroundEvent = new DrawDefaultBackgroundEvent();
        IngrosWare.INSTANCE.getBus().post(drawDefaultBackgroundEvent);
        if (drawDefaultBackgroundEvent.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo info) {
        if(stack != null) {
            final RenderToolTipEvent event = new RenderToolTipEvent(stack, x, y);
            IngrosWare.INSTANCE.getBus().post(event);

            if(event.isCancelled()) {
                info.cancel();
            }
        }
    }


}
