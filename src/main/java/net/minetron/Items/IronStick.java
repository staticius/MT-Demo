package net.minetron.Items;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.CreativeCategory;

public class IronStick extends ItemCustom {
    public IronStick() {
        super("minetron:iron_stick");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .customBuilder(this)
                .texture("iron_stick")
                .name("Demir Sap")
                .allowOffHand(false)
                .texture("iron_stick")
                .creativeCategory(CreativeCategory.ITEMS)
                .build();
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }
}
