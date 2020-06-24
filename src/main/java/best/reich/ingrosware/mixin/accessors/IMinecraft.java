package best.reich.ingrosware.mixin.accessors;

import net.minecraft.entity.Entity;
import net.minecraft.util.Session;

public interface IMinecraft {

    Entity getRenderViewEntity();

    void setSession(Session session);

    void setRightClickDelayTimer(int delay);
}
