package net.minetron.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.window.FormWindowCustom;
import net.minetron.Main;
import net.minetron.Utils.TpaUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TpaCommand extends Command {

    private final Main plugin;

    public TpaCommand(Main plugin) {
        super("tpa", "Işınlanma isteği gönder", "/tpa [kabul || red]");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komutlar yanlızca oyun içinde kullanılabilir.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            // Online oyuncuları dropdown'da göster
            List<String> onlinePlayers = plugin.getServer().getOnlinePlayers().values().stream()
                    .filter(p -> !p.equals(player)) // Kendi kendini dışla
                    .map(Player::getName)
                    .collect(Collectors.toList());

            if (onlinePlayers.isEmpty()) {
                player.sendMessage("Çevrimiçi oyuncu yok.");
                return true;
            }

            FormWindowCustom form = new FormWindowCustom("Işınlanma");
            form.addElement(new ElementDropdown("Bir Oyuncu Seç", onlinePlayers));
            player.showFormWindow(form, Main.TPA_FORM_ID);
            return true;

        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("kabul")) {
                // Gelen teleport isteğini kabul et
                if (TpaUtils.hasPendingRequest(player)) {
                    Player requester = TpaUtils.getRequester(player);
                    if (requester != null) {
                        requester.teleport(player.getLocation());
                        player.sendMessage("Işınlanma isteği kabul edildi.");
                        requester.sendMessage(player.getName() + "adlı oyuncu senin isteğini kabul etti.");
                        TpaUtils.clearRequest(player);
                    } else {
                        player.sendMessage("Bulunamadı.");
                    }
                } else {
                    player.sendMessage("Işınlanma isteği almamışsın.");
                }
                return true;

            } else if (args[0].equalsIgnoreCase("red")) {
                // Gelen teleport isteğini reddet
                if (TpaUtils.hasPendingRequest(player)) {
                    Player requester = TpaUtils.getRequester(player);
                    if (requester != null) {
                        player.sendMessage("Işınlanma isteği reddedildi.");
                        requester.sendMessage(player.getName() + " adlı oyuncu senin ışınlanma isteğini kabul etti.");
                        TpaUtils.clearRequest(player);
                    } else {
                        player.sendMessage("Bulunamadı.");
                    }
                } else {
                    player.sendMessage("Işınlanma isteği almamışsın.");
                }
                return true;
            }
        }

        player.sendMessage("Kullanım: /tpa [kabul || red]");
        return false;
    }
}
