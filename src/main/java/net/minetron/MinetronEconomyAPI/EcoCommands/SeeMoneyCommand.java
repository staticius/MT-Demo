package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class SeeMoneyCommand extends PluginCommand {


    private PrefixC prefixC = new PrefixC();
    private String prefix = prefixC.prefixString;

    public SeeMoneyCommand() {
        super("bakiyebak", Main.getInstance());
        this.setDescription("Başka bir oyuncunun bakiyesini gösterir.");
        this.setUsage("/bakiyebak <oyuncu>");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(prefix + TextFormat.RED + "Kullanım: /bakiyebak <oyuncu>");
            return false;
        }

        String playerName = args[0];
        Player targetPlayer = sender.getServer().getPlayerExact(playerName);

        if (targetPlayer != null) {
            double balance = EconomyAPI.getInstance().getBalance(targetPlayer);
            sender.sendMessage(prefix + TextFormat.GREEN + playerName + " adlı oyuncunun bakiyesi: " + balance + " Tronium");
        } else {
            sender.sendMessage(prefix + TextFormat.RED + "Oyuncu bulunamadı.");
        }
        return true;
    }
}
