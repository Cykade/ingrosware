package best.reich.ingrosware.hud.impl;

import best.reich.ingrosware.setting.annotation.Setting;
import net.minecraft.client.gui.ScaledResolution;
import best.reich.ingrosware.IngrosWare;
import best.reich.ingrosware.hud.Component;
import best.reich.ingrosware.hud.annotation.ComponentManifest;

import java.awt.*;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/20/2020
 **/
@ComponentManifest(label = "Coords", x = 2, y = 0, width = 100, height = 100)
public class CoordsComponent extends Component {

    @Setting("Color")
    public Color color = new Color(0x616161);

    public CoordsComponent() {
        setY(new ScaledResolution(mc).getScaledHeight() - 12);
        setHeight(IngrosWare.INSTANCE.getFontManager().getCurrentFont().getHeight());
    }

    @Override
    public void onDraw(ScaledResolution scaledResolution) {
        super.onDraw(scaledResolution);
        final float x = Math.round(mc.player.posX);
        final float y = Math.round(mc.player.posY);
        final float z = Math.round(mc.player.posZ);

        mc.fontRenderer.drawStringWithShadow(String.format("%s, %s, %s", x, y, z), getX(), getY() - ((getY() + getHeight() > scaledResolution.getScaledHeight() -
                10 && mc.ingameGUI.getChatGUI().getChatOpen()) ? 12 : 0), color.getRGB());
        if (getWidth() != mc.fontRenderer.getStringWidth(String.format("%s, %s, %s", x, y, z))) {
            setWidth(mc.fontRenderer.getStringWidth( String.format("%s, %s, %s", x, y, z)));
        }

    }
}
