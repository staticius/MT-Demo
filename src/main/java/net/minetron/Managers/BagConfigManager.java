package net.minetron.Managers;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BagConfigManager {

    private static Config config;

    public static void loadConfig(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "bags.yml");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Config Dosyası Oluşturuldu: " + file.getPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        config = new Config(file, Config.YAML);

        if (!config.exists("chests")) {
            config.set("chests", new HashMap<String, Object>());
            config.save();
        }
    }

    public static Config getConfig() {
        return config;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Map<Integer, Map<String, Object>>> getChests() {
        Map<String, Object> chestsSection = config.get("chests", new HashMap<String, Object>());
        Map<String, Map<Integer, Map<String, Object>>> chests = new HashMap<>();

        for (Map.Entry<String, Object> entry : chestsSection.entrySet()) {
            String playerName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Map<Integer, Map<String, Object>> items = new HashMap<>();
                for (Map.Entry<?, ?> itemEntry : ((Map<?, ?>) value).entrySet()) {
                    Integer slot = Integer.parseInt(itemEntry.getKey().toString());
                    Map<String, Object> itemData = (Map<String, Object>) itemEntry.getValue();
                    items.put(slot, itemData);
                }
                chests.put(playerName, items);
            }
        }

        return chests;
    }

    public static void saveChests(Map<String, Map<Integer, Map<String, Object>>> chests) {
        Map<String, Object> chestsSection = new HashMap<>();
        for (Map.Entry<String, Map<Integer, Map<String, Object>>> entry : chests.entrySet()) {
            String playerName = entry.getKey();
            Map<Integer, Map<String, Object>> items = entry.getValue();
            Map<String, Object> itemSection = new HashMap<>();
            for (Map.Entry<Integer, Map<String, Object>> itemEntry : items.entrySet()) {
                itemSection.put(String.valueOf(itemEntry.getKey()), itemEntry.getValue());
            }
            chestsSection.put(playerName, itemSection);
        }
        config.set("chests", chestsSection);
        config.save();
    }

    public static void grantBag(String playerName) {
        Map<String, Map<Integer, Map<String, Object>>> chests = getChests();
        String key = "player_" + playerName;
        if (!chests.containsKey(key)) {
            chests.put(key, new HashMap<>());
            saveChests(chests);
        }
    }

    public static boolean hasBag(String playerName) {
        return getChests().containsKey("player_" + playerName);
    }
}
