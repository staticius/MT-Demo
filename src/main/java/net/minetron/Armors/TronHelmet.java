package net.minetron.Armors;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronHelmet extends ItemCustomArmor {
    public TronHelmet() {
        super("minetron:tron_helmet");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition.
                armorBuilder(this)
                .name("Tron Kask")
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.HELMET)
                .handEquipped(true)
                .allowOffHand(false)
                .texture("tron_helmet")
                .build();
    }

    @Override
    public boolean isHelmet() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return 300;
    }

    @Override
    public int getTier() {
        return ItemCustomArmor.TIER_NETHERITE;
    }

    @Override
    public int getEnchantAbility() {
        return 20;
    }

    @Override
    public int getArmorPoints() {
        return 50;
    }
}
