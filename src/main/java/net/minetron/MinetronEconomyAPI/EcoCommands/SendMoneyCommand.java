package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class SendMoneyCommand extends PluginCommand {

    private PrefixC prefixC = new PrefixC();
    private String prefix = prefixC.prefixString;

    public SendMoneyCommand() {
        super("bakiyegonder", Main.getInstance());
        this.setDescription("İstediğin bir oyuncuya bakiye gönder.");
        this.setUsage("/bakiyegonder <oyuncu> <miktar>");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + TextFormat.RED + "Bu komutu sadece oyuncular kullanabilir.");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage(prefix + TextFormat.RED + "Kullanım: /bakiyegönder <oyuncu> <miktar>");
            return false;
        }

        String targetName = args[0];
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(prefix + TextFormat.RED + "Geçersiz miktar.");
            return false;
        }

        Player fromPlayer = (Player) sender;
        Player toPlayer = sender.getServer().getPlayerExact(targetName);

        if (toPlayer != null) {
            double fromBalance = EconomyAPI.getInstance().getBalance(fromPlayer);
            if (fromBalance >= amount) {
                EconomyAPI.getInstance().sendBalance(fromPlayer, toPlayer, amount);
                sender.sendMessage(prefix + TextFormat.GREEN + amount + " Tronium " + targetName + " adlı oyuncuya gönderildi.");
                toPlayer.sendMessage(prefix + TextFormat.GREEN + fromPlayer.getName() + " sana " + amount + " Tronium gönderdi!");
                // Loglama yapılabilir
                System.out.println("Oyuncu " + fromPlayer.getName() + " " + amount + " Tronium'u " + toPlayer.getName() + " adlı oyuncuya gönderdi.");
            } else {
                sender.sendMessage(prefix + TextFormat.RED + "Yetersiz bakiye.");
            }
        } else {
            sender.sendMessage(prefix + TextFormat.RED + "Oyuncu bulunamadı.");
        }
        return true;
    }
}
