package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class ReduceMoneyCommand extends PluginCommand {

    PrefixC prefixC = new PrefixC();
    String prefix = prefixC.prefixString;

    public ReduceMoneyCommand() {
        super("bakiyesil", Main.getInstance());
        this.setDescription("Bir oyuncunun bakiyesinden belirli bir miktarı siler.");
        this.setUsage("/bakiyesil <oyuncu> <miktar>");
        this.setPermission("minetron.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission("minetron.admin")) {
            sender.sendMessage(prefix + "Bu komutu kullanma yetkiniz yok.");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage(prefix + "Kullanım: /bakiyesil <oyuncu> <miktar>");
            return false;
        }

        String playerName = args[0];
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(prefix + "Geçersiz miktar.");
            return false;
        }

        Player targetPlayer = sender.getServer().getPlayer(playerName);

        if (targetPlayer != null) {
            double currentBalance = EconomyAPI.getInstance().getBalance(targetPlayer);
            if (currentBalance >= amount) {
                EconomyAPI.getInstance().subtractBalance(targetPlayer, amount);
                sender.sendMessage(prefix + playerName + " adlı oyuncunun bakiyesinden " + amount + " Tronium silindi.");
            } else {
                sender.sendMessage(prefix + "Yetersiz bakiye. " + playerName + " adlı oyuncunun bakiyesi " + currentBalance + " Tronium.");
            }
        } else {
            sender.sendMessage(prefix + "Oyuncu bulunamadı.");
        }
        return true;
    }
}
