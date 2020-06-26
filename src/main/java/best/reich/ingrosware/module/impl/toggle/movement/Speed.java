package best.reich.ingrosware.module.impl.toggle.movement;

import best.reich.ingrosware.event.impl.entity.MotionEvent;
import best.reich.ingrosware.event.impl.entity.UpdateEvent;
import best.reich.ingrosware.module.ModuleCategory;
import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import best.reich.ingrosware.setting.annotation.Mode;
import best.reich.ingrosware.setting.annotation.Setting;
import net.minecraft.init.MobEffects;
import org.lwjgl.input.Keyboard;
import tcb.bces.event.EventType;
import tcb.bces.listener.Subscribe;

import java.util.Objects;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/26/2020
 **/
@Toggleable(label = "Speed", category = ModuleCategory.MOVEMENT, color = 0x4696AC, bind = Keyboard.KEY_NONE)
public class Speed extends ToggleableModule {
    @Setting("Mode")
    @Mode({"NCP", "NCPHOP"})
    public String mode = "NCP";

    @Setting("AutoJump")
    public boolean autoJump = true;

    private int stage = 1;
    private double moveSpeed, lastDist;

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null || mc.player == null) return;
        switch (mode.toUpperCase()) {
            case "NCPHOP":
                if (event.getType() == EventType.PRE)
                    lastDist = Math.sqrt(((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX)) + ((mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ)));
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onMotion(MotionEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (mode.toUpperCase().equals("NCPHOP")) {
            switch (stage) {
                case 0:
                    ++stage;
                    lastDist = 0.0D;
                    break;
                case 2:
                    if(autoJump) {
                       handleY(event);
                    } else if(mc.gameSettings.keyBindJump.isKeyDown()) {
                        handleY(event);
                    }
                    break;
                case 3:
                    moveSpeed = lastDist - (0.76 * (lastDist - getBaseMoveSpeed()));
                    break;
                default:
                    if ((mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, mc.player.motionY, 0.0D)).size() > 0 || mc.player.isCollidedVertically) && stage > 0) {
                        stage = mc.player.moveForward == 0.0F && mc.player.moveStrafing == 0.0F ? 0 : 1;
                    }
                    moveSpeed = lastDist - lastDist / 159.0D;
                    break;
            }
            moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
            double forward = mc.player.movementInput.moveForward, strafe = mc.player.movementInput.moveStrafe, yaw = mc.player.rotationYaw;
            if (forward == 0.0F && strafe == 0.0F) {
                event.setX(0);
                event.setZ(0);
            }
            if (forward != 0 && strafe != 0) {
                forward = forward * Math.sin(Math.PI / 4);
                strafe = strafe * Math.cos(Math.PI / 4);
            }
            event.setX((forward * moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * moveSpeed * Math.cos(Math.toRadians(yaw))) * 0.99D);
            event.setZ((forward * moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * moveSpeed * -Math.sin(Math.toRadians(yaw))) * 0.99D);
            ++stage;
        }
    }

    private void handleY(MotionEvent event) {
        double motionY = 0.40123128;
        if ((mc.player.moveForward != 0.0F || mc.player.moveStrafing != 0.0F) && mc.player.onGround) {
            if (mc.player.isPotionActive(MobEffects.JUMP_BOOST))
                motionY += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
            event.setY(mc.player.motionY = motionY);
            moveSpeed *= 2.149;
        }
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.272;
        if (mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + (0.2 * amplifier);
        }
        return baseSpeed;
    }

}
