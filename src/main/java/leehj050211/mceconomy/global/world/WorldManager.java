package leehj050211.mceconomy.global.world;

import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;
import leehj050211.mceconomy.global.player.PlayerManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorldManager {

    private static WorldManager instance;
    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    private final PlayerManager playerManager = PlayerManager.getInstance();

    @Getter
    private final World mainWorld = Bukkit.getWorld("world");

    static {
        initWorkspaceWorlds();
    }

    private static void initWorkspaceWorlds() {
        Arrays.stream(JobType.values())
                .filter(jobType -> {
                    World world = Bukkit.getWorld(jobType.getWorkspaceWorldName());
                    return jobType.hasWorkspace() && world == null;
                })
                .forEach(jobType -> {
                    WorldCreator worldCreator = new WorldCreator(jobType.getWorkspaceWorldName());
                    worldCreator.type(WorldType.FLAT);
                    worldCreator.createWorld();
                });
    }

    public void tpToWorkspace(Player player) {
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        JobType jobType = playerData.getJob();
        if(!jobType.hasWorkspace()) {
            new GeneralMCPlayerException(playerData.getUuid(), "해당 직업은 별도의 작업장이 없습니다.");
        }
        World workspace = Bukkit.getWorld(jobType.getWorkspaceWorldName());
        player.teleport(workspace.getSpawnLocation());
    }
}
