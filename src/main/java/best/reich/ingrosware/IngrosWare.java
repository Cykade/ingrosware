package best.reich.ingrosware;

import best.reich.ingrosware.command.manager.CommandManager;
import best.reich.ingrosware.event.EventDispatcher;
import best.reich.ingrosware.font.FontManager;
import best.reich.ingrosware.friend.FriendManager;
import best.reich.ingrosware.hud.manager.ComponentManager;
import best.reich.ingrosware.macro.MacroManager;
import best.reich.ingrosware.module.manager.ModuleManager;
import best.reich.ingrosware.profile.ProfileManager;
import best.reich.ingrosware.setting.SettingManager;
import best.reich.ingrosware.traits.Closeable;
import best.reich.ingrosware.traits.Labelable;
import best.reich.ingrosware.traits.Startable;
import best.reich.ingrosware.util.rpc.DiscordPresence;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import tcb.bces.bus.DRCEventBus;
import tcb.bces.bus.DRCExpander;

import java.io.*;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/13/2020
 **/
public enum IngrosWare implements Startable, Closeable, Labelable {
    INSTANCE;

    private DRCExpander<DRCEventBus> bus;
    private File baseDir;

    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private ComponentManager componentManager;
    private FontManager fontManager;
    private FriendManager friendManager;
    private MacroManager macroManager;

    private final SettingManager settingManager = new SettingManager();
    private final ProfileManager profileManager = new ProfileManager();
    private DiscordPresence presence;

    private boolean rpc = true;

    @Override
    public void start() {
        this.baseDir = new File(Minecraft.getMinecraft().mcDataDir, "Ingros");
        final DRCEventBus baseBus = new DRCEventBus();
        baseBus.setDispatcher(EventDispatcher.class);
        this.bus = new DRCExpander<>(baseBus);

        this.fontManager = new FontManager(new File(baseDir, "fonts"));
        this.componentManager = new ComponentManager(new File(baseDir, "components"));
        this.macroManager = new MacroManager(new File(baseDir, "macros"));
        this.moduleManager = new ModuleManager(new File(baseDir, "modules"));
        this.commandManager = new CommandManager(new File(baseDir, "commands"));
        this.friendManager = new FriendManager(baseDir);
        this.friendManager.start();
        this.fontManager.start();
        this.componentManager.start();
        this.macroManager.start();
        this.moduleManager.start();
        this.commandManager.start();
        this.profileManager.start();
        this.loadSettings();

        if(isRpc())
            this.presence = new DiscordPresence();

        bus.bind();
    }

    @Override
    public void close() {
        if(!baseDir.exists())
            baseDir.mkdirs();
        this.friendManager.close();
        this.fontManager.close();
        this.componentManager.close();
        this.macroManager.close();
        this.moduleManager.close();
        this.friendManager.close();
        this.profileManager.close();
        this.saveSettings();
    }

    @Override
    public String getLabel() {
        return "IngrosWare";
    }

    public void setRpc(boolean rpc) {
        this.rpc = rpc;
    }

    public boolean isRpc() {
        return rpc;
    }

    public void loadSettings() {
        File f = new File(baseDir, "settings.json");

        if (f.exists()) {
            try (Reader reader = new FileReader(f)) {
                JsonElement element = new JsonParser().parse(reader);
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("RPC"))
                        setRpc(object.get("RPC").getAsBoolean());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void saveSettings() {
        File f = new File(baseDir, "settings.json");

        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (f.exists()) {
            JsonObject object = new JsonObject();
            object.addProperty("RPC", isRpc());

            try (Writer writer = new FileWriter(f)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(object));
            } catch (IOException e) {
                f.delete();
                e.printStackTrace();
            }
        }

    }

    public DRCExpander<DRCEventBus> getBus() {
        return bus;
    }

    public SettingManager getSettingManager() {
        return settingManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public MacroManager getMacroManager() {
        return macroManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public File getBaseDir() {
        return baseDir;
    }

}
