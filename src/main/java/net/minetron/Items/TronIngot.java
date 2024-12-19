package net.minetron.Items;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.CreativeCategory;

public class TronIngot extends ItemCustom {
    public TronIngot() {
        super("minetron:tron_ingot");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .customBuilder(this)
                .creativeCategory(CreativeCategory.ITEMS)
                .texture("tron_ingot")
                .name("Tron Külçesi")
                .allowOffHand(false)
                .build();
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }


}
