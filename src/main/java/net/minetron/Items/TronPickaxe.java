package net.minetron.Items;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronPickaxe extends ItemCustomTool {
    public TronPickaxe() {
        super("minetron:tron_pickaxe");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this)
                .speed(12)
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .texture("tron_pickaxe")
                .name("Tron Kazma")
                .allowOffHand(false)
                .handEquipped(true)
                .creativeGroup(CreativeGroup.PICKAXE)
                .build();
    }


    @Override
    public int getMaxDurability() {
        return 1800;
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

    @Override
    public int getAttackDamage() {
        return 6;
    }

    @Override
    public int getEnchantAbility() {
        return 20;
    }

    @Override
    public int getTier() {
        return ItemCustomTool.TIER_DIAMOND;
    }


}
