package net.minetron.Entities;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.behavior.Behavior;
import cn.nukkit.entity.ai.behaviorgroup.BehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.LookController;
import cn.nukkit.entity.ai.controller.WalkController;
import cn.nukkit.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import cn.nukkit.entity.ai.executor.FlatRandomRoamExecutor;
import cn.nukkit.entity.ai.executor.MeleeAttackExecutor;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import cn.nukkit.entity.ai.route.posevaluator.WalkingPosEvaluator;
import cn.nukkit.entity.ai.sensor.NearestPlayerSensor;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.minetron.Items.TronIngot;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TronKeeper extends EntityIntelligent implements CustomEntity {

    public TronKeeper(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setNameTag(TextFormat.RED + "Tron Bekçisi" + TextFormat.WHITE);
    }


    @Override
    public @NotNull String getIdentifier() {
        return "minetron:tron_keeper";
    }


    @Override
    public boolean attack(EntityDamageEvent source) {
        if (super.attack(source)) {
            if (this.getHealth() <= 0) {
                this.dropCustomLoot();
            }
            return true;
        }
        return false;
    }

    private void dropCustomLoot() {
        // İstediğiniz eşyanın oluşturulması ve düşürülmesi
        Item customItem = new TronIngot(); // Örneğin, 1 adet TronIngot düşür
        this.getLevel().dropItem(this.getLocation(), customItem); // Düşürülecek item'ın konumu entity'nin konumu
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(20);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return new BehaviorGroup(
                this.tickSpread,
                Set.of(),
                Set.of(
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 10), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                entity -> !entity.getMemoryStorage().notEmpty(CoreMemoryTypes.ATTACK_TARGET) || !(entity.getMemoryStorage().get(CoreMemoryTypes.ATTACK_TARGET) instanceof Player player) || player.isSurvival()
                        ), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 10), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                entity -> {
                                    if (entity.getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_PLAYER))
                                        return true;
                                    Player player = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
                                    return player.isSurvival();
                                }
                        ), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), (entity -> true), 1, 1)
                ),
                Set.of(new NearestPlayerSensor(40, 0, 20)),
                Set.of(new WalkController(), new LookController(true, true)),
                new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this),
                this
        );
    }
}
