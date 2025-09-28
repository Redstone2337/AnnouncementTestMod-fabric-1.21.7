package net.redstone233.atm.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.redstone233.atm.AnnouncementTestMod;

public class ModItemGroup {

    public static final RegistryKey<ItemGroup> MOD_ITEMS = register("mod_items");

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(AnnouncementTestMod.MOD_ID,id));
    }

    public static void register() {
        Registry.register(Registries.ITEM_GROUP,ModItemGroup.MOD_ITEMS,
                ItemGroup.create(
                                ItemGroup.Row.TOP, 0)
                        .displayName(Text.translatable("itemGroup.atm.mod_items"))
                        .icon(() -> new ItemStack(ModItems.BLAZING_FLAME_SWORD)).entries((displayContext, entries) -> {
                            entries.add(ModItems.BLAZING_FLAME_SWORD);
                            entries.add(ModItems.ICE_FREEZE_SWORD);
                        })
                        .build()
        );

        AnnouncementTestMod.LOGGER.info("创建物品栏成功！");
    }
}
