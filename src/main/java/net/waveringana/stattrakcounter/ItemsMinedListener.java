package net.waveringana.stattrakcounter;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemsMinedListener
implements PlayerBlockBreakEvents.After { //Event for after block break

    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
            BlockEntity blockEntity) {
        ItemStack itemStack = player.getMainHandStack();
        Item item = itemStack.getItem();
        
        if (item instanceof ToolItem || item instanceof SwordItem || item instanceof BowItem) {
            util.incrementCount(player.getMainHandStack(), player, "Mined Count", "StatTrak Mined Count");
        }
    }
}
