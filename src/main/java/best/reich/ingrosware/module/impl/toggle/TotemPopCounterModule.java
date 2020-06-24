package best.reich.ingrosware.module.impl.toggle;

import best.reich.ingrosware.event.impl.network.PacketEvent;
import best.reich.ingrosware.module.ModuleCategory;
import best.reich.ingrosware.module.annotation.Toggleable;
import best.reich.ingrosware.module.types.ToggleableModule;
import best.reich.ingrosware.traits.Chatable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import org.lwjgl.input.Keyboard;
import tcb.bces.listener.Subscribe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Made for IngrosWare-Recode
 *
 * @author neva lack
 * @since 6/23/2020
 **/
@Toggleable(label = "TotemPopCounter", category = ModuleCategory.RENDER, color = 0xFF72AE90, bind = Keyboard.KEY_NONE)
public final class TotemPopCounterModule extends ToggleableModule implements Chatable {

    // use player UUID instead of name
    public static final Map<UUID, Integer> POP_LIST = new HashMap<>();

    @Override
    public void onDisable() {
        POP_LIST.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            // on totem use
            if (packet.getOpCode() == 35) {
                final Entity entity = packet.getEntity(mc.world);

                // don't worry about local player
                //if (entity instanceof EntityOtherPlayerMP) {
                final EntityPlayer player = (EntityPlayer) entity;

                final UUID uuid = player.getUniqueID();

                if (!POP_LIST.containsKey(uuid)) {
                    POP_LIST.put(uuid, 1);
                } else {
                    POP_LIST.put(uuid, POP_LIST.get(uuid) + 1);
                }
            }
        }
    }
}