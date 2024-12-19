package net.minetron.Forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class BagPurchaseForm {

    public static final int FORM_ID = 1234;

    public static void openBagPurchaseForm(Player player) {
        FormWindowSimple form = new FormWindowSimple("Çanta Satın Alma", "10.000 Tronium karşılığında Çanta almak istiyormusunuz ?");
        form.addButton(new ElementButton(TextFormat.GREEN + "Evet"));
        form.addButton(new ElementButton(TextFormat.RED + "Hayır"));

        player.showFormWindow(form, FORM_ID);
    }

}
