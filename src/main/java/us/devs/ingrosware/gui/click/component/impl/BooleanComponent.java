package us.devs.ingrosware.gui.click.component.impl;

import net.minecraft.client.Minecraft;
import us.devs.ingrosware.gui.click.component.Component;
import us.devs.ingrosware.setting.impl.BooleanSetting;
import us.devs.ingrosware.util.math.MouseUtil;
import us.devs.ingrosware.util.render.RenderUtil;

import java.awt.*;

/**
 * Made for IngrosWare-Recode
 *
 * @author oHare
 * @since 6/18/2020
 **/
public class BooleanComponent extends Component {
    private final BooleanSetting booleanSetting;
    public BooleanComponent(BooleanSetting booleanSetting, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(booleanSetting.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.booleanSetting = booleanSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtil.drawBorderedRect(getPosX(),getPosY(),14,getHeight(),0.5f,getBooleanSetting().getValue() ?  0xff505050:0xff353535,0xff000000);
        if (getBooleanSetting().getValue()) RenderUtil.drawCheckMark(getPosX() + 2.5f,getPosY(),14,-1);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(getLabel(),getPosX() + 16,getPosY() + getHeight() - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT,-1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && MouseUtil.mouseWithin(mouseX,mouseY,getPosX(),getPosY(),14,getHeight())) getBooleanSetting().setValue(!getBooleanSetting().getValue());
    }

    public BooleanSetting getBooleanSetting() {
        return booleanSetting;
    }
}
