package net.waveringana.stattrakcounter;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class util {
    private static String createTagString(String tagName, int count) {
        return String.format("{\"text\": \"%s: %s\" }", tagName, count);
    }

    public static void incrementCount(ItemStack item, PlayerEntity player, String countType, String displayName) {
        NbtCompound nbt = item.getOrCreateNbt();
        int count = 1;
        if (nbt.contains(countType)) {
            count = item.getNbt().getInt(countType);
            count++;
        }
        nbt.putInt(countType, count);
        item.setNbt(nbt);
        util.addCounter(item, player, countType, displayName);
    }

    public static void addCounter(ItemStack item, PlayerEntity player, String nbttag, String tag) {
        NbtCompound nbt = item.getNbt();
        int killCount = nbt.getInt("Kill Count");
        int mineCount = nbt.getInt("Mined Count");

        NbtCompound display = item.getOrCreateSubNbt("display");
        NbtCompound rawJSON = new NbtCompound();
        NbtList lore = new NbtList();

        Map<String, String> tagMap = new LinkedHashMap<>();

        if (killCount != 0) {
            String killstring = createTagString("StatTrak™ Kills", killCount);
            tagMap.put("killedtag", killstring);
        }
        if (mineCount != 0) {
            String minedstring = createTagString("StatTrak™ Blocks Mined", mineCount);
            tagMap.put("minedtag", minedstring);
        }

        for (Map.Entry<String, String> entry : tagMap.entrySet()) {
            rawJSON.putString(entry.getKey(), entry.getValue());
            lore.add(rawJSON.get(entry.getKey()));
        }

        display.put("Lore", lore);
        StatTrakCounter.LOGGER.info(display.toString());

        nbt.put("display", display);

        item.setNbt(nbt);
    }
}
