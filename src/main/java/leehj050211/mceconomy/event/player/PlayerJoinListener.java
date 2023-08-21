package leehj050211.mceconomy.event.player;

import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.event.job.OpenSelectJobEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerManager playerManager = PlayerManager.getInstance();

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerManager.addPlayer(player);
        PlayerData playerData = playerManager.getData(player.getUniqueId());

        if (playerData.getJob() == JobType.JOBLESS) {
            Bukkit.getPluginManager().callEvent(new OpenSelectJobEvent(player));
        }
    }
}
