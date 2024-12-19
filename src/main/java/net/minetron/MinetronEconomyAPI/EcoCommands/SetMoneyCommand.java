package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class SetMoneyCommand extends PluginCommand {

    private PrefixC prefixC = new PrefixC();
    private String prefix = prefixC.prefixString;

    public SetMoneyCommand() {
        super("bakiyeayarla", Main.getInstance());
        this.setDescription("Bir oyuncunun bakiyesini değiştirir.");
        this.setUsage("/bakiyeayarla <oyuncu> <miktar>");
        this.setPermission("minetron.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission("minetron.admin")) {
            sender.sendMessage(prefix + TextFormat.RED + "Bu komutu kullanma yetkiniz yok.");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage(prefix + TextFormat.RED + "Kullanım: /bakiyeayarla <oyuncu> <miktar>");
            return false;
        }

        String playerName = args[0];
        double amount;

        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(prefix + TextFormat.RED + "Geçersiz miktar.");
            return false;
        }

        Player targetPlayer = sender.getServer().getPlayerExact(playerName);
        if (targetPlayer != null) {
            EconomyAPI.getInstance().setBalance(targetPlayer, amount);
            sender.sendMessage(prefix + TextFormat.GREEN + playerName + " adlı oyuncunun bakiyesi " + amount + " olarak ayarlandı.");
            // Loglama yapılabilir
            System.out.println("Admin " + sender.getName() + " " + playerName + " adlı oyuncunun bakiyesini " + amount + " olarak ayarladı.");
        } else {
            sender.sendMessage(prefix + TextFormat.RED + "Oyuncu bulunamadı.");
        }
        return true;
    }

}
