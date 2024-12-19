package net.minetron.Armors;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronLeggings extends ItemCustomArmor {
    public TronLeggings() {
        super("minetron:tron_leggings");
    }


    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition.
                armorBuilder(this)
                .name("Tron Pantolon")
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.LEGGINGS)
                .handEquipped(true)
                .allowOffHand(false)
                .texture("tron_leggings")
                .build();
    }

    @Override
    public boolean isLeggings() {
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
