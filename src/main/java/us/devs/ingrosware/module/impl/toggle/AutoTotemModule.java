package us.devs.ingrosware.module.impl.toggle;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import tcb.bces.listener.Subscribe;
import us.devs.ingrosware.event.impl.entity.UpdateEvent;
import us.devs.ingrosware.module.ModuleCategory;
import us.devs.ingrosware.module.annotation.Toggleable;
import us.devs.ingrosware.module.types.ToggleableModule;
import us.devs.ingrosware.setting.annotation.Clamp;
import us.devs.ingrosware.setting.annotation.Setting;


@Toggleable(label = "AutoTotem", category = ModuleCategory.COMBAT,color = 0xffffff10,bind = Keyboard.KEY_NONE)
public class AutoTotemModule extends ToggleableModule {
    @Clamp(minimum = "1", maximum = "22")
    @Setting("Health")
    public int health = 20;

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.currentScreen instanceof GuiContainer) return;
        if (shouldTotem() && !(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
            final int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
            if (getTotemSlot() != -1) {
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            }
        }
    }

    private boolean shouldTotem() {
        return (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= health || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA;
    }

    int getTotemSlot() {
        int totemSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                totemSlot = i;
                break;
            }
        }
        return totemSlot;
    }
}