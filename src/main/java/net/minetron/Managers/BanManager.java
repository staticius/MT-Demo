package net.minetron.Managers;

import cn.nukkit.command.CommandSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minetron.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BanManager {

    private static final String BAN_FILE = "yasaklananlar.json";
    private final Gson gson = new Gson();

    public boolean isPlayerBanned(String playerName) {
        List<BanInfo> banList = loadBanList();
        for (BanInfo banInfo : banList) {
            if (banInfo.getPlayer().equals(playerName)) {
                if (System.currentTimeMillis() > banInfo.getBanTime()) {
                    // Ban süresi dolmuş, banı kaldır
                    banList.remove(banInfo);
                    saveBanList(banList);
                    return false;
                }
                return true; // Ban süresi hala geçerli
            }
        }
        return false; // Oyuncu banlı değil
    }

    public String getBanReason(String playerName) {
        List<BanInfo> banList = loadBanList();
        for (BanInfo banInfo : banList) {
            if (banInfo.getPlayer().equals(playerName)) {
                return banInfo.getReason();
            }
        }
        return null;
    }

    private List<BanInfo> loadBanList() {
        File file = new File(Main.getInstance().getDataFolder(), BAN_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<BanInfo>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Ban listesi dosyası okunamadı: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveBanList(List<BanInfo> banList) {
        try (FileWriter writer = new FileWriter(new File(Main.getInstance().getDataFolder(), BAN_FILE))) {
            gson.toJson(banList, writer);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Ban listesi dosyasına yazılamadı: " + e.getMessage());
        }
    }

    public void banPlayer(CommandSender sender, String playerName, int days, String reason) {
        long banTime = System.currentTimeMillis() + (days * 86400000L); // gün = 24 * 60 * 60 * 1000 milisaniye
        List<BanInfo> banList = loadBanList();
        banList.add(new BanInfo(playerName, banTime, reason));
        saveBanList(banList);
    }

    public void unbanPlayer(String playerName) {
        List<BanInfo> banList = loadBanList();
        banList.removeIf(banInfo -> banInfo.getPlayer().equals(playerName));
        saveBanList(banList);
    }

    private String formatBanDuration(int days) {
        if (days <= 0) return "süresiz";
        return days + " gün";
    }

    public static class BanInfo {
        private String player;
        private long banTime;
        private String reason;

        public BanInfo() {
        }

        public BanInfo(String player, long banTime, String reason) {
            this.player = player;
            this.banTime = banTime;
            this.reason = reason;
        }

        // Getter ve Setter metodlarını ekleyin
        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public long getBanTime() {
            return banTime;
        }

        public void setBanTime(long banTime) {
            this.banTime = banTime;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getFormattedBanTime() {
            long banDuration = (banTime - System.currentTimeMillis()) / 86400000L; // milisaniyeyi güne çevir
            if (banDuration <= 0) return "süresiz";
            return banDuration + " gün";
        }
    }
}
