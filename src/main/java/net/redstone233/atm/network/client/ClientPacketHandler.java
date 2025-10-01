package net.redstone233.atm.network.client;


import net.redstone233.atm.config.v1.AnnouncementConfig;
import net.redstone233.atm.screen.v1.AnnouncementScreen;

public class ClientPacketHandler {

    public static void handleShowAnnouncement(AnnouncementConfig config) {
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();

        if (client.world == null) {
            return;
        }

        client.execute(() -> {
            if (client.currentScreen == null) {
                client.setScreen(new AnnouncementScreen(config));
            } else {
                client.currentScreen.close();
                client.setScreen(new AnnouncementScreen(config));
            }
        });
    }
}
