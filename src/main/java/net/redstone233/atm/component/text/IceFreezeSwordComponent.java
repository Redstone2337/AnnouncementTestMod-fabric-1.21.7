package net.redstone233.atm.component.text;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public record IceFreezeSwordComponent(boolean display) implements TooltipAppender {
    public static final IceFreezeSwordComponent DEFAULT = new IceFreezeSwordComponent(true);

    public static final Codec<IceFreezeSwordComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("isDisplay").forGetter(IceFreezeSwordComponent::display)
    ).apply(instance,IceFreezeSwordComponent::new));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
        textConsumer.accept(Text.literal("极寒玄冰凝聚而成的神剑，通体的冰晶。剑柄缠绕着银白色的冰蚕丝，护手处镶嵌着天蓝色的冰魄石。"));
        textConsumer.accept(Text.literal("剑柄缠绕着银白色的冰蚕丝，护手处镶嵌着天蓝色的冰雕琢，薄如蝉翼却坚不可摧，挥动时会飘散出细小的冰晶。"));
        textConsumer.accept(Text.literal("剑尖处常有寒气缭绕，形成小型的雪花漩涡。"));
        textConsumer.accept(Text.literal("整体散发着幽蓝的寒光，仿佛能冻结时间。"));
    }
}
