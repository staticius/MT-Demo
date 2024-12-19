package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

import java.util.Map;
import java.util.stream.Collectors;

public class TopMoneyCommand extends PluginCommand {

    private PrefixC prefixC = new PrefixC();
    private String prefix = prefixC.prefixString;

    public TopMoneyCommand() {
        super("zenginler", Main.getInstance());
        this.setDescription("Top 3 zengin oyuncuları listeler.");
        this.setUsage("/zenginler");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Map<String, Double> balances = EconomyAPI.getInstance().getBalances();

            if (balances.isEmpty()) {
                player.sendMessage(prefix + "Bakiye verileri yüklenemedi: Bakiyesi olan oyuncu yok");
                return true;
            }

            // En yüksek 3 bakiyeyi al
            String top3 = balances.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(3)
                    .map(entry -> TextFormat.GREEN + entry.getKey() + ": " + TextFormat.GOLD + entry.getValue() + " Tronium")
                    .collect(Collectors.joining("\n"));

            player.sendMessage(prefix + "En Zenginler:\n" + top3);
            return true;
        }
        sender.sendMessage(prefix + TextFormat.RED + "Bu komutu yalnızca oyuncular kullanabilir.");
        return false;
    }
}
