package net.redstone233.atm.config.v1;

import java.util.List;
import java.util.Objects;

public class AnnouncementConfig {
    public String mainTitle = "Server Announcement";
    public String subTitle = "Latest News";
    public List<String> announcementContent;
    public String confirmButtonText = "Confirm";
    public String submitButtonText = "Submit Report";
    public String buttonLink = "https://example.com";
    public boolean showIcon = false;
    public String iconPath = "";
    public int iconWidth = 32;
    public int iconHeight = 32;
    public int iconTextSpacing = 10;
    public boolean useCustomRGB = false;
    public int mainTitleColor = 0xFFFFFF;
    public int subTitleColor = 0xCCCCCC;
    public int contentColor = 0xFFFFFF;
    public double scrollSpeed = 1.0;
    public boolean useCustomAnnouncementBackground = false;
    public String announcementBackgroundPath = "";

    public AnnouncementConfig() {
        this.announcementContent = List.of(
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnouncementConfig that = (AnnouncementConfig) o;
        return showIcon == that.showIcon &&
                iconWidth == that.iconWidth &&
                iconHeight == that.iconHeight &&
                iconTextSpacing == that.iconTextSpacing &&
                useCustomRGB == that.useCustomRGB &&
                mainTitleColor == that.mainTitleColor &&
                subTitleColor == that.subTitleColor &&
                contentColor == that.contentColor &&
                Double.compare(that.scrollSpeed, scrollSpeed) == 0 &&
                useCustomAnnouncementBackground == that.useCustomAnnouncementBackground &&
                Objects.equals(mainTitle, that.mainTitle) &&
                Objects.equals(subTitle, that.subTitle) &&
                Objects.equals(announcementContent, that.announcementContent) &&
                Objects.equals(confirmButtonText, that.confirmButtonText) &&
                Objects.equals(submitButtonText, that.submitButtonText) &&
                Objects.equals(buttonLink, that.buttonLink) &&
                Objects.equals(iconPath, that.iconPath) &&
                Objects.equals(announcementBackgroundPath, that.announcementBackgroundPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainTitle, subTitle, announcementContent, confirmButtonText, submitButtonText,
                buttonLink, showIcon, iconPath, iconWidth, iconHeight, iconTextSpacing, useCustomRGB,
                mainTitleColor, subTitleColor, contentColor, scrollSpeed, useCustomAnnouncementBackground,
                announcementBackgroundPath);
    }

    public String getConfigHash() {
        return Integer.toHexString(this.hashCode());
    }
}