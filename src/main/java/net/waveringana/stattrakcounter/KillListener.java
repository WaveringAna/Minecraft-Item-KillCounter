package net.waveringana.stattrakcounter;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.AfterDeath;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;

public class KillListener 
implements AfterDeath {
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof ServerPlayerEntity == false) {
            return;
        }
        
        ServerPlayerEntity player = (ServerPlayerEntity) damageSource.getAttacker();
        ItemStack itemStack = player.getMainHandStack();
        Item item = itemStack.getItem();

        if (item instanceof ToolItem || item instanceof SwordItem || item instanceof BowItem) {
		    util.incrementCount(player.getMainHandStack(), player, "Kill Count", "StatTrak Kill Count");
        }
    }
}
