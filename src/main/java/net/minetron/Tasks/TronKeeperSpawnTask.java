package net.minetron.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import net.minetron.Entities.TronKeeper;

import java.util.Random;

public class TronKeeperSpawnTask extends Task {
    private final Level level;
    private final Random random = new Random();

    public TronKeeperSpawnTask(Level level) {
        this.level = level;
    }

    @Override
    public void onRun(int currentTick) {
        Position spawnPosition;
        boolean validPositionFound = false;

        int maxAttempts = 10;
        int attempts = 0;

        while (!validPositionFound && attempts < maxAttempts) {
            int x = random.nextInt(1000) - 500; // -500 ile 500 arasında
            int y = random.nextInt(384) - 64; // -64 ile 319 arasında
            int z = random.nextInt(1000) - 500; // -500 ile 500 arasında

            spawnPosition = new Position(x, y, z, level);

            if (isValidPosition(spawnPosition)) {
                validPositionFound = true;

                IChunk chunk = level.getChunk(spawnPosition.getChunkX(), spawnPosition.getChunkZ());
                CompoundTag tag = new CompoundTag();

                TronKeeper tronKeeper = new TronKeeper(chunk, tag);
                tronKeeper.spawnToAll();

                for (Player player : level.getPlayers().values()) {
                    player.sendMessage(TextFormat.GREEN + "Tron Bekçisi Spawnlandı: " + x + ", " + y + ", " + z);
                }
            } else {
                attempts++;
            }
        }

        if (!validPositionFound) {
            System.out.println("Tron Bekçisi Spawnlanamadı: GEÇERSİZ POZİSYON");
        }
    }

    private boolean isValidPosition(Position position) {
        Level level = position.getLevel();

        // Pozisyonun geçerli yükseklikte olduğunu kontrol et
        if (position.getY() < 0 || position.getY() > level.getMaxHeight()) {
            return false;
        }

        Block aboveBlock = level.getBlock(position.add(0, 1, 0));
        Block belowBlock = level.getBlock(position.add(0, -1, 0));

        // Hava ve zemin kontrolü string ID karşılaştırmaları ile yapılır
        String airId = "minecraft:air"; // Hava ID'si
        String belowBlockId = belowBlock.getId();

        return airId.equals(aboveBlock.getId()) && !airId.equals(belowBlockId);
    }

}
