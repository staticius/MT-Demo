package net.minetron.Commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.level.Sound
import net.minetron.Managers.RepairManager
import net.minetron.PrefixC

class RepairCommand(private val repairManager: RepairManager) :
    Command("tamir", "200 Tronium karşılığında kırılmak üzere olan eşyalarını tamir et!", "/tamir") {

    init {
        requireNotNull(repairManager) { "RepairManager Null Döndürülemez." }
    }


    val prefixC = PrefixC().prefixString
    val basarisiz = Sound.MOB_VILLAGER_NO

    init {
        this.description = "200 Tronium karşılığında elinizdeki eşyayı tamir eder."
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val player = sender
            val success = repairManager.repairItem(player)
            if (!success) {
                player.level.addSound(player.position, basarisiz)
                player.sendActionBar(prefixC + "Eşyanın tamir edilebilir olduğundan emin olun ve yeterli Tronium'a sahip olun.")
            }
        } else {
            sender.sendMessage("Bu komutu sadece oyuncular kullanabilir.")
        }
        return true
    }

}