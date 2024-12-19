package net.minetron.MinetronEconomyAPI.EcoCommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.minetron.Main;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class MyMoneyCommand extends PluginCommand {

    private final String prefix;

    public MyMoneyCommand() {
        super("bakiyem", Main.getInstance());
        this.setDescription("Oyuncunun bakiyesini gösterir.");
        this.setUsage("/bakiyem");
        PrefixC prefixC = new PrefixC();
        this.prefix = prefixC.prefixString;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            double balance = EconomyAPI.getInstance().getBalance(player);
            player.sendActionBar(prefix + "Mevcut bakiyeniz: " + balance + " Tronium");
            return true;
        }
        sender.sendMessage(prefix + "Bu komutu yalnızca oyuncular kullanabilir.");
        return false;
    }
}
