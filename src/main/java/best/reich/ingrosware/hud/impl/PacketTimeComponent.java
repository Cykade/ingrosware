package best.reich.ingrosware.hud.impl;

import best.reich.ingrosware.IngrosWare;
import best.reich.ingrosware.event.impl.network.PacketEvent;
import best.reich.ingrosware.hud.Component;
import best.reich.ingrosware.hud.annotation.ComponentManifest;
import best.reich.ingrosware.setting.annotation.Setting;
import best.reich.ingrosware.util.math.TimerUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import tcb.bces.event.EventType;
import tcb.bces.listener.IListener;
import tcb.bces.listener.Subscribe;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
@ComponentManifest(label = "PacketTime", x = 10, y = 4, width = 100, height = 100, hidden = true)
public class PacketTimeComponent extends Component implements IListener {
    @Setting("Color")
    public Color color = new Color(0x616161);

    @Setting("Format")
    public String format = "Server has not responded for %ss";

    private final TimerUtil timerUtil = new TimerUtil();

    public PacketTimeComponent() {
        setHeight(mc.fontRenderer.FONT_HEIGHT);
        IngrosWare.INSTANCE.getBus().register(this);
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.POST) {
            timerUtil.reset();
        }
    }

    @Override
    public void onDraw(ScaledResolution scaledResolution) {
        super.onDraw(scaledResolution);
        if(timerUtil.reach(1000)) {
            mc.fontRenderer.drawString(String.format(format, ""  +
                    ChatFormatting.WHITE + new DecimalFormat("0.0").format((double) timerUtil.time() / 1000)), (int) getX(), (int) getY(), color.getRGB());
            setWidth(mc.fontRenderer.getStringWidth(String.format(format, ""  + ChatFormatting.WHITE + new DecimalFormat("0.0").format((double) timerUtil.time() / 1000))));
        }
    }

    @Override
    public boolean isEnabled() {
        return !isHidden() && mc.world != null;
    }
}
