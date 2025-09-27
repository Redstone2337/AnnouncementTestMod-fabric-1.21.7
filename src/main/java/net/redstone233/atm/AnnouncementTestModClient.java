package net.redstone233.atm;

import net.redstone233.atm.config.ConfigInitializer;
import net.redstone233.atm.config.ModConfig;
import net.redstone233.atm.keys.ModKeys;
import net.redstone233.atm.screen.AnnouncementScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AnnouncementTestModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementTestMod.MOD_ID);
    public static ModConfig CONFIG;
    public static boolean DEBUG_MODE = false;
    public static boolean SHOW_ICON = true; // 新增：图标显示状态
    // 添加静态变量来跟踪是否已显示公告
    private static boolean hasAnnouncementBeenShown = false;

    public static boolean isHasAnnouncementBeenShown() {
        return hasAnnouncementBeenShown;
    }

    public static void setHasAnnouncementBeenShown(boolean hasAnnouncementBeenShown) {
        AnnouncementTestModClient.hasAnnouncementBeenShown = hasAnnouncementBeenShown;
    }

    @Override
    public void onInitializeClient() {

        LOGGER.info("开始初始化测试模组客户端...");
        long startTime = System.currentTimeMillis();


        ConfigInitializer.initialize();

        initializeKeyBindings();

        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            if (minecraftClient.world != null && minecraftClient.player != null) {
                showAnnouncementIfNeeded(minecraftClient);
            }
        });

        LOGGER.info("客户端初始化完成，总耗时 {}ms", System.currentTimeMillis() - startTime);

    }



    public static void saveConfig() {
        ConfigInitializer.saveConfig();
    }

    private void initializeKeyBindings() {
        ModKeys.register();

        ClientTickEvents.END_CLIENT_TICK.register(this::handleAnnouncementKey);
    }

    // TestModClient.java
    public static void setAnnouncementShown(boolean shown) {
        hasAnnouncementBeenShown = shown;
    }

    public static void resetAnnouncementShown() {
        hasAnnouncementBeenShown = false;
    }


    private void handleAnnouncementKey(MinecraftClient client) {
        while (ModKeys.isAnnouncementKeyPressed()) {
            if (client.player != null && client.currentScreen == null) {
                ConfigInitializer.refreshConfig();
                client.setScreen(new AnnouncementScreen());
                break;
            }
        }
    }

    private void showAnnouncementIfNeeded(MinecraftClient client) {
        // 确保配置已加载
        if (!ConfigInitializer.isInitialized()) {
            ConfigInitializer.initialize();
        }

        if (!CONFIG.showOnWorldEnter || hasAnnouncementBeenShown) {
            AnnouncementTestMod.LOGGER.info("不显示公告: showOnWorldEnter={}, hasAnnouncementBeenShown={}",
                    CONFIG.showOnWorldEnter, hasAnnouncementBeenShown);
            return;
        }

        // 计算当前公告内容的哈希值
        String currentHash = calculateAnnouncementHash();
        AnnouncementTestMod.LOGGER.info("公告哈希比较: 当前={}, 上次={}", currentHash, CONFIG.lastDisplayedHash);

        // 如果公告内容已更改或从未显示过
        if (!currentHash.equals(CONFIG.lastDisplayedHash)) {
            client.execute(() -> {
                if (client.currentScreen == null) {
                    AnnouncementTestMod.LOGGER.info("显示公告屏幕");
                    client.setScreen(new AnnouncementScreen());
                    hasAnnouncementBeenShown = true;

                    // 更新配置中的哈希值
                    CONFIG.lastDisplayedHash = currentHash;
                    saveConfig();
                } else {
                    AnnouncementTestMod.LOGGER.info("已有其他屏幕打开，延迟显示公告");
                    // 延迟显示公告
                    client.execute(() -> {
                        if (client.currentScreen == null) {
                            client.setScreen(new AnnouncementScreen());
                            hasAnnouncementBeenShown = true;
                            CONFIG.lastDisplayedHash = currentHash;
                            saveConfig();
                        }
                    });
                }
            });
        } else {
            AnnouncementTestMod.LOGGER.info("公告内容未更改，不显示");
        }
    }
    
    private String calculateAnnouncementHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder contentBuilder = new StringBuilder();

            if (CONFIG.announcementContent != null) {
                for (String line : CONFIG.announcementContent) {
                    contentBuilder.append(line);
                }
            }

            byte[] hash = digest.digest(contentBuilder.toString().getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("无法计算公告哈希值", e);
            return "";
        }
    }

    public static void refreshConfig() {
        ConfigInitializer.refreshConfig();
        // 新增：刷新配置后同步状态
        if (CONFIG != null) {
            DEBUG_MODE = CONFIG.debugMode;
            SHOW_ICON = CONFIG.showIcon;
        }
    }

    public static boolean isConfigAvailable() {
        return ConfigInitializer.isConfigAvailable();
    }

}
