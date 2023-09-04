package leehj050211.mceconomy;

import leehj050211.mceconomy.command.CommandManager;
import leehj050211.mceconomy.event.EventManager;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.task.DisplayEstateTask;
import leehj050211.mceconomy.global.task.SaveDataTask;
import leehj050211.mceconomy.global.task.StatusWindowTask;
import leehj050211.mceconomy.global.world.WorldManager;
import leehj050211.mceconomy.gui.MenuProvider;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEconomy extends JavaPlugin {

    private static MCEconomy instance;

    public static MCEconomy getInstance() {
        return instance;
    }

    private final PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getOnlinePlayers().forEach(playerManager::addPlayer);

        new InventoryAPI(this).init();
        MenuProvider.getInstance();
        ShopManager.getInstance();
        WorldManager.getInstance();

        EventManager.registerEvents();
        CommandManager.registerCommands();

        SaveDataTask.getInstance().start();
        StatusWindowTask.getInstance().start();
        DisplayEstateTask.getInstance().start();
    }

    @Override
    public void onDisable() {
        SaveDataTask.getInstance().stop();
        SaveDataTask.getInstance().execute();
        StatusWindowTask.getInstance().stop();
        DisplayEstateTask.getInstance().stop();
    }
}
