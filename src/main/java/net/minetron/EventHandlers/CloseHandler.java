package net.minetron.EventHandlers;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.inventory.fake.FakeInventory;
import cn.nukkit.item.Item;
import net.minetron.Managers.BagConfigManager;

import java.util.HashMap;
import java.util.Map;

public class CloseHandler implements Listener {

    @EventHandler
    public void handleInventoryClose(InventoryCloseEvent event) {
        if (!(event.getInventory() instanceof FakeInventory)) {
            return; // Eğer envanter FakeInventory değilse çık
        }

        FakeInventory inventory = (FakeInventory) event.getInventory();
        Map<Integer, Map<String, Object>> items = new HashMap<>();

        for (int i = 0; i < inventory.getSize(); i++) {
            Item item = inventory.getItem(i);
            String itemId = item.getId(); // ID'yi string olarak al

            if (item != null && !itemId.equals("minecraft:air")) { // Hava eşyası kontrolü
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("id", itemId); // ID'yi string olarak kaydet
                itemData.put("count", item.getCount()); // Adedi integer olarak kaydet
                items.put(i, itemData);
            }
        }

        // Eşyaları config.yml dosyasına kaydet
        Map<String, Map<Integer, Map<String, Object>>> chests = BagConfigManager.getChests();
        chests.put("player_" + event.getPlayer().getName(), items);
        BagConfigManager.saveChests(chests);
    }

}
