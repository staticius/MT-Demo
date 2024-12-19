package net.minetron.Utils;

import cn.nukkit.Player;

import java.util.HashMap;
import java.util.Map;

public class TpaUtils {

    private static final Map<Player, Player> pendingRequests = new HashMap<>();

    // Teleport isteği gönder
    public static void sendRequest(Player from, Player to) {
        pendingRequests.put(to, from);
    }

    // Oyuncunun bekleyen bir isteği olup olmadığını kontrol et
    public static boolean hasPendingRequest(Player player) {
        return pendingRequests.containsKey(player);
    }

    // Bekleyen isteği temizle
    public static void clearRequest(Player player) {
        pendingRequests.remove(player);
    }

    // Oyuncunun kimden isteği olduğunu al
    public static Player getRequester(Player player) {
        return pendingRequests.get(player);
    }

}
