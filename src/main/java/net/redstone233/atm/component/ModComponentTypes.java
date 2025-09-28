package net.redstone233.atm.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.redstone233.atm.AnnouncementTestMod;
import net.redstone233.atm.component.text.BlazingFlameSwordComponent;
import net.redstone233.atm.component.text.IceFreezeSwordComponent;

import java.util.function.UnaryOperator;

public class ModComponentTypes {

    public static final ComponentType<BlazingFlameSwordComponent> BLAZING_FLAME_SWORD_COMPONENT = register(
            "blazing_flame_sword",
            blazingFlameSwordComponentBuilder -> blazingFlameSwordComponentBuilder.codec(
                    BlazingFlameSwordComponent.CODEC
            )
    );

    public static final ComponentType<IceFreezeSwordComponent> ICE_FREEZE_SWORD_COMPONENT = register(
            "ice_freeze_sword",
            iceFreezeSwordComponentBuilder -> iceFreezeSwordComponentBuilder.codec(
                    IceFreezeSwordComponent.CODEC
            )

    );

    public static void init() {
        AnnouncementTestMod.LOGGER.info("数据组件类型注册成功！");
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(AnnouncementTestMod.MOD_ID,id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
