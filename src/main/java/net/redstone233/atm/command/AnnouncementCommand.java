package net.redstone233.atm.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.redstone233.atm.config.ConfigInitializer;
import net.redstone233.atm.config.ModConfig;
import net.redstone233.atm.screen.AnnouncementScreen;

public class AnnouncementCommand {
    private static ModConfig config;


    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandRegistryAccess registryAccess) {
        return CommandManager.literal("atm")
                .requires(src -> src.hasPermissionLevel(1))
                .then(CommandManager.literal("display")
                        .then(CommandManager.argument("isDisplay", BoolArgumentType.bool())
                                .executes(run -> execute(
                                        run.getSource(),
                                        BoolArgumentType.getBool(run,"isDisplay")
                                        )
                                )
                        )
                )
                .then(CommandManager.literal("settings")
                        .then(CommandManager.literal("mainTitle")
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("messages", TextArgumentType.text(registryAccess))
                                                .executes(run -> execute(
                                                        run.getSource(),
                                                        TextArgumentType.getTextArgument(run, "messages")
                                                ))
                                        .then(CommandManager.literal("empty")
                                                .then(CommandManager.argument("isEmpty", BoolArgumentType.bool())
                                                        .executes(run -> executeEmpty(
                                                                run.getSource(),
                                                                BoolArgumentType.getBool(run, "isEmpty")
                                                        ))
                                                )
                                        )
                                )
                                .then(CommandManager.literal("reset")
                                        .then(CommandManager.argument("refurnish",BoolArgumentType.bool())
                                                .executes(run -> executeReset(
                                                        run.getSource(),
                                                        BoolArgumentType.getBool(run, "refurnish")
                                                ))
                                        )
                                )
                        )
                )
                        .then(CommandManager.literal("subTitle")
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("messages", TextArgumentType.text(registryAccess))
                                                .executes(run -> execute1(
                                                        run.getSource(),
                                                        TextArgumentType.getTextArgument(run, "messages")
                                                ))
                                                .then(CommandManager.literal("empty")
                                                        .then(CommandManager.argument("isEmpty", BoolArgumentType.bool())
                                                                .executes(run -> executeEmpty1(
                                                                        run.getSource(),
                                                                        BoolArgumentType.getBool(run, "isEmpty")
                                                                ))
                                                        )
                                                )
                                        )
                                        .then(CommandManager.literal("reset")
                                                .then(CommandManager.argument("refurnish",BoolArgumentType.bool())
                                                        .executes(run -> executeReset1(
                                                                run.getSource(),
                                                                BoolArgumentType.getBool(run, "refurnish")
                                                        ))
                                                )
                                        )
                                )
                        )

                );
    }


    private static int execute(ServerCommandSource source, boolean isDisplay) {
        PlayerEntity player = source.getPlayer();
        if (source instanceof ServerCommandSource && isDisplay) {
            if (player != null) {
                MinecraftClient.getInstance().setScreen(new AnnouncementScreen());
            }
            source.sendFeedback(() -> Text.translatable("commands.atm.display.success", source.getPlayer().getName()),true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.display.failure"));
            return 0;
        }
    }


    private static int execute(ServerCommandSource source, Text message) {
        if (source instanceof ServerCommandSource && message != null) {
            config.mainTitle = message.toString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
            return 0;
        }
    }

    private static int executeEmpty(ServerCommandSource source, boolean isEmpty) {
        if (source instanceof ServerCommandSource && isEmpty) {
            config.mainTitle = Text.empty().toString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
           return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
           return 0;
        }
    }

    private static int executeReset(ServerCommandSource source, boolean refurnish) {
        if (source instanceof ServerCommandSource && refurnish) {
            ConfigInitializer.refreshConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
            return 0;
        }
    }

    /**
     * 副标题部分
     */


    private static int execute1(ServerCommandSource source, Text message) {
        if (source instanceof ServerCommandSource && message != null) {
            config.subTitle = message.toString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
            return 0;
        }
    }

    private static int executeEmpty1(ServerCommandSource source, boolean isEmpty) {
        if (source instanceof ServerCommandSource && isEmpty) {
            config.subTitle = Text.empty().toString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
            return 0;
        }
    }

    private static int executeReset1(ServerCommandSource source, boolean refurnish) {
        if (source instanceof ServerCommandSource && refurnish) {
            ConfigInitializer.refreshConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.success"), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.settings.failure"));
            return 0;
        }
    }
}
