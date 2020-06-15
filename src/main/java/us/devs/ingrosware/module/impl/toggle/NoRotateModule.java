package us.devs.ingrosware.module.impl.toggle;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.lwjgl.input.Keyboard;
import tcb.bces.event.EventType;
import tcb.bces.listener.Subscribe;
import us.devs.ingrosware.event.impl.network.PacketEvent;
import us.devs.ingrosware.mixin.accessors.ISPacketPosLook;
import us.devs.ingrosware.module.ModuleCategory;
import us.devs.ingrosware.module.annotation.Toggleable;
import us.devs.ingrosware.module.types.ToggleableModule;

/**
 * Made for IngrosWare-Recode
 *
 * @author oHare
 * @since 6/14/2020
 **/
@Toggleable(label = "NoRotate", category = ModuleCategory.OTHER,color = 0xfff33f00,bind = Keyboard.KEY_NONE)
public class NoRotateModule extends ToggleableModule {

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (mc.world == null) return;
        if (event.getType() == EventType.POST) {
            if (event.getPacket() instanceof SPacketPlayerPosLook && mc.player != null) {
                final SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
                ((ISPacketPosLook) packet).setYaw(mc.player.rotationYaw);
                ((ISPacketPosLook) packet).setPitch(mc.player.rotationPitch);
            }
        }
    }
}
