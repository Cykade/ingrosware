package us.devs.ingrosware.module.impl.toggle;

import org.lwjgl.input.Keyboard;
import tcb.bces.listener.Subscribe;
import us.devs.ingrosware.event.impl.render.DrawDefaultBackgroundEvent;
import us.devs.ingrosware.module.ModuleCategory;
import us.devs.ingrosware.module.annotation.Toggleable;
import us.devs.ingrosware.module.types.ToggleableModule;

/**
 * @author TBM
 * @since 6/22/2020
 **/
@Toggleable(label = "AntiBackground", category = ModuleCategory.RENDER, color = 0xff33fe11, bind = Keyboard.KEY_NONE)
public class AntiGuiBackgroundModule extends ToggleableModule {

    @Subscribe
    public void onDrawDefaultBackground(DrawDefaultBackgroundEvent event) {
        event.setCancelled();
    }

}
