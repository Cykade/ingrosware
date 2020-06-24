package best.reich.ingrosware.util.rpc;

import best.reich.ingrosware.IngrosWare;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

/**
 * made for Ingros
 *
 * @author Brennan
 * @since 6/23/2020
 **/
public class DiscordPresence {
    private long startTime;

    public DiscordPresence() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {}).build();
        DiscordRPC.discordInitialize("725079838410539118", handlers, false);
        DiscordRPC.discordRegister("725079838410539118", "");
        this.startTime = System.currentTimeMillis() / 1000L;

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                DiscordRichPresence.Builder presence;

                ServerData data = Minecraft.getMinecraft().getCurrentServerData();

                if(data != null && Minecraft.getMinecraft().player != null) {
                    presence = new DiscordRichPresence.Builder("Multiplayer: " + data.serverIP);
                    presence.setDetails("https://reich.best/");
                    presence.setStartTimestamps(startTime);
                    presence.setBigImage("large", IngrosWare.INSTANCE.getLabel());
                } else if(Minecraft.getMinecraft().isSingleplayer()) {
                    presence = new DiscordRichPresence.Builder("Singleplayer");
                    presence.setDetails("https://reich.best/");
                    presence.setStartTimestamps(startTime);
                    presence.setBigImage("large", IngrosWare.INSTANCE.getLabel());
                } else {
                    presence = new DiscordRichPresence.Builder(Minecraft.getMinecraft().getSession().getUsername());
                    presence.setDetails("https://reich.best/");
                    presence.setStartTimestamps(startTime);
                    presence.setBigImage("large", IngrosWare.INSTANCE.getLabel());
                }
                DiscordRPC.discordUpdatePresence(presence.build());

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();

    }

    public void shutdown() {
        DiscordRPC.discordShutdown();
    }
}
