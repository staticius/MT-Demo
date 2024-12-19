package net.minetron.Managers

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.level.Sound
import net.minetron.Armors.TronBoots
import net.minetron.Armors.TronChestplate
import net.minetron.Armors.TronHelmet
import net.minetron.Armors.TronLeggings
import net.minetron.Items.TronAxe
import net.minetron.Items.TronPickaxe
import net.minetron.Items.TronShovel
import net.minetron.Items.TronSword
import net.minetron.MinetronEconomyAPI.EconomyAPI
import net.minetron.PrefixC

class RepairManager(private val economyAPI: EconomyAPI) {


    //TRON ITEMS
    private val tronAxe = TronAxe()
    private val tronSword = TronSword()
    private val tronShovel = TronShovel()
    private val tronPickaxe = TronPickaxe()
    private val tronHelmet = TronHelmet()
    private val tronChestplate = TronChestplate()
    private val tronLeggings = TronLeggings()
    private val tronBoots = TronBoots()

    //REPAIR COST & PREFIX
    private val prefixC = PrefixC().prefixString
    private val repairCost = 200.0 // Tamir maliyeti

    //SOUNDS
    private val tamirsound = Sound.RANDOM_ANVIL_USE
    private val basarisiz = Sound.MOB_VILLAGER_NO


    fun repairItem(player: Player): Boolean {
        val item = player.inventory.itemInHand

        // Eğer item null ise veya item bir ekipman değilse (örneğin, bir kılıç, kazma vs.) ve eşyayı tamir edilebilecekse kontrol et
        if (item == null || !canBeRepaired(item)) {
            player.level.addSound(player.position, basarisiz)
            player.sendActionBar(prefixC + "Elinizde tamir edilebilecek bir ekipman yok!")
            return false
        }

        // Eğer eşyanın durumu tamamen doluysa, oyuncuya mesaj gönder ve para kesme
        if (item.damage == 0) {
            player.level.addSound(player.position, basarisiz)
            player.sendActionBar(prefixC + "Elinizdeki eşya daha önce kullanılmamış.")
            return false
        }

        // Oyuncunun bakiyesini kontrol et
        val balance = economyAPI.getBalance(player)
        if (balance < repairCost) {
            player.level.addSound(player.position, basarisiz)
            player.sendActionBar(prefixC + "Tamir için $repairCost Tronium gerekli.")
            return false
        }

        // Bakiyeden maliyeti düş
        economyAPI.subtractBalance(player, repairCost)

        // Eşyayı tamir et
        item.damage = 0 // Eşyayı tamamen tamir et (damage özelliğini sıfırla)
        player.inventory.setItemInHand(item)

        player.sendActionBar(prefixC + "Eşyanız başarıyla tamir edildi!")
        player.level.addSound(player.position, tamirsound)

        return true
    }

    private fun canBeRepaired(item: Item): Boolean {
        // Burada hangi eşyaların tamir edilebileceğini belirleyin
        val repairableItems = arrayOf(

            tronHelmet.id,
            tronChestplate.id,
            tronLeggings.id,
            tronBoots.id,
            tronSword.id,
            tronAxe.id,
            tronShovel.id,
            tronPickaxe.id,
            Item.ELYTRA,
            Item.SHIELD,
            Item.TRIDENT,
            Item.FISHING_ROD,
            Item.BOW,
            Item.CROSSBOW,
            Item.MACE,
            Item.FLINT_AND_STEEL,
            Item.WARPED_FUNGUS_ON_A_STICK,
            Item.CARROT_ON_A_STICK,
            Item.SHEARS,
            Item.WOODEN_SWORD,
            Item.WOODEN_AXE,
            Item.WOODEN_SHOVEL,
            Item.WOODEN_PICKAXE,
            Item.WOODEN_HOE,
            Item.STONE_SWORD,
            Item.STONE_AXE,
            Item.STONE_SHOVEL,
            Item.STONE_PICKAXE,
            Item.IRON_SWORD,
            Item.IRON_AXE,
            Item.IRON_SHOVEL,
            Item.IRON_PICKAXE,
            Item.IRON_HOE,
            Item.DIAMOND_SWORD,
            Item.DIAMOND_AXE,
            Item.DIAMOND_SHOVEL,
            Item.DIAMOND_PICKAXE,
            Item.DIAMOND_HOE,
            Item.NETHERITE_SWORD,
            Item.NETHERITE_AXE,
            Item.NETHERITE_SHOVEL,
            Item.NETHERITE_PICKAXE,
            Item.NETHERITE_HOE,
            Item.GOLDEN_SWORD,
            Item.GOLDEN_AXE,
            Item.GOLDEN_SHOVEL,
            Item.GOLDEN_PICKAXE,
            Item.GOLDEN_HOE,
            Item.LEATHER_HELMET,
            Item.LEATHER_CHESTPLATE,
            Item.LEATHER_LEGGINGS,
            Item.LEATHER_BOOTS,
            Item.IRON_HELMET,
            Item.IRON_CHESTPLATE,
            Item.IRON_LEGGINGS,
            Item.IRON_BOOTS,
            Item.DIAMOND_HELMET,
            Item.DIAMOND_CHESTPLATE,
            Item.DIAMOND_LEGGINGS,
            Item.DIAMOND_BOOTS,
            Item.GOLDEN_HELMET,
            Item.GOLDEN_CHESTPLATE,
            Item.GOLDEN_LEGGINGS,
            Item.GOLDEN_BOOTS,
            Item.CHAINMAIL_HELMET,
            Item.CHAINMAIL_CHESTPLATE,
            Item.CHAINMAIL_LEGGINGS,
            Item.CHAINMAIL_BOOTS,
            Item.NETHERITE_HELMET,
            Item.NETHERITE_CHESTPLATE,
            Item.NETHERITE_LEGGINGS,
            Item.NETHERITE_BOOTS,
            Item.BRUSH
        )
        return item.id in repairableItems
    }
}
