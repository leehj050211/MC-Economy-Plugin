package leehj050211.mceconomy;

import leehj050211.mceconomy.command.money.SendMoneyCommand;
import leehj050211.mceconomy.event.job.JobClickListener;
import leehj050211.mceconomy.event.player.PlayerJoinListener;
import leehj050211.mceconomy.event.player.PlayerKickListener;
import leehj050211.mceconomy.event.player.PlayerQuitListener;
import leehj050211.mceconomy.global.exception.CommandExceptionHandler;
import leehj050211.mceconomy.global.task.SaveDataTask;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEconomy extends JavaPlugin {

    private static MCEconomy instance;

    public static MCEconomy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerKickListener(), this);
        getServer().getPluginManager().registerEvents(new JobClickListener(), this);
        getCommand("송금").setExecutor(new CommandExceptionHandler(new SendMoneyCommand()));

        SaveDataTask.getInstance().start();
    }

    @Override
    public void onDisable() {
        SaveDataTask.getInstance().execute();
    }
}
