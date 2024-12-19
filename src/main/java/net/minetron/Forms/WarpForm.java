package net.minetron.Forms;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.Level;
import net.minetron.PrefixC;

public class WarpForm implements Listener {

    PrefixC prefix = new PrefixC();


    @EventHandler
    public void onFormRespond(PlayerFormRespondedEvent event) {

        Player player = event.getPlayer();
        if (event.getWindow().wasClosed()) return;
        if (event.getWindow() instanceof FormWindowCustom) {
            FormWindowCustom formWindow = (FormWindowCustom) event.getWindow();
            if (formWindow.getTitle().equals("Bölgeler")) {
                int selectedOption = formWindow.getResponse().getDropdownResponse(0).getElementID();

                String worldName = "";
                switch (selectedOption) {
                    case 0:
                        worldName = "orman";
                        break;
                    case 1:
                        worldName = "void";
                        break;
                    case 2:
                        worldName = "cave";
                        break;
                }

                Level level = player.getServer().getLevelByName(worldName);

                if (level != null) {
                    player.teleport(level.getSpawnLocation());
                    player.sendActionBar(prefix.prefixString + worldName + " adlı dünyaya ışınlandın.");
                } else {
                    player.sendActionBar(prefix.prefixString + "Seçtiğiniz dünya bulunamadı.");
                }


            }
        }

    }


}
