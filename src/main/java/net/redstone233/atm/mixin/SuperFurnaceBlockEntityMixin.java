package net.redstone233.atm.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class SuperFurnaceBlockEntityMixin {
    @Unique
    private boolean isFast = false;

    @Unique
    private final static BlockPattern SUPER_FURNACE = BlockPatternBuilder.start()
            .aisle("AAA","AAA","AAA")
            .aisle("AAA","AAA","ABA")
            .aisle("AAA","AAA","AAA")
            .where('A', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.STONE)))
            .where('B', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.FURNACE)))
            .build();


    @Inject(method = "tick", at = @At("TAIL"))
    private static void onServerTick(ServerWorld world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        if (!(state.getBlock() instanceof FurnaceBlock)) return;
        SuperFurnaceBlockEntityMixin mixin = (SuperFurnaceBlockEntityMixin) (Object) blockEntity;
        boolean bl = SUPER_FURNACE.searchAround(world, pos) != null;
        if (bl && mixin != null) {
            mixin.isFast = true;
        } else if (mixin != null) {
            mixin.isFast = false;
        }
    }

    @Inject(method = "getCookTime", at = @At("HEAD"), cancellable = true)
    private static void onGetCookTime(ServerWorld world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> cir) {
        SuperFurnaceBlockEntityMixin mixin = (SuperFurnaceBlockEntityMixin) (Object) furnace;
        if (mixin != null && mixin.isFast) {
            cir.setReturnValue(cir.getReturnValueI()/4);
        }
    }
}
