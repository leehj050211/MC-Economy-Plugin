package leehj050211.mceconomy.event;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.event.job.JobClickListener;
import leehj050211.mceconomy.event.player.PlayerJoinListener;
import leehj050211.mceconomy.event.player.PlayerKickListener;
import leehj050211.mceconomy.event.player.PlayerQuitListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public static void registerEvents() {
        Plugin plugin = MCEconomy.getInstance();
        if (plugin == null) {
            return;
        }
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        Listener[] listeners = {
                new PlayerJoinListener(),
                new PlayerQuitListener(),
                new PlayerKickListener(),
                new JobClickListener()
        };

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
