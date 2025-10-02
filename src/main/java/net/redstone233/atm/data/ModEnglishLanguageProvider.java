package net.redstone233.atm.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.redstone233.atm.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLanguageProvider extends FabricLanguageProvider {
    public ModEnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add(ModItems.BLAZING_FLAME_SWORD, "Blazing Flame Sword");
        translationBuilder.add(ModItems.ICE_FREEZE_SWORD, "Ice Freezing Sword");
        translationBuilder.add(ModItems.TEST_ITEM, "Test Item");

        translationBuilder.add("narration.scrollable_text","Scrollable Text：%s");
        translationBuilder.add("narration.scrollable_text.usage","Usage Scrollable Text");

        // 原有的翻译键
        translationBuilder.add("key.atm.announcement", "Displays the custom announcement.");
        translationBuilder.add("category.atm", "Custom Announcement.");
        translationBuilder.add("key.atm.use_ability", "Use Ability");
        translationBuilder.add("commands.atm.display.success", "Executed successfully! Screens have been shown to %s.");
        translationBuilder.add("commands.atm.display.failure", "Execution failed! No player targets found.");

        // 新增的翻译键 - 主标题设置相关
        translationBuilder.add("commands.atm.settings.maintitle.set.success", "Main title successfully set to: %s");
        translationBuilder.add("commands.atm.settings.maintitle.set.failure", "Failed to set main title: %s");
        translationBuilder.add("commands.atm.settings.maintitle.empty.status", "Main title is currently empty: %s");
        translationBuilder.add("commands.atm.settings.maintitle.empty.failure", "Failed to check if main title is empty: %s");
        translationBuilder.add("commands.atm.settings.maintitle.reset.success", "Main title successfully reset to default: %s");
        translationBuilder.add("commands.atm.settings.maintitle.reset.failure", "Failed to reset main title: %s");

        // 新增的翻译键 - 副标题设置相关
        translationBuilder.add("commands.atm.settings.subtitle.set.success", "Subtitle successfully set to: %s");
        translationBuilder.add("commands.atm.settings.subtitle.set.failure", "Failed to set subtitle: %s");
        translationBuilder.add("commands.atm.settings.subtitle.empty.status", "Subtitle is currently empty: %s");
        translationBuilder.add("commands.atm.settings.subtitle.empty.failure", "Failed to check if subtitle is empty: %s");
        translationBuilder.add("commands.atm.settings.subtitle.reset.success", "Subtitle successfully reset to default: %s");
        translationBuilder.add("commands.atm.settings.subtitle.reset.failure", "Failed to reset subtitle: %s");

        // 新增的翻译键 - 参数验证相关
        translationBuilder.add("commands.atm.settings.argument.true", "This argument must be set to 'true' to execute this command.");

        // 新增的翻译键 - 通用设置相关
        translationBuilder.add("commands.atm.settings.success", "Settings applied successfully.");
        translationBuilder.add("commands.atm.settings.failure", "Failed to apply settings.");

        translationBuilder.add("itemGroup.atm.mod_items", "ATM | Items");
        translationBuilder.add("tooltip.ability_sword.display1","Hold [");
        translationBuilder.add("key.use_ability.item","%s");
        translationBuilder.add("tooltip.ability_sword.display2"," ] for Ability to use");

        // GUI elements
        translationBuilder.add("title.announcement_mod.config", "Announcement Mod Configuration");
        translationBuilder.add("category.announcement_mod.general", "General Settings");
        translationBuilder.add("category.announcement_mod.display", "Display Settings");
        translationBuilder.add("category.announcement_mod.colors", "Color Settings");
        translationBuilder.add("category.announcement_mod.buttons", "Button Settings");
        translationBuilder.add("category.announcement_mod.icon", "Icon Settings");
        translationBuilder.add("category.announcement_mod.background", "Background Settings");
        translationBuilder.add("category.announcement_mod.content", "Content Settings");

        // Options
        translationBuilder.add("option.announcement_mod.showOnWorldEnter", "Show Announcement on World Enter");
        translationBuilder.add("option.announcement_mod.debugMode", "Debug Mode");
        translationBuilder.add("option.announcement_mod.mainTitle", "Main Title");
        translationBuilder.add("option.announcement_mod.subTitle", "Subtitle");
        translationBuilder.add("option.announcement_mod.scrollSpeed", "Scroll Speed");
        translationBuilder.add("option.announcement_mod.useCustomRGB", "Use Custom RGB Colors");
        translationBuilder.add("option.announcement_mod.mainTitleColor", "Main Title Color");
        translationBuilder.add("option.announcement_mod.subTitleColor", "Subtitle Color");
        translationBuilder.add("option.announcement_mod.contentColor", "Content Color");
        translationBuilder.add("option.announcement_mod.confirmButtonText", "Confirm Button Text");
        translationBuilder.add("option.announcement_mod.submitButtonText", "Submit Button Text");
        translationBuilder.add("option.announcement_mod.buttonLink", "Button Link");
        translationBuilder.add("option.announcement_mod.showIcon", "Show Icon");
        translationBuilder.add("option.announcement_mod.iconPath", "Icon Path");
        translationBuilder.add("option.announcement_mod.iconWidth", "Icon Width");
        translationBuilder.add("option.announcement_mod.iconHeight", "Icon Height");
        translationBuilder.add("option.announcement_mod.iconTextSpacing", "Icon Text Spacing");
        translationBuilder.add("option.announcement_mod.backgroundEnabled", "Enable Custom Background");
        translationBuilder.add("option.announcement_mod.backgroundPath", "Background Path");
        translationBuilder.add("option.announcement_mod.announcementContent", "Announcement Content");

        // Tooltips
        translationBuilder.add("tooltip.announcement_mod.showOnWorldEnter", "Whether to show announcement when player first enters the world");
        translationBuilder.add("tooltip.announcement_mod.debugMode", "Enable debug mode to show additional debug information");
        translationBuilder.add("tooltip.announcement_mod.mainTitle", "The main title text of the announcement screen");
        translationBuilder.add("tooltip.announcement_mod.subTitle", "The subtitle text of the announcement screen");
        translationBuilder.add("tooltip.announcement_mod.scrollSpeed", "The speed at which text scrolls");
        translationBuilder.add("tooltip.announcement_mod.useCustomRGB", "Enable to use custom RGB colors, otherwise use vanilla color codes");
        translationBuilder.add("tooltip.announcement_mod.mainTitleColor", "The color value for the main title");
        translationBuilder.add("tooltip.announcement_mod.subTitleColor", "The color value for the subtitle");
        translationBuilder.add("tooltip.announcement_mod.contentColor", "The color value for the announcement content");
        translationBuilder.add("tooltip.announcement_mod.confirmButtonText", "The text displayed on the confirm button");
        translationBuilder.add("tooltip.announcement_mod.submitButtonText", "The text displayed on the submit button");
        translationBuilder.add("tooltip.announcement_mod.buttonLink", "The link opened when the submit button is clicked");
        translationBuilder.add("tooltip.announcement_mod.showIcon", "Whether to show an icon next to the title");
        translationBuilder.add("tooltip.announcement_mod.iconPath", "The resource path for the icon");
        translationBuilder.add("tooltip.announcement_mod.iconWidth", "The width of the icon in pixels");
        translationBuilder.add("tooltip.announcement_mod.iconHeight", "The height of the icon in pixels");
        translationBuilder.add("tooltip.announcement_mod.iconTextSpacing", "The spacing between the icon and text");
        translationBuilder.add("tooltip.announcement_mod.backgroundEnabled", "Whether to use a custom background image");
        translationBuilder.add("tooltip.announcement_mod.backgroundPath", "The resource path for the background image");
        translationBuilder.add("tooltip.announcement_mod.announcementContent", "The specific content of the announcement, one line per entry");

        // GUI elements
        translationBuilder.add("gui.announcement_mod.announcement_screen", "Server Announcement");
        translationBuilder.add("gui.announcement_mod.confirm_button", "Confirm");
        translationBuilder.add("gui.announcement_mod.submit_button", "Submit Report");
        translationBuilder.add("gui.announcement_mod.scrollable_text", "Announcement Content");

        // Narration
        translationBuilder.add("narration.announcement_mod.announcement_screen", "Server announcement screen");
        translationBuilder.add("narration.announcement_mod.scrollable_text", "Scrollable announcement content: %s");
        translationBuilder.add("narration.announcement_mod.scrollable_text.usage", "Use mouse wheel or scrollbar to navigate");

        // Messages
        translationBuilder.add("message.announcement_mod.welcome", "Welcome to the server!");
        translationBuilder.add("message.announcement_mod.link_open_error", "Unable to open link: %s");
        translationBuilder.add("message.announcement_mod.config_updated", "Announcement configuration has been updated");
        translationBuilder.add("message.announcement_mod.already_seen", "You have already seen the current announcement");
        translationBuilder.add("message.announcement_mod.new_announcement", "New announcement available! Press %s to view");

        // Default content
        translationBuilder.add("text.announcement_mod.default_content.line1", "§aWelcome to our modded server!");
        translationBuilder.add("text.announcement_mod.default_content.line2", " ");
        translationBuilder.add("text.announcement_mod.default_content.line3", "§eSome reminders:");
        translationBuilder.add("text.announcement_mod.default_content.line4", "§f1. Mod is limited to 1.21.7~1.21.8 Fabric");
        translationBuilder.add("text.announcement_mod.default_content.line5", "§f2. Mod is currently in beta");
        translationBuilder.add("text.announcement_mod.default_content.line6", "§f3. Will continue to update");
        translationBuilder.add("text.announcement_mod.default_content.line7", " ");
        translationBuilder.add("text.announcement_mod.default_content.line8", "§bMod updates randomly");
        translationBuilder.add("text.announcement_mod.default_content.line9", "§cIf you find bugs, please report to the mod author or repository!");
    }
}