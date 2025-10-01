package net.redstone233.atm.config;

import net.redstone233.atm.config.v1.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("ConfigInitializer");
    private static ConfigHolder<ModConfig> configHolder;
    private static boolean initialized = false;
    private static long initializationTime = 0;

    /**
     * 初始化配置系统
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        LOGGER.info("开始初始化配置系统...");
        long startTime = System.currentTimeMillis();

        try {
            // 注册配置 - 新的配置类在 net.redstone233.atm.config.v1 包中
            ModConfig.init(); // 调用新配置类的初始化方法

            // 获取配置持有者
            configHolder = AutoConfig.getConfigHolder(ModConfig.class);

            initialized = true;
            initializationTime = System.currentTimeMillis() - startTime;

            LOGGER.info("配置初始化成功，耗时 {}ms", initializationTime);

        } catch (Exception e) {
            LOGGER.error("配置初始化失败", e);
            initialized = true; // 仍然标记为已初始化
            initializationTime = System.currentTimeMillis() - startTime;
        }
    }

    /**
     * 获取配置持有者
     */
    public static ConfigHolder<ModConfig> getConfigHolder() {
        ensureInitialized();
        return configHolder;
    }

    /**
     * 刷新配置
     */
    public static void refreshConfig() {
        ensureInitialized();
        if (configHolder != null) {
            configHolder.load();
            LOGGER.debug("配置已刷新");
        }
    }

    /**
     * 保存配置
     */
    public static void saveConfig() {
        ensureInitialized();
        if (configHolder != null) {
            try {
                configHolder.save();
                LOGGER.debug("配置已保存");
            } catch (Exception e) {
                LOGGER.error("保存配置失败", e);
            }
        } else {
            LOGGER.warn("无法保存配置: config holder is null");
        }
    }

    /**
     * 检查配置是否可用
     */
    public static boolean isConfigAvailable() {
        return initialized && configHolder != null;
    }

    /**
     * 确保配置已初始化
     */
    private static void ensureInitialized() {
        if (!initialized) {
            LOGGER.warn("配置尚未初始化，正在延迟初始化...");
            initialize();
        }
    }

    /**
     * 获取初始化状态
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * 获取初始化耗时
     */
    public static long getInitializationTime() {
        return initializationTime;
    }

    /**
     * 获取配置实例
     */
    public static ModConfig getConfig() {
        ensureInitialized();
        if (configHolder != null) {
            return configHolder.getConfig();
        }
        return null;
    }
}