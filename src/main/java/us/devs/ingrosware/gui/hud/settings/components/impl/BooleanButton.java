package us.devs.ingrosware.gui.hud.settings.components.impl;

import us.devs.ingrosware.IngrosWare;
import us.devs.ingrosware.gui.hud.settings.components.HudSetting;
import us.devs.ingrosware.setting.impl.BooleanSetting;
import us.devs.ingrosware.util.math.MouseUtil;
import us.devs.ingrosware.util.render.RenderUtil;

import java.awt.*;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/13/2020
 **/
public class BooleanButton extends HudSetting {
    private final BooleanSetting booleanSetting;

    public BooleanButton(BooleanSetting booleanSetting, float posX, float posY) {
        super(booleanSetting.getLabel(), posX, posY);
        this.booleanSetting = booleanSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean isHovered = MouseUtil.mouseWithin(mouseX,mouseY,getPosX(), getPosY(), 10, 10);
        RenderUtil.drawBorderedRect(getPosX(), getPosY(), 10, 10,0.5F, new Color(36,41,51,255).getRGB(),isHovered ? new Color(0x505760).getRGB() : new Color(0xFF3b4149).getRGB());
        IngrosWare.INSTANCE.getFontManager().getCurrentFont().drawString(booleanSetting.getLabel(), getPosX() + 12, getPosY() + 3, -1);
        if(booleanSetting.getValue()) {
            RenderUtil.drawCheckMark(getPosX() + 5,getPosY() - 2,10,new Color(0, 107, 214, 255).getRGB());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);


        if(MouseUtil.mouseWithin(mouseX,mouseY,getPosX(), getPosY(), 10, 10) && mouseButton == 0) {
            booleanSetting.toggle();
        }
    }
}
