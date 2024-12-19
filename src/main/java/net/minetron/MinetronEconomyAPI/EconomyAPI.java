package net.minetron.MinetronEconomyAPI;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.minetron.PrefixC;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EconomyAPI {

    private static volatile EconomyAPI instance;
    private final Config ecoconfig;
    private final PrefixC prefixC = new PrefixC();
    private final String prefix = prefixC.prefixString;
    private Map<String, Double> balances;

    private EconomyAPI() {
        File file = new File("plugins/Minetron/economy.json");
        this.ecoconfig = new Config(file, Config.JSON);
        this.balances = new HashMap<>();
        loadBalances();
    }

    public static EconomyAPI getInstance() {
        if (instance == null) {
            synchronized (EconomyAPI.class) {
                if (instance == null) {
                    instance = new EconomyAPI();
                }
            }
        }
        return instance;
    }

    private void loadBalances() {
        try {
            if (ecoconfig.exists("troniums")) {
                balances = (Map<String, Double>) ecoconfig.get("troniums");
            } else {
                balances = new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBalances() {
        try {
            ecoconfig.set("troniums", balances);
            ecoconfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> getBalances() {
        return new HashMap<>(balances);
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getName(), 0.0);
    }

    public void setBalance(Player player, double amount) {
        balances.put(player.getName(), amount);
        saveBalances();
    }

    public void addBalance(Player player, double amount) {
        double balance = getBalance(player);
        setBalance(player, balance + amount);
        logTransaction(player.getName(), "ekleme", amount);
    }

    public void subtractBalance(Player player, double amount) {
        double balance = getBalance(player);
        if (balance >= amount) {
            setBalance(player, balance - amount);
            logTransaction(player.getName(), "çıkarma", amount);
        } else {
            player.sendMessage(TextFormat.RED + "Yetersiz bakiye!");
        }
    }

    public void sendBalance(Player from, Player to, double amount) {
        double fromBalance = getBalance(from);
        if (fromBalance >= amount) {
            subtractBalance(from, amount);
            addBalance(to, amount);
            logTransaction(from.getName(), "gönderim", amount);
            to.sendMessage(prefix + TextFormat.GREEN + from.getName() + " size " + amount + "tronium gönderdi!");
        } else {
            from.sendMessage(TextFormat.RED + "Yetersiz bakiye!");
        }
    }

    private void logTransaction(String playerName, String type, double amount) {
        System.out.println(prefix + TextFormat.GRAY + " [" + playerName + "] " + type + ": " + amount);
    }
}
