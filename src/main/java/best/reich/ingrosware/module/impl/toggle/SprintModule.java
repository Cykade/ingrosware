package best.reich.ingrosware.module.impl.toggle;

import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import net.minecraft.init.MobEffects;
import org.lwjgl.input.Keyboard;
import tcb.bces.listener.Subscribe;
import best.reich.ingrosware.event.impl.entity.UpdateEvent;
import best.reich.ingrosware.module.ModuleCategory;

/**
 * Made for IngrosWare-Recode
 *
 * @author oHare
 * @since 6/14/2020
 **/
@Toggleable(label = "Sprint", category = ModuleCategory.MOVEMENT,color = 0xff72B190,bind = Keyboard.KEY_NONE)
public class SprintModule extends ToggleableModule {

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        mc.player.setSprinting(canSprint());
    }

    private boolean canSprint() {
        return !mc.player.isSprinting() && !mc.player.isSneaking() && mc.player.movementInput.moveForward > 0.0f &&
                (mc.player.getFoodStats().getFoodLevel() >= 6f || mc.player.capabilities.allowFlying) &&
                !mc.player.isPotionActive(MobEffects.BLINDNESS);
    }
}