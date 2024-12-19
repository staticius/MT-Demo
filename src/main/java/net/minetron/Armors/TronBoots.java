package net.minetron.Armors;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronBoots extends ItemCustomArmor {
    public TronBoots() {
        super("minetron:tron_boots");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this)
                .name("Tron Bot")
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.BOOTS)
                .handEquipped(true)
                .allowOffHand(false)
                .texture("tron_boots")
                .build();
    }


    @Override
    public boolean isBoots() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return 660;
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
        return 100;
    }
}
