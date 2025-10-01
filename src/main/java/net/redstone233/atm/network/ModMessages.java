package net.redstone233.atm.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.redstone233.atm.AnnouncementTestMod;
import net.redstone233.atm.config.v1.AnnouncementConfig;
import net.redstone233.atm.config.v1.ModConfig;
import net.redstone233.atm.network.client.ClientPacketHandler;

import java.util.ArrayList;
import java.util.List;

public class ModMessages {
    public static final CustomPayload.Id<ShowAnnouncementPayload> SHOW_ANNOUNCEMENT_PACKET_ID =
            new CustomPayload.Id<>(Identifier.of(AnnouncementTestMod.MOD_ID, "show_announcement"));

    public static void registerC2SPackets() {
        // 注册客户端到服务器的payload（如果有的话）
    }

    public static void registerS2CPackets() {
        // 注册服务器到客户端的payload
        PayloadTypeRegistry.playS2C().register(SHOW_ANNOUNCEMENT_PACKET_ID, ShowAnnouncementPayload.PACKET_CODEC);

        // 注册客户端接收器
        ClientPlayNetworking.registerGlobalReceiver(SHOW_ANNOUNCEMENT_PACKET_ID, (payload, context) -> {
            context.client().execute(() -> {
                ClientPacketHandler.handleShowAnnouncement(payload.config());
            });
        });
    }

    public static void sendShowAnnouncementPacket(ServerPlayerEntity player) {
        AnnouncementConfig config = ModConfig.createAnnouncementConfig();
        ShowAnnouncementPayload payload = new ShowAnnouncementPayload(config);
        ServerPlayNetworking.send(player, payload);
    }

    // 定义Payload记录
    public record ShowAnnouncementPayload(AnnouncementConfig config) implements CustomPayload {
        public static final CustomPayload.Id<ShowAnnouncementPayload> ID = ModMessages.SHOW_ANNOUNCEMENT_PACKET_ID;

        public static final PacketCodec<RegistryByteBuf, ShowAnnouncementPayload> PACKET_CODEC =
                PacketCodec.of(ShowAnnouncementPayload::write, ShowAnnouncementPayload::new);

        public ShowAnnouncementPayload(RegistryByteBuf buf) {
            this(readConfigFromBuffer(buf));
        }

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }

        public void write(RegistryByteBuf buf) {
            writeConfigToBuffer(config, buf);
        }

        private static void writeConfigToBuffer(AnnouncementConfig config, RegistryByteBuf buf) {
            // Write string fields
            buf.writeString(config.mainTitle != null ? config.mainTitle : "");
            buf.writeString(config.subTitle != null ? config.subTitle : "");

            // Write announcement content
            if (config.announcementContent != null) {
                buf.writeVarInt(config.announcementContent.size());
                for (String line : config.announcementContent) {
                    buf.writeString(line != null ? line : "");
                }
            } else {
                buf.writeVarInt(0);
            }

            // Write boolean values and colors
            buf.writeBoolean(config.useCustomRGB);
            buf.writeInt(config.mainTitleColor);
            buf.writeInt(config.subTitleColor);
            buf.writeInt(config.contentColor);
            buf.writeDouble(config.scrollSpeed);

            // Write icon-related configuration
            buf.writeBoolean(config.showIcon);
            buf.writeString(config.iconPath != null ? config.iconPath : "");
            buf.writeInt(config.iconWidth);
            buf.writeInt(config.iconHeight);
            buf.writeInt(config.iconTextSpacing);

            // Write button configuration
            buf.writeString(config.confirmButtonText != null ? config.confirmButtonText : "");
            buf.writeString(config.submitButtonText != null ? config.submitButtonText : "");
            buf.writeString(config.buttonLink != null ? config.buttonLink : "");

            // Write background configuration
            buf.writeBoolean(config.useCustomAnnouncementBackground);
            buf.writeString(config.announcementBackgroundPath != null ? config.announcementBackgroundPath : "");
        }

        private static AnnouncementConfig readConfigFromBuffer(RegistryByteBuf buf) {
            AnnouncementConfig config = new AnnouncementConfig();

            config.mainTitle = readStringSafe(buf);
            config.subTitle = readStringSafe(buf);
            config.announcementContent = readStringList(buf);
            config.useCustomRGB = buf.readBoolean();
            config.mainTitleColor = buf.readInt();
            config.subTitleColor = buf.readInt();
            config.contentColor = buf.readInt();
            config.scrollSpeed = buf.readDouble();
            config.showIcon = buf.readBoolean();
            config.iconPath = readStringSafe(buf);
            config.iconWidth = buf.readInt();
            config.iconHeight = buf.readInt();
            config.iconTextSpacing = buf.readInt();
            config.confirmButtonText = readStringSafe(buf);
            config.submitButtonText = readStringSafe(buf);
            config.buttonLink = readStringSafe(buf);
            config.useCustomAnnouncementBackground = buf.readBoolean();
            config.announcementBackgroundPath = readStringSafe(buf);

            return config;
        }

        private static String readStringSafe(RegistryByteBuf buf) {
            String str = buf.readString();
            return str.isEmpty() ? null : str;
        }

        private static List<String> readStringList(RegistryByteBuf buf) {
            int size = buf.readVarInt();
            if (size <= 0) {
                return new ArrayList<>();
            }

            List<String> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                String line = buf.readString();
                list.add(line);
            }
            return list;
        }
    }
}