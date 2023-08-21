package leehj050211.mceconomy.event;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.event.job.OpenSelectJobListener;
import leehj050211.mceconomy.event.player.PlayerJoinListener;
import leehj050211.mceconomy.event.player.PlayerKickListener;
import leehj050211.mceconomy.event.player.PlayerQuitListener;
import leehj050211.mceconomy.event.shop.OpenShopListener;
import leehj050211.mceconomy.event.shop.SelectShopCategoryListener;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryListener;
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
                new OpenSelectJobListener(),
                new OpenShopListener(),
                new SelectShopCategoryListener(),
                new SelectShopItemCategoryListener()
        };

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
