package net.redstone233.atm.items.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.xml.crypto.Data;

public class TestItem extends Item {
    public TestItem(Settings settings) {
        super(settings.component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE,true));
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 14.0, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -5.5F, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }


    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof LivingEntity livingEntity && attacker instanceof ServerPlayerEntity player) {
            livingEntity.setOnFireForTicks(120);
            if (!livingEntity.isDead()) {
                livingEntity.kill(player.getWorld());
            }
            if (player instanceof PlayerEntity player1) {
                player1.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,1200,4,false,false,false));
                player1.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 300,4,false,true,true));
            }
        }
        super.postHit(stack, target, attacker);
    }


    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,1800,6));
            return ActionResult.SUCCESS;
        } else {
//            user.sendMessage(Text.literal("这个物品没有任何右键功能。"),false);
            user.sendMessage(Text.literal("似乎并没有正常执行。").formatted(Formatting.RED,Formatting.BOLD),false);
            return ActionResult.PASS;
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user instanceof ServerPlayerEntity player && entity instanceof LivingEntity target) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,6000,1,true,true,true));
            if (!target.isDead()) {
                target.kill(player.getWorld());
            }
            if (player instanceof PlayerEntity player1) {
                player1.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,1200,4,false,false,false));
                player1.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 300,4,false,true,true));
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }
}
