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

    public static void addCounter(ItemStack item, PlayerEntity player, String nbttag, String tag) {
        NbtCompound nbt = item.getNbt();
        int killCount = nbt.getInt("Kill Count");
        int mineCount = nbt.getInt("Mined Count");
        //int counter = nbt.getInt(nbttag);

        NbtCompound display = item.getSubNbt("display");
        NbtCompound rawJSON = new NbtCompound();
        NbtList lore = new NbtList();

        if (display == null) {
            display = new NbtCompound();
        }

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
