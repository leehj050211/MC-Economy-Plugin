package leehj050211.mceconomy;

import leehj050211.mceconomy.event.player.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCEconomy extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
