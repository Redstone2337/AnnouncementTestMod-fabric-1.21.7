package net.redstone233.atm.config.v1;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.redstone233.atm.AnnouncementTestMod;

import java.util.Arrays;
import java.util.List;

@Config(name = AnnouncementTestMod.MOD_ID)
public class ModConfig implements ConfigData {

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    @ConfigEntry.Category("display")
    @ConfigEntry.Gui.TransitiveObject
    public Display display = new Display();

    @ConfigEntry.Category("colors")
    @ConfigEntry.Gui.TransitiveObject
    public Colors colors = new Colors();

    @ConfigEntry.Category("buttons")
    @ConfigEntry.Gui.TransitiveObject
    public Buttons buttons = new Buttons();

    @ConfigEntry.Category("icon")
    @ConfigEntry.Gui.TransitiveObject
    public Icon icon = new Icon();

    @ConfigEntry.Category("background")
    @ConfigEntry.Gui.TransitiveObject
    public Background background = new Background();

    @ConfigEntry.Category("content")
    @ConfigEntry.Gui.TransitiveObject
    public Content content = new Content();

    public static class General {
        @ConfigEntry.Gui.Tooltip
        public boolean showOnWorldEnter = true;

        @ConfigEntry.Gui.Tooltip
        public boolean debugMode = false;
    }

    public static class Display {
        @ConfigEntry.Gui.Tooltip
        public String mainTitle = "Server Announcement";

        @ConfigEntry.Gui.Tooltip
        public String subTitle = "Latest News";

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
        public int scrollSpeed = 1;
    }

    public static class Colors {
        @ConfigEntry.Gui.Tooltip
        public boolean useCustomRGB = false;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.ColorPicker
        public int mainTitleColor = 0xFFFFFF;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.ColorPicker
        public int subTitleColor = 0xCCCCCC;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.ColorPicker
        public int contentColor = 0xFFFFFF;
    }

    public static class Buttons {
        @ConfigEntry.Gui.Tooltip
        public String confirmButtonText = "Confirm";

        @ConfigEntry.Gui.Tooltip
        public String submitButtonText = "Submit Report";

        @ConfigEntry.Gui.Tooltip
        public String buttonLink = "https://example.com";
    }

    public static class Icon {
        @ConfigEntry.Gui.Tooltip
        public boolean showIcon = false;

        @ConfigEntry.Gui.Tooltip
        public String path = "announcement_mod:textures/gui/icon.png";

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 16, max = 128)
        public int width = 32;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 16, max = 128)
        public int height = 32;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 50)
        public int textSpacing = 10;
    }

    public static class Background {
        @ConfigEntry.Gui.Tooltip
        public boolean enabled = false;

        @ConfigEntry.Gui.Tooltip
        public String path = "announcement_mod:textures/gui/background.png";
    }

    public static class Content {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.CollapsibleObject
        public List<String> lines = Arrays.asList(
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

        public String lastDisplayedHash = "";
    }

    public static void init() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
    }

    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public static void save() {
        AutoConfig.getConfigHolder(ModConfig.class).save();
    }

    public static Screen getScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.announcement_mod.config"));

        builder.setSavingRunnable(ModConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.general"));
        ConfigCategory display = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.display"));
        ConfigCategory colors = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.colors"));
        ConfigCategory buttons = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.buttons"));
        ConfigCategory icon = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.icon"));
        ConfigCategory background = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.background"));
        ConfigCategory content = builder.getOrCreateCategory(Text.translatable("category.announcement_mod.content"));

        // General category
        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.announcement_mod.showOnWorldEnter"), get().general.showOnWorldEnter)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("tooltip.announcement_mod.showOnWorldEnter"))
                .setSaveConsumer(newValue -> get().general.showOnWorldEnter = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.announcement_mod.debugMode"), get().general.debugMode)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("tooltip.announcement_mod.debugMode"))
                .setSaveConsumer(newValue -> get().general.debugMode = newValue)
                .build());

        // Display category
        display.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.mainTitle"), get().display.mainTitle)
                .setDefaultValue("Server Announcement")
                .setTooltip(Text.translatable("tooltip.announcement_mod.mainTitle"))
                .setSaveConsumer(newValue -> get().display.mainTitle = newValue)
                .build());

        display.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.subTitle"), get().display.subTitle)
                .setDefaultValue("Latest News")
                .setTooltip(Text.translatable("tooltip.announcement_mod.subTitle"))
                .setSaveConsumer(newValue -> get().display.subTitle = newValue)
                .build());

        display.addEntry(entryBuilder.startIntSlider(Text.translatable("option.announcement_mod.scrollSpeed"), get().display.scrollSpeed, 1, 10)
                .setDefaultValue(1)
                .setTooltip(Text.translatable("tooltip.announcement_mod.scrollSpeed"))
                .setSaveConsumer(newValue -> get().display.scrollSpeed = newValue)
                .build());

        // Colors category
        colors.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.announcement_mod.useCustomRGB"), get().colors.useCustomRGB)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("tooltip.announcement_mod.useCustomRGB"))
                .setSaveConsumer(newValue -> get().colors.useCustomRGB = newValue)
                .build());

        colors.addEntry(entryBuilder.startColorField(Text.translatable("option.announcement_mod.mainTitleColor"), get().colors.mainTitleColor)
                .setDefaultValue(0xFFFFFF)
                .setTooltip(Text.translatable("tooltip.announcement_mod.mainTitleColor"))
                .setSaveConsumer(newValue -> get().colors.mainTitleColor = newValue)
                .build());

        colors.addEntry(entryBuilder.startColorField(Text.translatable("option.announcement_mod.subTitleColor"), get().colors.subTitleColor)
                .setDefaultValue(0xCCCCCC)
                .setTooltip(Text.translatable("tooltip.announcement_mod.subTitleColor"))
                .setSaveConsumer(newValue -> get().colors.subTitleColor = newValue)
                .build());

        colors.addEntry(entryBuilder.startColorField(Text.translatable("option.announcement_mod.contentColor"), get().colors.contentColor)
                .setDefaultValue(0xFFFFFF)
                .setTooltip(Text.translatable("tooltip.announcement_mod.contentColor"))
                .setSaveConsumer(newValue -> get().colors.contentColor = newValue)
                .build());

        // Buttons category
        buttons.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.confirmButtonText"), get().buttons.confirmButtonText)
                .setDefaultValue("Confirm")
                .setTooltip(Text.translatable("tooltip.announcement_mod.confirmButtonText"))
                .setSaveConsumer(newValue -> get().buttons.confirmButtonText = newValue)
                .build());

        buttons.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.submitButtonText"), get().buttons.submitButtonText)
                .setDefaultValue("Submit Report")
                .setTooltip(Text.translatable("tooltip.announcement_mod.submitButtonText"))
                .setSaveConsumer(newValue -> get().buttons.submitButtonText = newValue)
                .build());

        buttons.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.buttonLink"), get().buttons.buttonLink)
                .setDefaultValue("https://example.com")
                .setTooltip(Text.translatable("tooltip.announcement_mod.buttonLink"))
                .setSaveConsumer(newValue -> get().buttons.buttonLink = newValue)
                .build());

        // Icon category
        icon.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.announcement_mod.showIcon"), get().icon.showIcon)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("tooltip.announcement_mod.showIcon"))
                .setSaveConsumer(newValue -> get().icon.showIcon = newValue)
                .build());

        icon.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.iconPath"), get().icon.path)
                .setDefaultValue("announcement_mod:textures/gui/icon.png")
                .setTooltip(Text.translatable("tooltip.announcement_mod.iconPath"))
                .setSaveConsumer(newValue -> get().icon.path = newValue)
                .build());

        icon.addEntry(entryBuilder.startIntSlider(Text.translatable("option.announcement_mod.iconWidth"), get().icon.width, 16, 128)
                .setDefaultValue(32)
                .setTooltip(Text.translatable("tooltip.announcement_mod.iconWidth"))
                .setSaveConsumer(newValue -> get().icon.width = newValue)
                .build());

        icon.addEntry(entryBuilder.startIntSlider(Text.translatable("option.announcement_mod.iconHeight"), get().icon.height, 16, 128)
                .setDefaultValue(32)
                .setTooltip(Text.translatable("tooltip.announcement_mod.iconHeight"))
                .setSaveConsumer(newValue -> get().icon.height = newValue)
                .build());

        icon.addEntry(entryBuilder.startIntSlider(Text.translatable("option.announcement_mod.iconTextSpacing"), get().icon.textSpacing, 0, 50)
                .setDefaultValue(10)
                .setTooltip(Text.translatable("tooltip.announcement_mod.iconTextSpacing"))
                .setSaveConsumer(newValue -> get().icon.textSpacing = newValue)
                .build());

        // Background category
        background.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.announcement_mod.backgroundEnabled"), get().background.enabled)
                .setDefaultValue(false)
                .setTooltip(Text.translatable("tooltip.announcement_mod.backgroundEnabled"))
                .setSaveConsumer(newValue -> get().background.enabled = newValue)
                .build());

        background.addEntry(entryBuilder.startStrField(Text.translatable("option.announcement_mod.backgroundPath"), get().background.path)
                .setDefaultValue("announcement_mod:textures/gui/background.png")
                .setTooltip(Text.translatable("tooltip.announcement_mod.backgroundPath"))
                .setSaveConsumer(newValue -> get().background.path = newValue)
                .build());

        // Content category
        content.addEntry(entryBuilder.startStrList(Text.translatable("option.announcement_mod.announcementContent"), get().content.lines)
                .setDefaultValue(Arrays.asList(
                        "§aWelcome to our modded server!",
                        " ",
                        "§eSome reminders:",
                        "§f1. Mod is limited to 1.21.7~1.21.8 Fabric",
                        "§f2. Mod is currently in beta",
                        "§f3. Will continue to update",
                        " ",
                        "§bMod updates randomly",
                        "§cIf you find bugs, please report to the mod author or repository!"
                ))
                .setTooltip(Text.translatable("tooltip.announcement_mod.announcementContent"))
                .setSaveConsumer(newValue -> get().content.lines = newValue)
                .build());

        return builder.build();
    }

    public static AnnouncementConfig createAnnouncementConfig() {
        ModConfig config = get();
        AnnouncementConfig announcementConfig = new AnnouncementConfig();

        announcementConfig.mainTitle = config.display.mainTitle;
        announcementConfig.subTitle = config.display.subTitle;
        announcementConfig.announcementContent = config.content.lines;
        announcementConfig.confirmButtonText = config.buttons.confirmButtonText;
        announcementConfig.submitButtonText = config.buttons.submitButtonText;
        announcementConfig.buttonLink = config.buttons.buttonLink;
        announcementConfig.showIcon = config.icon.showIcon;
        announcementConfig.iconPath = config.icon.path;
        announcementConfig.iconWidth = config.icon.width;
        announcementConfig.iconHeight = config.icon.height;
        announcementConfig.iconTextSpacing = config.icon.textSpacing;
        announcementConfig.useCustomRGB = config.colors.useCustomRGB;
        announcementConfig.mainTitleColor = config.colors.mainTitleColor;
        announcementConfig.subTitleColor = config.colors.subTitleColor;
        announcementConfig.contentColor = config.colors.contentColor;
        announcementConfig.scrollSpeed = config.display.scrollSpeed;
        announcementConfig.useCustomAnnouncementBackground = config.background.enabled;
        announcementConfig.announcementBackgroundPath = config.background.path;

        return announcementConfig;
    }
}