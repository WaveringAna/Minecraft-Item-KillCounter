package net.waveringana.stattrakcounter;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents.After;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.State;
import net.minecraft.world.World;

public class ItemsMinedListener
implements After { //Event for after block break

    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
            BlockEntity blockEntity) {
        StatTrakCounter.LOGGER.info( String.format("Player %s broke %s", player.getEntityName(), State.NAME));
        ItemStack item = player.getMainHandStack();
        NbtCompound nbt = item.getOrCreateNbt();
        
        int minedCount = 1;

        if (nbt.contains("Mined Count")) {
            minedCount = item.getNbt().getInt("Mined Count");
            minedCount++;
            nbt.putInt("Mined Count", minedCount);
        } else {
            nbt.putInt("Mined Count", minedCount);
        }

        item.setNbt(nbt);

        util.addCounter(item, player, "Mined Count", "StatTrak Mined Count");
    }
    
}
