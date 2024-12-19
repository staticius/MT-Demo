package net.minetron.EventHandlers;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowCustom;
import net.minetron.Main;
import net.minetron.Utils.TpaUtils;

public class TpaHandler implements Listener {

    private final Main plugin;

    public TpaHandler(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getFormID() != Main.TPA_FORM_ID) {
            return; // Farklı bir form ise işlem yapma
        }

        if (event.wasClosed()) {
            return; // Form kapatıldıysa işlem yapma
        }

        Player player = event.getPlayer();
        FormWindowCustom form = (FormWindowCustom) event.getWindow();

        String selectedPlayerName = form.getResponse().getDropdownResponse(0).getElementContent();
        Player targetPlayer = this.plugin.getServer().getPlayer(selectedPlayerName);

        if (targetPlayer != null) {
            TpaUtils.sendRequest(player, targetPlayer);
            player.sendMessage(targetPlayer.getName() + " adlı oyuncuya ışınlanma isteği gönderildi.");
            targetPlayer.sendMessage(player.getName() + " adlı oyuncu sana ışınlanma isteği gönderdi: /tpa [kabul & red].");
        } else {
            player.sendMessage("Oyuncu Bulunamadı.");
        }
    }

}
