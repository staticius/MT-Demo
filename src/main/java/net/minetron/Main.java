package net.minetron;

import cn.nukkit.block.BlockIronBlock;
import cn.nukkit.item.*;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.recipe.ShapedRecipe;
import cn.nukkit.registry.EntityRegistry;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import cn.nukkit.scheduler.TaskHandler;
import net.minetron.Armors.TronBoots;
import net.minetron.Armors.TronChestplate;
import net.minetron.Armors.TronHelmet;
import net.minetron.Armors.TronLeggings;
import net.minetron.Commands.*;
import net.minetron.Commands.TpaCommand;
import net.minetron.Entities.TronKeeper;
import net.minetron.EventHandlers.BagPurchaseHandler;
import net.minetron.EventHandlers.CloseHandler;
import net.minetron.EventHandlers.PlayerJoin;
import net.minetron.EventHandlers.TpaHandler;
import net.minetron.Forms.WarpForm;
import net.minetron.Items.*;
import net.minetron.Managers.BagConfigManager;
import net.minetron.Managers.BanDataReader;
import net.minetron.Managers.BanManager;
import net.minetron.Managers.RepairManager;
import net.minetron.MinetronEconomyAPI.EcoCommands.*;
import net.minetron.MinetronEconomyAPI.EconomyAPI;
import net.minetron.Tasks.TronKeeperSpawnTask;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends PluginBase {

    private static Main instance;
    private TaskHandler taskHandler;
    private RepairManager repairManager;
    public static final int TPA_FORM_ID = 12345;


    @Contract(pure = true)
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {

        try {
            Registries.ITEM.registerCustomItem
                    (
                            this,
                            TronIngot.class,
                            TronPickaxe.class,
                            IronStick.class,
                            TronSword.class,
                            TronShovel.class,
                            TronAxe.class,
                            TronHelmet.class,
                            TronChestplate.class,
                            TronLeggings.class,
                            TronBoots.class
                    );

        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onEnable() {
        instance = this;

        initializeEconomy();
        initializeConfig();
        initializeEntityRegistry();
        new BanDataReader().loadBanFile(this);
        scheduleTasks();
        registerRecipes();
        registerEvents();
        registerCommands();


        getLogger().info("Minetron Sistemleri Aktif");
    }

    private void registerRecipes() {
        try {
            Registries.RECIPE.register("minetron:tron_pickaxe", createTronPickaxeRecipe());
            Registries.RECIPE.register("minetron:tron_sword", createTronSwordRecipe());
            Registries.RECIPE.register("minetron:iron_stick", createIronStickRecipe());
            Registries.RECIPE.register("minetron:tron_shovel", createTronShovelRecipe());
            Registries.RECIPE.register("minetron:tron_axe", createTronAxeRecipe());
            Registries.RECIPE.register("minetron:tron_ingot", createTronIngotRecipe());

            getServer().getRecipeRegistry().rebuildPacket();
        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createTronPickaxeRecipe() {
        return new ShapedRecipe(createTronPickaxe(), new String[]{"AAA", "CBC", " B "}, createTronEquipments(), List.of());
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createTronSwordRecipe() {
        return new ShapedRecipe(createTronSword(), new String[]{" A ", "CAC", " B "}, createTronEquipments(), List.of());
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createIronStickRecipe() {
        return new ShapedRecipe(createIronStick(), new String[]{" F ", "FEF", " E "}, createIronRod(), List.of());
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createTronShovelRecipe() {
        return new ShapedRecipe(createTronShovel(), new String[]{"CAC", " B ", " B "}, createTronEquipments(), List.of());
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createTronAxeRecipe() {
        return new ShapedRecipe(createTronAxe(), new String[]{"CAA", " BA", " B "}, createTronEquipments(), List.of());
    }

    @Contract(" -> new")
    private @NotNull ShapedRecipe createTronIngotRecipe() {
        return new ShapedRecipe(createTronIngot(), new String[]{" X ", "NGN", " Z "}, createTronKulce(), List.of());
    }

    private @NotNull Map<Character, Item> createTronEquipments() {
        Map<Character, Item> tronEquipments = new HashMap<>();
        tronEquipments.put('A', createTronIngot());
        tronEquipments.put('B', createIronStick());
        tronEquipments.put('C', createDiamond());
        return tronEquipments;
    }

    private @NotNull Map<Character, Item> createIronRod() {
        Map<Character, Item> ironRod = new HashMap<>();
        ironRod.put('F', createIronIngot());
        ironRod.put('E', createIronBlock());
        return ironRod;
    }

    private @NotNull Map<Character, Item> createTronKulce() {
        Map<Character, Item> tronKulce = new HashMap<>();
        tronKulce.put('N', createNetheriteScrap());
        tronKulce.put('G', createGoldIngot());
        tronKulce.put('Z', createDiamond());
        tronKulce.put('X', createIronIngot());
        return tronKulce;
    }

    @Contract(" -> new")
    private @NotNull Item createTronIngot() {
        return new TronIngot();
    }

    @Contract(" -> new")
    private @NotNull Item createIronStick() {
        return new IronStick();
    }

    @Contract(" -> new")
    private @NotNull Item createIronBlock() {
        return new ItemBlock(new BlockIronBlock());
    }

    @Contract(" -> new")
    private @NotNull Item createIronIngot() {
        return new ItemIronIngot();
    }

    @Contract(" -> new")
    private @NotNull Item createDiamond() {
        return new ItemDiamond();
    }

    @Contract(" -> new")
    private @NotNull Item createGoldIngot() {
        return new ItemGoldIngot();
    }

    @Contract(" -> new")
    private @NotNull Item createNetheriteScrap() {
        return new ItemNetheriteScrap();
    }

    @Contract(" -> new")
    private @NotNull Item createTronSword() {
        return new TronSword();
    }

    @Contract(" -> new")
    private @NotNull Item createTronShovel() {
        return new TronShovel();
    }

    @Contract(" -> new")
    private @NotNull Item createTronAxe() {
        return new TronAxe();
    }

    @Contract(" -> new")
    private @NotNull Item createTronPickaxe() {
        return new TronPickaxe();
    }

    private void scheduleTasks() {
        taskHandler = getServer().getScheduler().scheduleRepeatingTask(
                new TronKeeperSpawnTask(getServer().getLevelByName("cave")),
                20 * 60 * 10
        );
    }

    private void initializeEntityRegistry() {
        try {
            Registries.ENTITY.registerCustomEntity(
                    this,
                    new EntityRegistry.CustomEntityDefinition("minetron:tron_keeper", "45", false, true),
                    TronKeeper.class
            );
        } catch (RegisterException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeEconomy() {
        EconomyAPI economyAPI = EconomyAPI.getInstance();
        repairManager = new RepairManager(economyAPI);
    }

    private void initializeConfig() {
        BagConfigManager.loadConfig(this);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new WarpForm(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new CloseHandler(), this);
        getServer().getPluginManager().registerEvents(new BagPurchaseHandler(), this);
        getServer().getPluginManager().registerEvents(new TpaHandler(this), this);

    }

    private void registerCommands() {
        this.getServer().getCommandMap().register("çanta", new BagCommand());
        this.getServer().getCommandMap().register("warp", new WarpCommand());
        this.getServer().getCommandMap().register("bakiyem", new MyMoneyCommand());
        this.getServer().getCommandMap().register("bakiyebak", new SeeMoneyCommand());
        this.getServer().getCommandMap().register("bakiyegönder", new SendMoneyCommand());
        this.getServer().getCommandMap().register("bakiyeayarla", new SetMoneyCommand());
        this.getServer().getCommandMap().register("bakiyesil", new ReduceMoneyCommand());
        this.getServer().getCommandMap().register("topmoney", new TopMoneyCommand());
        this.getServer().getCommandMap().register("tamir", new RepairCommand(repairManager));
        this.getServer().getCommandMap().register("mtban", new BanCommand("mtban", new BanManager()));
        this.getServer().getCommandMap().register("tpa", new TpaCommand(this));
    }

    @Override
    public void onDisable() {
        if (taskHandler != null) {
            taskHandler.cancel();
        }

        getLogger().info("Minetron Sistemleri Kapatıldı");
    }

}