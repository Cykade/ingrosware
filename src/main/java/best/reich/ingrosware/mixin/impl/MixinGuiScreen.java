package best.reich.ingrosware.mixin.impl;

import best.reich.ingrosware.IngrosWare;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.devs.ingrosware.event.impl.render.DrawDefaultBackgroundEvent;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    @Inject(method = "drawDefaultBackground", at = @At("HEAD"), cancellable = true)
    public void onGuiDrawBackground(CallbackInfo ci) {
        DrawDefaultBackgroundEvent drawDefaultBackgroundEvent = new DrawDefaultBackgroundEvent();
        IngrosWare.INSTANCE.getBus().post(drawDefaultBackgroundEvent);
        if (drawDefaultBackgroundEvent.isCancelled()) ci.cancel();
    }

}
