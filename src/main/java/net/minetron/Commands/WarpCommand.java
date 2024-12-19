package net.minetron.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.window.FormWindowCustom;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand extends Command implements Listener {
    public WarpCommand() {
        super("warp", "Minetron Bölgelerini Görebilirsin (Duyduğuma göre çok farklı dünyalar varmış).", "/warp");
    }


    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komutlar yanlızca oyun içinde kullanılabilir.");
            return false;
        }

        FormWindowCustom formWindowCustom = new FormWindowCustom("Bölgeler");
        List<String> warpList = new ArrayList<>();
        warpList.add("Orman");
        warpList.add("Void");
        warpList.add("Derin Mağara");
        formWindowCustom.addElement(new ElementDropdown("Bölge Seç", warpList));
        player.showFormWindow(formWindowCustom);
        return true;

    }
}
