package net.redstone233.atm.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.redstone233.atm.AnnouncementTestMod;
import net.redstone233.atm.items.custom.BlazingFlameSwordItem;
import net.redstone233.atm.items.custom.IceFreezeSwordItem;
import net.redstone233.atm.materials.ModToolMaterials;

import java.util.function.Function;

public class ModItems {

    public static final Item BLAZING_FLAME_SWORD = register("blazing_flame_sword",
            settings -> new BlazingFlameSwordItem(ModToolMaterials.SPECIAL,50, 7.5f ,settings),
            new Item.Settings()
                    .maxDamage(300000)
                    .rarity(Rarity.RARE)
                    .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
    );

    public static final Item ICE_FREEZE_SWORD = register("ice_freeze_sword",
            settings -> new IceFreezeSwordItem(ModToolMaterials.SPECIAL,55, 7.5f ,settings),
            new Item.Settings()
                    .maxDamage(300000)
                    .rarity(Rarity.RARE)
                    .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE,true)
    );

    private static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AnnouncementTestMod.MOD_ID, id));
        return Items.register(registryKey, factory, settings);
    }

    private static Item register(String id, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AnnouncementTestMod.MOD_ID, id));
        return Items.register(registryKey, Item::new, settings);
    }

    public static void init() {
        AnnouncementTestMod.LOGGER.info("物品注册成功！");
    }
}
