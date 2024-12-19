package net.minetron.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.minetron.Managers.BanManager;
import net.minetron.PERMISSIONS.PERMISSIONS;

public class BanCommand extends Command {

    private final BanManager banManager;

    public BanCommand(String name, BanManager banManager) {
        super(name, "/mtban <oyuncu> <süre-gün> <sebep>", "/mtban <oyuncu> <süre=gün> <sebep>", new String[]{});
        this.setPermission(PERMISSIONS.MT_PERMISSION_ADMIN);
        this.banManager = banManager;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        // Yetki kontrolü
        if (!sender.isOp()) {
            sender.sendMessage(TextFormat.RED + "Bu komutu sadece OP'lar kullanabilir!");
            return false;
        }

        // Argüman kontrolü
        if (args.length < 3) {
            sender.sendMessage(TextFormat.RED + "Kullanım: /mtban <oyuncu> <süre=gün> <sebep>");
            return false;
        }

        String playerName = args[0];
        int days;
        // Gün sayısının geçerli olup olmadığını kontrol etme
        try {
            days = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(TextFormat.RED + "Geçerli bir gün sayısı giriniz!");
            return false;
        }

        // Sebep metnini oluşturma
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reasonBuilder.append(args[i]);
            if (i < args.length - 1) {
                reasonBuilder.append(" ");
            }
        }
        String reason = reasonBuilder.toString();

        // Oyuncuyu banlama
        banManager.banPlayer(sender, playerName, days, reason);

        // Broadcast mesajı oluşturma
        String broadcastMessage = TextFormat.RED + playerName + " adlı oyuncu yasaklandı! Sebep: " + reason + ", Süre: " + days + " gün. Lütfen kurallara uymaya dikkat edin.";

        // Broadcast mesajını gönderme
        sender.getServer().broadcastMessage(broadcastMessage);

        // Başarılı işlem mesajı
        sender.sendMessage(TextFormat.GREEN + "Oyuncu başarıyla banlandı: " + playerName);
        return true;
    }
}
