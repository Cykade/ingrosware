package best.reich.ingrosware.gui.click.component.impl;

import best.reich.ingrosware.setting.impl.NumberSetting;
import best.reich.ingrosware.util.math.MathUtil;
import best.reich.ingrosware.util.math.MouseUtil;
import best.reich.ingrosware.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import best.reich.ingrosware.gui.click.component.Component;

/**
 * Made for IngrosWare-Recode
 *
 * @author oHare
 * @since 6/18/2020
 **/
public class NumberComponent extends Component {
    private final NumberSetting<Number> numberSetting;
    private boolean dragging;
    public NumberComponent(NumberSetting<Number> numberSetting, float posX, float posY, float offsetX, float offsetY, float width, float height) {
        super(numberSetting.getLabel(), posX, posY, offsetX, offsetY, width, height);
        this.numberSetting = numberSetting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        float sliderWidth = 94;
        final Number type = getNumberSetting().getValue();
        final double max = getNumberSetting().getMax().doubleValue();
        final double min = getNumberSetting().getMin().doubleValue();
        final double val = type.doubleValue();
        final double inc = getNumberSetting().getInc().doubleValue();

        float length = MathHelper.floor(((val) - min) / (max - min) * sliderWidth);

        RenderUtil.drawBorderedRect(getPosX(), getPosY(), 100, getHeight(), 0.5F, 0xff353535,0xff000000);
        RenderUtil.drawRect(getPosX() + length + 1f, getPosY() + 1, 4f, getHeight() - 2, 0xff505050);

        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(numberSetting.getLabel() + ": " + MathUtil.round(val, 2),
                getPosX() + 102, getPosY() + getHeight() - 1 - RenderUtil.getStringHeight(), -1);

        if (isDragging()) {
            if (type instanceof Double) {
                numberSetting.setValue(MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 2));
            } else if (type instanceof Float) {
                numberSetting.setValue((float) MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 2));
            } else if (type instanceof Long) {
                numberSetting.setValue(MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 1));
            } else if (type instanceof Integer) {
                numberSetting.setValue((int) MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 1));
            } else if (type instanceof Short) {
                numberSetting.setValue((short) MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 1));
            } else if (type instanceof Byte) {
                numberSetting.setValue((byte) MathUtil.round(((mouseX - getPosX()) * (max - min) / sliderWidth + min), 1));
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithin(mouseX, mouseY, getPosX(), getPosY(), 100, 14)) {
            setDragging(true);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isDragging())
            setDragging(false);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        setDragging(false);
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public NumberSetting<Number> getNumberSetting() {
        return numberSetting;
    }
}
