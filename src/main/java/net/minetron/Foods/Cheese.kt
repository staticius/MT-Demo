package net.minetron.Foods

import cn.nukkit.Player
import cn.nukkit.item.customitem.CustomItemDefinition
import cn.nukkit.item.customitem.ItemCustomFood
import cn.nukkit.item.customitem.data.CreativeCategory
import cn.nukkit.item.customitem.data.CreativeGroup
import org.jetbrains.annotations.NotNull

class Cheese(id: @NotNull String) : ItemCustomFood(id) {

    override fun getDefinition(): CustomItemDefinition {
        return CustomItemDefinition
            .customBuilder(this)
            .texture("texture_cheese")
            .creativeGroup(CreativeGroup.COOKED_FOOD)
            .creativeCategory(CreativeCategory.ITEMS)
            .name("Peynir")
            .handEquipped(true)
            .build()
    }

    override fun onEaten(player: Player?): Boolean {
        super.onEaten(player)
        return true
    }


}