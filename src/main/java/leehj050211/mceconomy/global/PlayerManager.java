package leehj050211.mceconomy.global;

import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.PlayerData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerManager {

    private static PlayerManager instance;

    private static HashMap<Player, PlayerData> onlinePlayerData = new HashMap<>();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    public void addPlayer(Player player) {
        PlayerData playerData = playerDao.findByNickname(player.getName())
                .orElseGet(() -> signupPlayer(player));
        onlinePlayerData.put(player, playerData);
    }

    private PlayerData signupPlayer(Player player) {
        PlayerData playerData = PlayerData.create(player.getUniqueId(), player.getName());
        playerDao.save(playerData);
        return playerData;
    }

    public void deletePlayer(Player player) {
        onlinePlayerData.remove(player);
    }

    public PlayerData getData(Player player) {
        return onlinePlayerData.get(player);
    }
}
