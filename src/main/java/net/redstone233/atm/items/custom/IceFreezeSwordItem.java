package net.redstone233.atm.items.custom;

import net.minecraft.entity.EquipmentSlot;
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
import net.redstone233.atm.component.text.IceFreezeSwordComponent;
import net.redstone233.atm.keys.ModKeys;

public class IceFreezeSwordItem extends Item {
    public IceFreezeSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(settings
                .sword(material, attackDamage, attackSpeed)
                .component(ModComponentTypes.ICE_FREEZE_SWORD_COMPONENT, IceFreezeSwordComponent.DEFAULT)
        );
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof LivingEntity livingEntity && attacker instanceof PlayerEntity player) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,300,255,true,true,true));
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
               target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,6000,255,true,true,true));
            }
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,3600,4));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,1800,6));
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        super.postDamageEntity(stack, target, attacker);
    }
}
