package net.minetron.Items;

import cn.nukkit.item.ItemDiamondSword;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;
import net.minetron.Main;

public class TronSword extends ItemCustomTool {

    Main main = new Main();


    public TronSword() {
        super("minetron:tron_sword");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this)
                .creativeCategory(CreativeCategory.EQUIPMENT)
                .texture("tron_sword")
                .name("Tron Kılıç")
                .allowOffHand(false)
                .texture("tron_sword")
                .creativeGroup(CreativeGroup.SWORD)
                .handEquipped(true)
                .build();
    }

    @Override
    public boolean isSword() {
        return true;
    }

    @Override
    public int getAttackDamage() {
        return 8;
    }

    @Override
    public int getEnchantAbility() {
        return 20;
    }

    @Override
    public int getTier() {
        return ItemDiamondSword.TIER_NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 1825;
    }

}

