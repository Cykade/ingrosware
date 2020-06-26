package best.reich.ingrosware.hud.impl;

import best.reich.ingrosware.IngrosWare;
import best.reich.ingrosware.hud.Component;
import best.reich.ingrosware.hud.annotation.ComponentManifest;
import best.reich.ingrosware.module.impl.toggle.render.TotemPopCounterModule;
import best.reich.ingrosware.setting.annotation.Setting;
import best.reich.ingrosware.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/20/2020
 **/
@ComponentManifest(label = "TotemCounter", x = 2, y = 100, width = 100, height = 13, hidden = true)
public class TotemCounterComponent extends Component {
    @Setting("Color")
    public Color color = new Color(0x616161);

    @Setting("Format")
    public String format = "Totems: %s";

    @Override
    public void onDraw(ScaledResolution scaledResolution) {
        final String localPlayerTotemCount = String.format(format, "" + ChatFormatting.WHITE + totemCount());

        if (IngrosWare.INSTANCE.getModuleManager().getModule("TotemPopCounter").isEnabled()) {
            RenderUtil.drawBorderedRect(getX(),
                    getY(), getWidth(), getHeight(), 1, 0x75101010, 0x90000000);

            RenderUtil.drawBorderedRect(getX(),
                    getY(), getWidth(), 13, 1, 0x75101010, 0x90000000);

            final Map<UUID, Integer> players = TotemPopCounterModule.POP_LIST;

            int height = 13;

            for (UUID player : players.keySet()) {
                if (players.get(player) <= 0) {
                    continue;
                }
                final EntityPlayer playerSP = mc.world.getPlayerEntityByUUID(player);
                if(playerSP != null) {
                    final String playerTotemCount = playerSP.getName() + ": " + ChatFormatting.WHITE + players.get(player);

                    setWidth(Math.max(100, Math.max(mc.fontRenderer.getStringWidth(playerTotemCount), mc.fontRenderer.getStringWidth(localPlayerTotemCount))));

                    mc.fontRenderer.drawStringWithShadow(playerTotemCount,
                            getX() + 2, getY() + height + 2, color.getRGB());

                    height += 13;
                }
            }

            setHeight(height);
        } else {
            setWidth(Math.max(100, mc.fontRenderer.getStringWidth(localPlayerTotemCount)));
        }

        mc.fontRenderer.drawStringWithShadow(String.format(format, "" + ChatFormatting.WHITE + totemCount()),
                getX() + 2, getY() + 2, color.getRGB());
    }

    private int totemCount() {
        int count = 0;
        for (int i = 0; i < 45; ++i) {
            if (!mc.player.inventory.getStackInSlot(i).isEmpty() && mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                count++;
            }
        }
        return count;
    }
}
