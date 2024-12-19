package net.minetron.Items;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronAxe extends ItemCustomTool {
    public TronAxe() {
        super("minetron:tron_axe");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this)
                .speed(12)
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.AXE)
                .texture("tron_axe")
                .name("Tron Balta")
                .allowOffHand(false)
                .handEquipped(true)
                .build();
    }


    @Override
    public boolean isAxe() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return 1800;
    }

    @Override
    public int getAttackDamage() {
        return 7;
    }

    @Override
    public int getEnchantAbility() {
        return 20;
    }

    @Override
    public int getTier() {
        return ItemCustomTool.TIER_NETHERITE;
    }
}
