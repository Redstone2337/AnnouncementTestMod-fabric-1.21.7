package net.redstone233.atm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.redstone233.atm.command.AnnouncementCommand;
import net.redstone233.atm.component.ModComponentTypes;
import net.redstone233.atm.config.v1.ModConfig;
import net.redstone233.atm.event.ServerEventHandler;
import net.redstone233.atm.items.ModItemGroup;
import net.redstone233.atm.items.ModItems;
import net.redstone233.atm.materials.ModToolMaterials;
import net.redstone233.atm.network.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnouncementTestMod implements ModInitializer {
    public static final String MOD_ID = "atm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ServerEventHandler serverEventHandler;

    @Override
    public void onInitialize() {
        LOGGER.info("开始初始化公告模组内容...");
        long startTime = System.currentTimeMillis();

        // 初始化配置系统
        ModConfig.init();

        // Initialize server event handler
        serverEventHandler = new ServerEventHandler();

        // Register network messages
        ModMessages.registerC2SPackets();
        LOGGER.info("Network system registered");

        // Register event listeners
        registerEvents();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
//            commandDispatcher.register(AnnouncementCommand.register(commandRegistryAccess));
        });

        ModItems.init();
        ModComponentTypes.init();
        ModItemGroup.register();
        ModToolMaterials.init();

        LOGGER.info("Hello Fabric world!");
        LOGGER.info("模组内容初始化完成，总耗时 {}ms", System.currentTimeMillis() - startTime);
    }

    private void registerEvents() {
        // Player join server event
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            serverEventHandler.onPlayerJoined(handler.player);
        });

        // Server starting event
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            LOGGER.info("Server starting, Announcement Mod ready");
        });

        // Server stopping event
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            LOGGER.info("Server stopping");
            // 保存配置
            ModConfig.save();
        });
    }

    public static ServerEventHandler getServerEventHandler() {
        return serverEventHandler;
    }
}