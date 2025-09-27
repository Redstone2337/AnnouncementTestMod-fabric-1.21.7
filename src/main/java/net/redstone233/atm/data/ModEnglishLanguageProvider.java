package net.redstone233.atm.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLanguageProvider extends FabricLanguageProvider {
    public ModEnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("key.atm.announcement","Displays the custom announcement.");
        translationBuilder.add("category.atm","Custom Announcement.");
        translationBuilder.add("commands.atm.display.success","Executed successfully! Screens have been shown to %s.");
        translationBuilder.add("commands.atm.display.failure", "Execution failed! No player targets found.");
    }
}
