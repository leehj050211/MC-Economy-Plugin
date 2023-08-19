package leehj050211.mceconomy.global.player;

import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.player.PlayerData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerManager {

    private static PlayerManager instance;
    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }
        return instance;
    }

    private static final HashMap<UUID, PlayerWrapper> onlinePlayer = new HashMap<>();
    private static final HashMap<String, PlayerWrapper> onlinePlayerNickname = new HashMap<>();

    private final PlayerDao playerDao = PlayerDao.getInstance();

    public void addPlayer(Player player) {
        PlayerData playerData = playerDao.findByNickname(player.getName())
                .orElseGet(() -> signupPlayer(player));

        PlayerWrapper playerWrapper = new PlayerWrapper(player.getUniqueId(), player, playerData);
        onlinePlayer.put(player.getUniqueId(), playerWrapper);
        onlinePlayerNickname.put(player.getName(), playerWrapper);
    }

    private PlayerData signupPlayer(Player player) {
        PlayerData playerData = PlayerData.create(player.getUniqueId(), player.getName());
        playerDao.save(playerData);
        return playerData;
    }

    public void deletePlayer(Player player) {
        playerDao.update(getData(player.getUniqueId()));

        onlinePlayer.remove(player.getUniqueId());
        onlinePlayerNickname.remove(player.getName());
    }

    public List<PlayerData> getAllData() {
        return onlinePlayer.values().stream()
                .map(PlayerWrapper::playerData)
                .toList();
    }

    public PlayerData getData(UUID uuid) {
        return onlinePlayer.get(uuid).playerData();
    }

    public PlayerData getData(String nickname) {
        return onlinePlayerNickname.get(nickname).playerData();
    }

    public Player getPlayer(UUID uuid) {
        return onlinePlayer.get(uuid).player();
    }

    public Player getPlayer(String nickname) {
        return onlinePlayerNickname.get(nickname).player();
    }
}
