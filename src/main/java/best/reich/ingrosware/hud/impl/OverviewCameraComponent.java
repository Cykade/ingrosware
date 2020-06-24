package best.reich.ingrosware.hud.impl;

import best.reich.ingrosware.hud.Component;
import best.reich.ingrosware.hud.annotation.ComponentManifest;
import best.reich.ingrosware.util.camera.BaseCamera;
import best.reich.ingrosware.util.camera.impl.OverviewCamera;
import best.reich.ingrosware.util.math.TimerUtil;
import best.reich.ingrosware.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
@ComponentManifest(label = "OverviewCamera", x = 50, y = 150, width = 120, height = 120, hidden = true)
public class OverviewCameraComponent extends Component {
    private BaseCamera camera;
    private final TimerUtil timer = new TimerUtil();

    public OverviewCameraComponent() {
        this.camera = new OverviewCamera((int) getWidth(), (int) getHeight());
    }

    @Override
    public void onDraw(ScaledResolution scaledResolution) {
        RenderUtil.drawBorderedRect(getX(), getY(), getWidth(), getHeight(), 1, new Color(0x2F2F2F).getRGB(), new Color(0x2F2F2F).getRGB());

        camera.draw(getX() + 2, getY() + 2, getX() + getWidth() - 2, getY() + getHeight() - 2);

        if (timer.reach(timer.convertToMS(30))) {
            camera.updateFramebuffer();
            timer.reset();
        }
    }
}
