package net.redstone233.atm.component.text;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.redstone233.atm.keys.ModKeys;

import java.util.function.Consumer;

public record BlazingFlameSwordComponent(boolean isDisplay) implements TooltipAppender {
    public static final BlazingFlameSwordComponent DEFAULT = new BlazingFlameSwordComponent(true);

    public static final Codec<BlazingFlameSwordComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("isDisplay").forGetter(BlazingFlameSwordComponent::isDisplay)
    ).apply(instance,BlazingFlameSwordComponent::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.translatable("tooltip.ability_sword.display1").formatted(Formatting.WHITE)
                .append(Text.translatable("key.use_ability.item",Text.keybind(ModKeys.USE_ABILITY_KEY.getBoundKeyLocalizedText().getString())
                        .formatted(Formatting.GOLD))
                .append(Text.translatable("tooltip.ability_sword.display2").formatted(Formatting.WHITE))
            )
        );

        textConsumer.accept(Text.literal("[武器品质]").formatted(Formatting.DARK_GRAY,Formatting.BOLD));
        textConsumer.accept(Text.literal("品质 ").formatted(Formatting.WHITE)
                .append(Text.literal("传说").formatted(Formatting.GOLD, Formatting.BOLD)));

        textConsumer.accept(Text.literal("[武器介绍]").formatted(Formatting.DARK_GRAY,Formatting.BOLD));
        textConsumer.accept(Text.literal("由陨铁与火晶石锻造的神兵，剑身呈深红色，表面有流动的岩浆纹路。").formatted(Formatting.YELLOW));
        textConsumer.accept(Text.literal("剑柄由黑曜石制成，发着橙红色的光芒，仿佛握着一团永不熄灭的烈焰。").formatted(Formatting.YELLOW));
        textConsumer.accept(Text.literal("剑刃炽热通红，边缘隐约可见金色的火焰纹路。").formatted(Formatting.YELLOW));
        textConsumer.accept(Text.literal("动时会在空中留下火焰轨迹，剑尖处常有小火苗跳动。").formatted(Formatting.YELLOW));
        textConsumer.accept(Text.literal("整体散发着橙红色的光芒，仿佛握着一团永不熄灭的烈焰。").formatted(Formatting.YELLOW));
    }
}
