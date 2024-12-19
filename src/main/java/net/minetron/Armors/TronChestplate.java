package net.minetron.Armors;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronChestplate extends ItemCustomArmor {

    public TronChestplate() {
        super("minetron:tron_chestplate");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition.
                armorBuilder(this)
                .name("Tron Göğüslük")
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.CHESTPLATE)
                .handEquipped(true)
                .allowOffHand(false)
                .texture("tron_chestplate")
                .build();
    }

    @Override
    public boolean isChestplate() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return 300;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_NETHERITE;
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
