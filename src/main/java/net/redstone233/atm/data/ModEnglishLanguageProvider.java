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
    }
}