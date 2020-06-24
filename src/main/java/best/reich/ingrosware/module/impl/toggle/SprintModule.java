package best.reich.ingrosware.module.impl.toggle;

import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import org.lwjgl.input.Keyboard;
import tcb.bces.event.EventType;
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
public final class SprintModule extends ToggleableModule {

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        // sprinting gets updated server side at the head of EntityPlayerSP#onUpdateWalkingPlayer
        // therefore setting on post is pointless since pre and post have <1ms difference
        if (event.getType() == EventType.PRE) {
            // if you want legit sprint just set pressed to true.. lol
            mc.gameSettings.keyBindSprint.pressed = true;
        }
    }

//    private boolean canSprint(EntityPlayerSP player) {
//        return !player.isSprinting() && !player.isSneaking() && player.movementInput.moveForward > 0.8f &&
//                (player.getFoodStats().getFoodLevel() > 6f || player.capabilities.allowFlying) &&
//                !player.isPotionActive(MobEffects.BLINDNESS);
//    }
}