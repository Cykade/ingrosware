package best.reich.ingrosware.module.impl.toggle;

import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import best.reich.ingrosware.traits.Chatable;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import org.lwjgl.input.Keyboard;
import tcb.bces.event.EventType;
import tcb.bces.listener.Subscribe;
import best.reich.ingrosware.event.impl.entity.UpdateEvent;
import best.reich.ingrosware.event.impl.network.PacketEvent;
import best.reich.ingrosware.module.ModuleCategory;
import best.reich.ingrosware.util.other.chat.ChatColor;

import java.util.HashMap;
import java.util.Map;

/**
 * Made for IngrosWare-Recode
 *
 * @author oHare
 * @since 6/16/2020
 **/
@Toggleable(label = "TotemPopCounter", category = ModuleCategory.OTHER, color = 0xff72AE90, bind = Keyboard.KEY_NONE)
public class TotemPopCounterModule extends ToggleableModule implements Chatable {
    private final Map<String, Integer> popList = new HashMap<>();

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null || mc.player == null) return;

        mc.world.playerEntities.stream().filter(player -> player.getHealth() <= 0).filter(player -> popList.containsKey(player.getName())).forEach(player -> {
            clientChatMsg().appendText(player.getName(), ChatColor.DARK_AQUA).appendText(" died after popping ", ChatColor.DARK_RED)
                    .appendText(popList.get(player.getDisplayNameString()) + " totems!", ChatColor.GOLD).send();
            popList.remove(player.getName(), popList.get(player.getName()));
        });
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (mc.world == null || mc.player == null || event.getType() == EventType.PRE) return;

        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) { //totem
                final Entity entity = packet.getEntity(mc.world);

                if(!popList.containsKey(entity.getName())) {
                    popList.put(entity.getName(), 1);
                } else {
                    final int getNewPopped = popList.get(entity.getName()) + 1;
                    popList.put(entity.getName(), getNewPopped);
                    clientChatMsg().appendText(entity.getName(), ChatColor.DARK_AQUA).appendText(" popped ",
                            ChatColor.DARK_RED).appendText(getNewPopped + " totems!", ChatColor.GOLD).send();
                }
            }
        }
    }
}