package best.reich.ingrosware.util.camera.impl;

import best.reich.ingrosware.util.camera.BaseCamera;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
public class RearviewCamera extends BaseCamera {

    public RearviewCamera(int width, int height) {
        super(true, width, height);
    }

    @Override
    public void updateFramebuffer() {
        setToEntityPosition(mc.player);

        setCameraRotationYaw(mc.player.rotationYaw + 180);
        setCameraRotationPitch(-mc.player.rotationPitch);

        super.updateFramebuffer();
    }
}
