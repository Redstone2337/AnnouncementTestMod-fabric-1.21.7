package net.redstone233.atm.keys;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static KeyBinding ANNOUNCEMENT_KEY = new KeyBinding(
            "key.atm.announcement",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.atm"
    );

    public static KeyBinding USE_ABILITY_KEY = new KeyBinding(
            "key.atm.use_ability",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.atm"
    );


    public static void register() {
        KeyBindingHelper.registerKeyBinding(ANNOUNCEMENT_KEY);
        KeyBindingHelper.registerKeyBinding(USE_ABILITY_KEY);
    }

    public static boolean isAnnouncementKeyPressed() {
        return ANNOUNCEMENT_KEY.isPressed();
    }

    public static boolean isUseAbilityKeyPressed() {
        return USE_ABILITY_KEY.isPressed();
    }
}
