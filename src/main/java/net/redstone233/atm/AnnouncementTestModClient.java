package net.redstone233.atm;

import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.redstone233.atm.component.ModComponentTypes;
import net.redstone233.atm.config.v1.ModConfig;
import net.redstone233.atm.config.v1.AnnouncementConfig;
import net.redstone233.atm.keys.ModKeys;
import net.redstone233.atm.screen.v1.AnnouncementScreen;
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

    // 使用新的配置系统
    public static ModConfig CONFIG;
    public static boolean DEBUG_MODE = false;
    public static boolean SHOW_ICON = true;

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
        LOGGER.info("开始初始化公告模组客户端...");
        long startTime = System.currentTimeMillis();

        // 初始化新的配置系统
        initializeConfig();

        initializeKeyBindings();

        registerClientEvents();

        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            if (minecraftClient.world != null && minecraftClient.player != null) {
                showAnnouncementIfNeeded(minecraftClient);
            }
        });

        ComponentTooltipAppenderRegistry.addBefore(DataComponentTypes.LORE, ModComponentTypes.BLAZING_FLAME_SWORD_COMPONENT);
        ComponentTooltipAppenderRegistry.addBefore(DataComponentTypes.LORE, ModComponentTypes.ICE_FREEZE_SWORD_COMPONENT);

        LOGGER.info("客户端初始化完成，总耗时 {}ms", System.currentTimeMillis() - startTime);
    }

    private void initializeConfig() {
        try {
            // 使用新的配置系统
            CONFIG = ModConfig.get();
            DEBUG_MODE = CONFIG.general.debugMode;
            SHOW_ICON = CONFIG.icon.showIcon;
            LOGGER.info("配置系统初始化完成");
        } catch (Exception e) {
            LOGGER.error("配置初始化失败", e);
            // 创建默认配置作为后备
            CONFIG = createDefaultConfig();
        }
    }

    public static void saveConfig() {
        try {
            ModConfig.save();
            LOGGER.info("配置已保存");
        } catch (Exception e) {
            LOGGER.error("保存配置失败", e);
        }
    }

    private void initializeKeyBindings() {
        ModKeys.register();

        ClientTickEvents.END_CLIENT_TICK.register(this::handleAnnouncementKey);
    }

    public static void setAnnouncementShown(boolean shown) {
        hasAnnouncementBeenShown = shown;
    }

    public static void resetAnnouncementShown() {
        hasAnnouncementBeenShown = false;
    }

    private void handleAnnouncementKey(MinecraftClient client) {
        while (ModKeys.isAnnouncementKeyPressed()) {
            if (client.player != null && client.currentScreen == null) {
                refreshConfig();
                // 使用新的配置创建 AnnouncementConfig
                AnnouncementConfig announcementConfig = ModConfig.createAnnouncementConfig();
                client.setScreen(new AnnouncementScreen(announcementConfig));
                break;
            }
        }
    }

    private void showAnnouncementIfNeeded(MinecraftClient client) {
        if (CONFIG == null) {
            initializeConfig();
        }

        if (!CONFIG.general.showOnWorldEnter || hasAnnouncementBeenShown) {
            AnnouncementTestMod.LOGGER.info("不显示公告: showOnWorldEnter={}, hasAnnouncementBeenShown={}",
                    CONFIG.general.showOnWorldEnter, hasAnnouncementBeenShown);
            return;
        }

        // 计算当前公告内容的哈希值
        String currentHash = calculateAnnouncementHash();
        AnnouncementTestMod.LOGGER.info("公告哈希比较: 当前={}, 上次={}", currentHash, CONFIG.content.lastDisplayedHash);

        // 如果公告内容已更改或从未显示过
        if (!currentHash.equals(CONFIG.content.lastDisplayedHash)) {
            client.execute(() -> {
                if (client.currentScreen == null) {
                    AnnouncementTestMod.LOGGER.info("显示公告屏幕");
                    // 使用新的配置创建 AnnouncementConfig
                    AnnouncementConfig announcementConfig = ModConfig.createAnnouncementConfig();
                    client.setScreen(new AnnouncementScreen(announcementConfig));
                    hasAnnouncementBeenShown = true;

                    // 更新配置中的哈希值
                    CONFIG.content.lastDisplayedHash = currentHash;
                    saveConfig();
                } else {
                    AnnouncementTestMod.LOGGER.info("已有其他屏幕打开，延迟显示公告");
                    // 延迟显示公告
                    client.execute(() -> {
                        if (client.currentScreen == null) {
                            AnnouncementConfig announcementConfig = ModConfig.createAnnouncementConfig();
                            client.setScreen(new AnnouncementScreen(announcementConfig));
                            hasAnnouncementBeenShown = true;
                            CONFIG.content.lastDisplayedHash = currentHash;
                            saveConfig();
                        }
                    });
                }
            });
        } else {
            AnnouncementTestMod.LOGGER.info("公告内容未更改，不显示");
        }
    }

    private void registerClientEvents() {
        // Client join world event
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            LOGGER.info("Client joined world");
        });

        // Client leave world event
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            LOGGER.info("Client left world");
            // 重置公告显示状态
            resetAnnouncementShown();
        });
    }

    private static ModConfig createDefaultConfig() {
        // 创建一个默认的配置实例
        ModConfig config = new ModConfig();

        // 设置默认值
        config.general.showOnWorldEnter = true;
        config.general.debugMode = false;

        config.display.mainTitle = "Server Announcement";
        config.display.subTitle = "Latest News";
        config.display.scrollSpeed = 1;

        config.colors.useCustomRGB = false;
        config.colors.mainTitleColor = 0xFFFFFF;
        config.colors.subTitleColor = 0xCCCCCC;
        config.colors.contentColor = 0xFFFFFF;

        config.buttons.confirmButtonText = "Confirm";
        config.buttons.submitButtonText = "Submit Report";
        config.buttons.buttonLink = "https://example.com";

        config.icon.showIcon = false;
        config.icon.path = "announcement_mod:textures/gui/icon.png";
        config.icon.width = 32;
        config.icon.height = 32;
        config.icon.textSpacing = 10;

        config.background.enabled = false;
        config.background.path = "announcement_mod:textures/gui/background.png";

        config.content.lines = java.util.Arrays.asList(
                "§aWelcome to our modded server!",
                " ",
                "§eSome reminders:",
                "§f1. Mod is limited to 1.21.7~1.21.8 Fabric",
                "§f2. Mod is currently in beta",
                "§f3. Will continue to update",
                " ",
                "§bMod updates randomly",
                "§cIf you find bugs, please report to the mod author or repository!"
        );
        config.content.lastDisplayedHash = "";

        return config;
    }

    public static void refreshConfig() {
        try {
            // 重新加载配置
            CONFIG = ModConfig.get();
            // 同步状态
            if (CONFIG != null) {
                DEBUG_MODE = CONFIG.general.debugMode;
                SHOW_ICON = CONFIG.icon.showIcon;
            }
            LOGGER.info("配置已刷新");
        } catch (Exception e) {
            LOGGER.error("刷新配置失败", e);
        }
    }

    public static boolean isConfigAvailable() {
        return CONFIG != null;
    }

    private String calculateAnnouncementHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder contentBuilder = new StringBuilder();

            if (CONFIG.content.lines != null) {
                for (String line : CONFIG.content.lines) {
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
}