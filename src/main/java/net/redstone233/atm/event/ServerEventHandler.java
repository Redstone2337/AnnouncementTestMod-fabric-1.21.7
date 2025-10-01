package net.redstone233.atm.event;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.redstone233.atm.AnnouncementTestMod;
import net.redstone233.atm.config.v1.ModConfig;
import net.redstone233.atm.network.ModMessages;

import java.util.Optional;

public class ServerEventHandler {
    private static final String SEEN_ANNOUNCEMENT_KEY = "announcement_mod:has_seen_announcement";

    public void onPlayerJoined(ServerPlayerEntity player) {
        if (!ModConfig.get().general.showOnWorldEnter) {
            return;
        }

        // Check if player has already seen the announcement
        if (!hasPlayerSeenAnnouncement(player).orElse(false)) {
            // Send announcement packet
            ModMessages.sendShowAnnouncementPacket(player);

            // Mark player as having seen the announcement
            markPlayerSeenAnnouncement(player);

            AnnouncementTestMod.LOGGER.info("Sent announcement to player {}", player.getName().getString());
        }
    }

    private Optional<Boolean> hasPlayerSeenAnnouncement(ServerPlayerEntity player) {
        // 使用数据组件获取自定义数据
        NbtComponent customData = player.get(DataComponentTypes.CUSTOM_DATA);
        if (customData != null && customData.contains(SEEN_ANNOUNCEMENT_KEY)) {
            NbtCompound nbt = customData.copyNbt();
            return nbt.getBoolean(SEEN_ANNOUNCEMENT_KEY);
        }
        return Optional.of(false);
    }

    private void markPlayerSeenAnnouncement(ServerPlayerEntity player) {
        // 获取现有的自定义数据或创建新的
        NbtComponent currentData = player.get(DataComponentTypes.CUSTOM_DATA);
        NbtCompound nbt = currentData != null ? currentData.copyNbt() : new NbtCompound();

        // 设置标志
        nbt.putBoolean(SEEN_ANNOUNCEMENT_KEY, true);

        // 更新组件
        player.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    // Method to reset player announcement status (for testing or admin commands)
    public static void resetPlayerAnnouncementStatus(ServerPlayerEntity player) {
        NbtComponent currentData = player.get(DataComponentTypes.CUSTOM_DATA);
        if (currentData != null && currentData.contains(SEEN_ANNOUNCEMENT_KEY)) {
            NbtCompound nbt = currentData.copyNbt();
            nbt.putBoolean(SEEN_ANNOUNCEMENT_KEY, false);
            player.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        }
    }
}