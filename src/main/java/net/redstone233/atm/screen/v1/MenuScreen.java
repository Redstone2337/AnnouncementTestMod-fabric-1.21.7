package net.redstone233.atm.screen.v1;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.Optional;

import static net.redstone233.atm.AnnouncementTestMod.MOD_ID;

public class MenuScreen extends Screen {
    public MenuScreen() {
        super(Text.translatable("menu.title.text"));
    }

    @Override
    protected void init() {
        super.init();

        // 添加按钮 - 上部分区域
        int buttonWidth = 150;
        int buttonHeight = 20;

        // 第一个按钮
        ButtonWidget testButton1 = ButtonWidget.builder(
                        Text.translatable("menu.button.text.1"),
                        button -> {
                            // 按钮点击事件
                            System.out.println("按钮 1 被点击!");
                        })
                .dimensions(width / 2 - buttonWidth / 2, height / 4 + 20, buttonWidth, buttonHeight)
                .build();

        // 第二个按钮
        ButtonWidget testButton2 = ButtonWidget.builder(
                        Text.translatable("menu.button.text.2"),
                        button -> System.out.println("按钮 2 被点击!"))
                .dimensions(width / 2 - buttonWidth / 2, height / 4 + 50, buttonWidth, buttonHeight)
                .build();

        // 第三个按钮
        ButtonWidget testButton3 = ButtonWidget.builder(
                        Text.translatable("gui.cancel"),
                        button -> this.close())
                .dimensions(width / 2 - buttonWidth / 2, height / 4 + 80, buttonWidth, buttonHeight)
                .build();

        // 将按钮添加到屏幕
        this.addDrawableChild(testButton1);
        this.addDrawableChild(testButton2);
        this.addDrawableChild(testButton3);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        // 渲染半透明黑色背景
        context.fill(0, 0, this.width, this.height, 0x80000000); // 50% 透明度的黑色

        // 绘制主标题 (上部分)
        int titleY = this.height / 8;
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable("menu.body.text"),
                this.width / 2,
                titleY,
                0xFFFFFF // 白色
        );

        // 获取版本信息
        String modVersion = getModVersion();
        String gameVersion = getGameVersion();

        // 在标题左下角显示模组版本
        String modVersionText = "menu.title.modVersion.text" + modVersion;
        context.drawTextWithShadow(
                this.textRenderer,
                Text.translatable(modVersionText),
                10, // 左边距
                titleY + 15, // 标题下方
                0x88FF88 // 浅绿色
        );

        // 在标题右下角显示游戏版本
        String gameVersionText = "menu.title.gameVersion.text" + gameVersion;
        int gameVersionWidth = this.textRenderer.getWidth(gameVersionText);
        context.drawTextWithShadow(
                this.textRenderer,
                Text.translatable(gameVersionText),
                this.width - gameVersionWidth - 10, // 右边距
                titleY + 15, // 标题下方
                0x8888FF // 浅蓝色
        );

        // 绘制分隔线（可选）
        int separatorY = titleY + 35;
        context.fill(10, separatorY, this.width - 10, separatorY + 1, 0x44FFFFFF);

        // 绘制说明文本
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable("menu.body.text"),
                this.width / 2,
                separatorY + 10,
                0xCCCCCC
        );

        // 渲染所有子部件（按钮等)
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    private String getModVersion() {
        try {
            // 从 fabric.mod.json 获取版本
            Optional<ModContainer> modContainer = FabricLoader.getInstance()
                    .getModContainer(MOD_ID);

            if (modContainer.isPresent()) {
                return modContainer.get().getMetadata().getVersion().getFriendlyString();
            }
            return "1.0.0";
        } catch (Exception e) {
            return "未知";
        }
    }

    private String getGameVersion() {
        try {
            // 获取游戏版本
            return FabricLoader.getInstance()
                    .getModContainer("minecraft")
                    .orElseThrow()
                    .getMetadata()
                    .getVersion()
                    .getFriendlyString();
        } catch (Exception e) {
            return "未知";
        }
    }

    @Override
    public void close() {
        // 关闭屏幕，返回游戏
        if (this.client != null) {
            this.client.setScreen(null);
        }
    }
}
