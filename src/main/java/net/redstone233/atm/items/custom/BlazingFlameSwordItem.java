package net.redstone233.atm.items.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.redstone233.atm.component.ModComponentTypes;
import net.redstone233.atm.component.text.BlazingFlameSwordComponent;
import net.redstone233.atm.keys.ModKeys;

public class BlazingFlameSwordItem extends Item {
    public BlazingFlameSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(settings
                .sword(material, attackDamage, attackSpeed)
                .component(ModComponentTypes.BLAZING_FLAME_SWORD_COMPONENT, BlazingFlameSwordComponent.DEFAULT)
        );
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof LivingEntity livingEntity && attacker instanceof PlayerEntity player) {
            livingEntity.isOnFire();
            livingEntity.setOnFire(true);
            livingEntity.setOnFireForTicks(2400);
            livingEntity.setOnFireFor(120.0f);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,1200,4,false,false,false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,1200,4,false,false,false));
        }
        super.postHit(stack, target, attacker);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof PlayerEntity player && world.isClient) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,1200,4,false,false,false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 300,4,false,true,true));
            return ActionResult.SUCCESS;
        } else {
           user.sendMessage(Text.literal("似乎并没有正常执行。").formatted(Formatting.RED,Formatting.BOLD),false);
            return ActionResult.PASS;
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user instanceof PlayerEntity player && entity instanceof LivingEntity target) {
            if (ModKeys.isUseAbilityKeyPressed()) {
                target.setOnFire(true);
                target.setOnFireFor(180.0f);
                target.setOnFireForTicks(3600);
            }
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,3600,4));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,1800,6));
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }
}
