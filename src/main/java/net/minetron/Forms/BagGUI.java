package net.minetron.Forms;

import cn.nukkit.Player;
import cn.nukkit.inventory.fake.FakeInventory;
import cn.nukkit.inventory.fake.FakeInventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.minetron.Managers.BagConfigManager;

import java.util.Map;

public class BagGUI {

    public static FakeInventory createChestMenu(Player player) {

        FakeInventory fakeInventory = new FakeInventory(FakeInventoryType.DOUBLE_CHEST, "title");
        FakeInventory inventory = new FakeInventory(FakeInventoryType.DOUBLE_CHEST, TextFormat.RED + player.getName() + ": Çanta");

        // Config dosyasından eşyaları yükle
        Map<String, Map<Integer, Map<String, Object>>> chests = BagConfigManager.getChests();
        Map<Integer, Map<String, Object>> playerItems = chests.get("player_" + player.getName());

        if (playerItems != null) {
            for (Map.Entry<Integer, Map<String, Object>> entry : playerItems.entrySet()) {
                int slot = entry.getKey();
                Map<String, Object> itemData = entry.getValue();
                String id = (String) itemData.get("id"); // ID'yi string olarak al
                int count = (int) itemData.get("count"); // Integer olarak al
                inventory.setItem(slot, Item.get(id, 0, count));
            }
        }

        return inventory;
    }
}
