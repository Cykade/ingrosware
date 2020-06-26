package best.reich.ingrosware.module.impl.toggle.movement;

import best.reich.ingrosware.event.impl.entity.UpdateEvent;
import best.reich.ingrosware.module.ModuleCategory;
import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import tcb.bces.listener.Subscribe;

/**
 * @author TBM
 * @since 6/26/2020
 **/
@Toggleable(label = "AutoWalk", category = ModuleCategory.MOVEMENT, color = 0xfA72E3ff, bind = Keyboard.KEY_NONE)
public final class AutoWalkModule extends ToggleableModule {

    @Override
    public void onState() {
        if (mc.world != null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.world != null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

    @Override
    public void onDisable() {
        if (mc.world != null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
        }
    }
}
