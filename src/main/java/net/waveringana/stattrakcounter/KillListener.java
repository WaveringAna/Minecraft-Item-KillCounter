package net.waveringana.stattrakcounter;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.AfterDeath;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class KillListener 
implements AfterDeath {
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof ServerPlayerEntity == false) {
            return;
        }
        
        StatTrakCounter.LOGGER.info( String.format("Player %s killed %s", damageSource.getAttacker().getEntityName(), entity.getType()));
        ItemStack weapon = ((ServerPlayerEntity) damageSource.getAttacker()).getMainHandStack();
        NbtCompound nbt = weapon.getOrCreateNbt();

        int killcount = 1;

        if (nbt.contains("Kill Count")) {
            killcount = weapon.getNbt().getInt("Kill Count");
            killcount++;
            nbt.putInt("Kill Count", killcount);
        } else {
            nbt.putInt("Kill Count", killcount);
        }

        weapon.setNbt(nbt);
        util.addCounter(weapon, (ServerPlayerEntity) damageSource.getAttacker(), "Kill Count", "StatTrak Kill Count");
    }
}
