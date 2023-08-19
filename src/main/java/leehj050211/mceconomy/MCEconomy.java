package leehj050211.mceconomy;

import leehj050211.mceconomy.command.CommandManager;
import leehj050211.mceconomy.event.EventManager;
import leehj050211.mceconomy.global.task.SaveDataTask;
import leehj050211.mceconomy.global.task.StatusWindowTask;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEconomy extends JavaPlugin {

    private static MCEconomy instance;

    public static MCEconomy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        EventManager.registerEvents();
        CommandManager.registerCommands();

        SaveDataTask.getInstance().start();
        StatusWindowTask.getInstance().start();
    }

    @Override
    public void onDisable() {
        SaveDataTask.getInstance().stop();
        SaveDataTask.getInstance().execute();
        StatusWindowTask.getInstance().stop();
    }
}
