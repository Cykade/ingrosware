package us.devs.ingrosware.mixin.impl;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import us.devs.ingrosware.IngrosWare;
import us.devs.ingrosware.event.impl.other.GetRainStrengthEvent;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    public void getRainStrength(float delta, CallbackInfoReturnable<Float> cir) {
        GetRainStrengthEvent getRainStrengthEvent = new GetRainStrengthEvent();
        IngrosWare.INSTANCE.getBus().post(getRainStrengthEvent);
        if (getRainStrengthEvent.isCancelled()) cir.setReturnValue(0f);
    }

}
