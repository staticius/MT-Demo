package net.minetron.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.fake.FakeInventory;
import net.minetron.Forms.BagGUI;
import net.minetron.Forms.BagPurchaseForm;
import net.minetron.Managers.BagConfigManager;

public class BagCommand extends Command {
    public BagCommand() {
        super("canta", "10.000 Tronium karşılığında 'Çanta' alarak eşyalarını güvende tut!", "/çanta");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (BagConfigManager.hasBag(player.getName())) {
                // Çanta menüsünü oluştur ve oyuncuya göster
                FakeInventory inventory = BagGUI.createChestMenu(player);
                player.addWindow(inventory);
            } else {
                // Çanta satın alma formunu aç
                BagPurchaseForm.openBagPurchaseForm(player);
            }

            return true;
        }
        return false;
    }
}
