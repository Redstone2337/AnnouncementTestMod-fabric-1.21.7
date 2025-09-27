package net.redstone233.atm.keys;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static KeyBinding ANNOUNCEMENT_KEY = new KeyBinding(
            "key.mtc.announcement",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.freezesword"
    );


    public static void register() {
        KeyBindingHelper.registerKeyBinding(ANNOUNCEMENT_KEY);
    }

    public static boolean isAnnouncementKeyPressed() {
        return ANNOUNCEMENT_KEY.isPressed();
    }
}
