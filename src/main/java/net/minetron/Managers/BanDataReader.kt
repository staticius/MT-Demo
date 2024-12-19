package net.minetron.Managers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.minetron.Main
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

class BanDataReader {

    private val gson = Gson()
    private val banListType: Type = object : TypeToken<List<BanManager.BanInfo>>() {}.type

    fun loadBanFile(plugin: Main) {
        val banFile = File(plugin.dataFolder, "yasaklananlar.json")
        if (!banFile.exists()) {
            banFile.createNewFile()
            // Boş dosya oluşturulduğunda bir JSON array olarak initialize edelim
            banFile.writeText("[]")
        }

        try {
            val reader = FileReader(banFile)
            val banList: List<BanManager.BanInfo> = gson.fromJson(reader, banListType)

            for (banInfo in banList) {
                val playerName = banInfo.player
                val banTime = banInfo.banTime
                val reason = banInfo.reason

                if (banTime > System.currentTimeMillis()) {
                    plugin.server.nameBans.addBan(
                        playerName,
                        "Minetron'dan yasaklandın!: Sebep: $reason",
                        Date(banTime),
                        null
                    )
                }

            }
        } catch (e: IOException) {
            plugin.logger.warning("Ban dosyası okunamadı: ${e.message}")
        }
    }
}
