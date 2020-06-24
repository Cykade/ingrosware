package best.reich.ingrosware.util.camera.impl;

import best.reich.ingrosware.util.camera.BaseCamera;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
public class OverviewCamera extends BaseCamera {

    public OverviewCamera(int width, int height) {
        super(true, width, height);
    }

    @Override
    public void updateFramebuffer() {
        setToEntityPosition(mc.player);

        setCameraRotationYaw(mc.player.rotationYaw);
        setCameraRotationPitch(90);

        super.updateFramebuffer();
    }
}
