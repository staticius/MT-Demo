package net.minetron.EventHandlers;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseSimple;
import net.minetron.Forms.BagPurchaseForm;
import net.minetron.Managers.BagConfigManager;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.PrefixC;

public class BagPurchaseHandler implements Listener {

    PrefixC prefixC = new PrefixC();
    String prefix = prefixC.prefixString;

    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getFormID() == BagPurchaseForm.FORM_ID) { // Form ID'sini kontrol et
            Player player = event.getPlayer();
            Object response = event.getResponse();

            if (response instanceof FormResponseSimple) {
                FormResponseSimple formResponse = (FormResponseSimple) response;
                int responseId = formResponse.getClickedButtonId();


                if (responseId == 0) { // Evet seçeneği
                    double balance = EconomyAPI.getInstance().getBalance(player);
                    if (balance >= 10000) {
                        EconomyAPI.getInstance().subtractBalance(player, 10000);
                        player.sendMessage(prefix + "Çanta başarıyla satın alındı!");
                        BagConfigManager.grantBag(player.getName()); // Çanta erişimini verin
                    } else {
                        player.sendMessage(prefix + "Yetersiz bakiye!");
                    }
                } else if (responseId == 1) { // Hayır seçeneği
                    player.sendMessage(prefix + "Çanta alım işlemi iptal edildi.");
                }
            }
        }
    }

}
