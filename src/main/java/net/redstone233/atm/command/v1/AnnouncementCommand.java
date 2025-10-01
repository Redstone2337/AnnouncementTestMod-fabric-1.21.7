package net.redstone233.atm.command.v1;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.redstone233.atm.AnnouncementTestMod;
import net.redstone233.atm.config.ConfigInitializer;
import net.redstone233.atm.config.ModConfig;
import net.redstone233.atm.screen.AnnouncementScreen;

public class AnnouncementCommand {
    private static ModConfig config;

    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandRegistryAccess registryAccess) {
        return CommandManager.literal("atm-v1")
                .requires(src -> src.hasPermissionLevel(1))
                .then(CommandManager.literal("display")
                        .then(CommandManager.argument("isDisplay", BoolArgumentType.bool())
                                .executes(run -> executeDisplay(
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
                                                .executes(run -> executeSetMainTitle(
                                                        run.getSource(),
                                                        TextArgumentType.getTextArgument(run, "messages")
                                                ))
                                        )
                                        .then(CommandManager.literal("empty")
                                                .then(CommandManager.argument("isEmpty", BoolArgumentType.bool())
                                                        .executes(run -> executeEmptyMainTitle(
                                                                run.getSource(),
                                                                BoolArgumentType.getBool(run, "isEmpty")
                                                        ))
                                                )
                                        )
                                )
                                .then(CommandManager.literal("reset")
                                        .then(CommandManager.argument("refurnish",BoolArgumentType.bool())
                                                .executes(run -> executeResetMainTitle(
                                                        run.getSource(),
                                                        BoolArgumentType.getBool(run, "refurnish")
                                                ))
                                        )
                                )
                        )
                        .then(CommandManager.literal("subTitle")
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.argument("messages", TextArgumentType.text(registryAccess))
                                                .executes(run -> executeSetSubTitle(
                                                        run.getSource(),
                                                        TextArgumentType.getTextArgument(run, "messages")
                                                ))
                                        )
                                        .then(CommandManager.literal("empty")
                                                .then(CommandManager.argument("isEmpty", BoolArgumentType.bool())
                                                        .executes(run -> executeEmptySubTitle(
                                                                run.getSource(),
                                                                BoolArgumentType.getBool(run, "isEmpty")
                                                        ))
                                                )
                                        )
                                )
                                .then(CommandManager.literal("reset")
                                        .then(CommandManager.argument("refurnish",BoolArgumentType.bool())
                                                .executes(run -> executeResetSubTitle(
                                                        run.getSource(),
                                                        BoolArgumentType.getBool(run, "refurnish")
                                                ))
                                        )
                                )
                        )
                );
    }

    private static int executeDisplay(ServerCommandSource source, boolean isDisplay) {
        if (isDisplay) {
            PlayerEntity player = source.getPlayer();
            if (player != null) {
                MinecraftClient.getInstance().setScreen(new AnnouncementScreen());
            }
            source.sendFeedback(() -> Text.translatable("commands.atm.display.success", source.getPlayer().getName()), true);
            return 1;
        } else {
            source.sendError(Text.translatable("commands.atm.display.failure"));
            return 0;
        }
    }

    private static int executeSetMainTitle(ServerCommandSource source, Text message) {
        try {
            config.mainTitle = message.getString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.maintitle.set.success", message.getString()), true);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.translatable("commands.atm.settings.maintitle.set.failure", e.getMessage()));
            return 0;
        }
    }

    private static int executeEmptyMainTitle(ServerCommandSource source, boolean isEmpty) {
        if (isEmpty) {
            try {
                boolean isCurrentlyEmpty = config.mainTitle == null || config.mainTitle.isEmpty();
                source.sendFeedback(() -> Text.translatable("commands.atm.settings.maintitle.empty.status", isCurrentlyEmpty), true);
                return 1;
            } catch (Exception e) {
                source.sendError(Text.translatable("commands.atm.settings.maintitle.empty.failure", e.getMessage()));
                return 0;
            }
        } else {
            source.sendError(Text.translatable("commands.atm.settings.argument.true"));
            return 0;
        }
    }

    private static int executeResetMainTitle(ServerCommandSource source, boolean refurnish) {
        if (refurnish) {
            try {
                ConfigInitializer.refreshConfig();
                String defaultTitle = config.mainTitle;
                source.sendFeedback(() -> Text.translatable("commands.atm.settings.maintitle.reset.success", defaultTitle), true);
                return 1;
            } catch (Exception e) {
                source.sendError(Text.translatable("commands.atm.settings.maintitle.reset.failure", e.getMessage()));
                return 0;
            }
        } else {
            source.sendError(Text.translatable("commands.atm.settings.argument.true"));
            return 0;
        }
    }

    private static int executeSetSubTitle(ServerCommandSource source, Text message) {
        try {
            config.subTitle = message.getString();
            ConfigInitializer.saveConfig();
            source.sendFeedback(() -> Text.translatable("commands.atm.settings.subtitle.set.success", message.getString()), true);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.translatable("commands.atm.settings.subtitle.set.failure", e.getMessage()));
            return 0;
        }
    }

    private static int executeEmptySubTitle(ServerCommandSource source, boolean isEmpty) {
        if (isEmpty) {
            try {
                boolean isCurrentlyEmpty = config.subTitle == null || config.subTitle.isEmpty();
                source.sendFeedback(() -> Text.translatable("commands.atm.settings.subtitle.empty.status", isCurrentlyEmpty), true);
                return 1;
            } catch (Exception e) {
                source.sendError(Text.translatable("commands.atm.settings.subtitle.empty.failure", e.getMessage()));
                return 0;
            }
        } else {
            source.sendError(Text.translatable("commands.atm.settings.argument.true"));
            return 0;
        }
    }

    private static int executeResetSubTitle(ServerCommandSource source, boolean refurnish) {
        if (refurnish) {
            try {
                ConfigInitializer.refreshConfig();
                String defaultSubTitle = config.subTitle;
                source.sendFeedback(() -> Text.translatable("commands.atm.settings.subtitle.reset.success", defaultSubTitle), true);
                return 1;
            } catch (Exception e) {
                source.sendError(Text.translatable("commands.atm.settings.subtitle.reset.failure", e.getMessage()));
                return 0;
            }
        } else {
            source.sendError(Text.translatable("commands.atm.settings.argument.true"));
            return 0;
        }
    }

    public static void init() {
        AnnouncementTestMod.LOGGER.info("自定义命令注册成功！");
    }
}