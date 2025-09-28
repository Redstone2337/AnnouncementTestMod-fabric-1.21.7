package net.redstone233.atm;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.redstone233.atm.data.ModEnglishLanguageProvider;
import net.redstone233.atm.data.ModModelsProvider;

public class AnnouncementTestModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModEnglishLanguageProvider::new);
        pack.addProvider(ModModelsProvider::new);
	}
}
