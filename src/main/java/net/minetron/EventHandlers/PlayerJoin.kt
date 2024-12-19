package net.minetron.EventHandlers

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.level.Sound
import cn.nukkit.network.protocol.EntityEventPacket

class PlayerJoin : Listener {

    @EventHandler
    fun onPlayerJoin(joinEvent: PlayerJoinEvent) {

        val player: Player = joinEvent.player
        val playerName = player.name

        val level = player.level
        val position = player.position

        val entityEventPacket = EntityEventPacket()
        entityEventPacket.eid = player.id
        entityEventPacket.event = EntityEventPacket.GUARDIAN_ATTACK_ANIMATION
        player.dataPacket(entityEventPacket)

        level.addSound(position, Sound.RANDOM_TOTEM)

        val title = "§l§n§o§6Mine§ftron'a Hoşgeldin"
        val subtitle = "§a" + player.name
        player.sendTitle(title, subtitle, 10, 70, 20)
    }
}
