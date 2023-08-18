package leehj050211.mceconomy.event.player;

import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private PlayerDao playerDao;

    public PlayerJoinListener() {
        playerDao = PlayerDao.getInstance();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.create(player.getUniqueId(), player.getName());
        playerDao.save(playerData);
    }
}
