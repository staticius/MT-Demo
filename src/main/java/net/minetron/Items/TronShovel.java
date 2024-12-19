package net.minetron.Items;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;

public class TronShovel extends ItemCustomTool {

    public TronShovel() {
        super("minetron:tron_shovel");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this)
                .speed(12)
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .creativeGroup(CreativeGroup.SHOVEL)
                .texture("tron_shovel")
                .name("Tron Kürek")
                .allowOffHand(false)
                .handEquipped(true)
                .build();

    }

    @Override
    public boolean isShovel() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return 1800;
    }

    @Override
    public int getAttackDamage() {
        return 5;
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
