package leehj050211.mceconomy.event.player.listener;

import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exp.JobExpManager;
import leehj050211.mceconomy.global.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerManager playerManager = PlayerManager.getInstance();

    private final JobExpManager jobExpManager = JobExpManager.getInstance();

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = playerManager.getData(player.getUniqueId());

        playerManager.deletePlayer(player);
        jobExpManager.deletePlayer(playerData);
    }
}
