package net.redstone233.atm;

import com.nimbusds.jose.util.cache.CachedObject;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.redstone233.atm.command.AnnouncementCommand;
import net.redstone233.atm.component.ModComponentTypes;
import net.redstone233.atm.items.ModItemGroup;
import net.redstone233.atm.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnouncementTestMod implements ModInitializer {
	public static final String MOD_ID = "atm";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        LOGGER.info("开始初始化公告模组内容...");
        long startTime = System.currentTimeMillis();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(AnnouncementCommand.register(commandRegistryAccess));
        });

        ModItems.init();
        ModComponentTypes.init();
        ModItemGroup.register();


		LOGGER.info("Hello Fabric world!");

        LOGGER.info("模组内容初始化完成，总耗时 {}ms", System.currentTimeMillis() - startTime);
	}
}