package net.waveringana.killtracker;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents.AfterDeath;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class KillListener implements AfterDeath {
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof ServerPlayerEntity) {
            KillTracker.LOGGER.info( String.format("Player %s killed %b", damageSource.getAttacker().getEntityName(), entity));
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
            addCounter(weapon, (ServerPlayerEntity) damageSource.getAttacker());
        }
    }

    public void addCounter(ItemStack weapon, ServerPlayerEntity player) {
        if (weapon.hasNbt() && weapon.getNbt().contains("Kill Count")) {
            NbtCompound nbt = weapon.getNbt();
            int killcount = nbt.getInt("Kill Count");

            NbtCompound display = new NbtCompound();
            NbtList lore = new NbtList();
            NbtCompound rawJson = new NbtCompound();

            rawJson.putString("killcount",  String.format ("{\"text\": \"StatTrak Kill Count: %s\" }", killcount));
            lore.add(rawJson.get("killcount"));
            display.put("Lore", lore);

            nbt.put("display", display);

            weapon.setNbt(nbt);
        }
    }
}
