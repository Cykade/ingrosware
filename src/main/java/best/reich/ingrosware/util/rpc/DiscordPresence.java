package best.reich.ingrosware.util.rpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
public class DiscordPresence {
    private static DiscordRPC RPC = DiscordRPC.INSTANCE;
    private DiscordRichPresence presence;
    private long startTime;

    public DiscordPresence() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        RPC.Discord_Initialize("725079838410539118", handlers, true, "");
        this.startTime = System.currentTimeMillis() / 1000L;

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.presence = new DiscordRichPresence();
                this.presence.startTimestamp = startTime;
                this.presence.largeImageText = "Ingros Client";
                this.presence.largeImageKey = "large";
                this.presence.smallImageText = "https://reich.best/";
                this.presence.details = "IngrosWare";

                ServerData data = Minecraft.getMinecraft().getCurrentServerData();

                if(data != null && Minecraft.getMinecraft().player != null) {
                    this.presence.state = "Multiplayer: " + data.serverIP;
                } else if(Minecraft.getMinecraft().isSingleplayer()) {
                    this.presence.state = "Singleplayer";
                } else {
                    this.presence.state = Minecraft.getMinecraft().getSession().getUsername();
                }
                RPC.Discord_UpdatePresence(this.presence);
                RPC.Discord_RunCallbacks();

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();

    }

    public void shutdown() {
        RPC.Discord_Shutdown();
    }
}
