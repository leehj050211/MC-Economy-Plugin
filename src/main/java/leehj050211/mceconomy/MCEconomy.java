package leehj050211.mceconomy;

import leehj050211.mceconomy.command.money.SendMoneyCommand;
import leehj050211.mceconomy.event.job.JobClickListener;
import leehj050211.mceconomy.event.player.PlayerJoinListener;
import leehj050211.mceconomy.global.exception.CommandExceptionHandler;
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
        getServer().getPluginManager().registerEvents(new JobClickListener(), this);
        getCommand("송금").setExecutor(new CommandExceptionHandler(new SendMoneyCommand()));
    }

    @Override
    public void onDisable() {
    }
}
