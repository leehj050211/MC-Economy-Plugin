package leehj050211.mceconomy.event;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.event.gacha.listener.OpenGachaListener;
import leehj050211.mceconomy.event.job.listener.OpenJobMenuListener;
import leehj050211.mceconomy.event.job.listener.OpenSelectJobListener;
import leehj050211.mceconomy.event.menu.listener.OpenMenuListener;
import leehj050211.mceconomy.event.player.listener.PlayerBlockBreakListener;
import leehj050211.mceconomy.event.player.listener.PlayerJoinListener;
import leehj050211.mceconomy.event.player.listener.PlayerKickListener;
import leehj050211.mceconomy.event.player.listener.PlayerQuitListener;
import leehj050211.mceconomy.event.shop.listener.OpenShopItemManageListener;
import leehj050211.mceconomy.event.shop.listener.OpenShopListener;
import leehj050211.mceconomy.event.shop.listener.OpenShopMaterialInfoListener;
import leehj050211.mceconomy.event.shop.listener.OpenWholeSaleListener;
import leehj050211.mceconomy.event.shop.listener.SelectShopCategoryListener;
import leehj050211.mceconomy.event.shop.listener.SelectShopItemCategoryListener;
import leehj050211.mceconomy.event.stat.listener.OpenStatListener;
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
                new PlayerBlockBreakListener(),
                new OpenMenuListener(),
                new OpenJobMenuListener(),
                new OpenSelectJobListener(),
                new OpenShopListener(),
                new SelectShopCategoryListener(),
                new SelectShopItemCategoryListener(),
                new OpenShopMaterialInfoListener(),
                new OpenWholeSaleListener(),
                new OpenShopItemManageListener(),
                new OpenGachaListener(),
                new OpenStatListener()
        };

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
