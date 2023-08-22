package leehj050211.mceconomy.global.world;

import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;
import leehj050211.mceconomy.global.player.PlayerManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorldManager {

    private final PlayerManager playerManager = PlayerManager.getInstance();
    private static final Map<JobType, World> worlds = new HashMap<>();
    private static WorldManager instance;
    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    public static void WorldInit() {
        for(JobType i : JobType.values()) {
            World world = Bukkit.getWorld(i.getName());
            if(world != null) {
                continue;
            }
            if(i.isWorldGen() == false) {
                continue;
            }
            WorldCreator worldCreator = new WorldCreator(i.getName());
            worldCreator.type(WorldType.FLAT);
            world = worldCreator.createWorld();
            worlds.put(i, world);
        }
    }

    public void tpToJobWorld(Player player) {
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        JobType playerJob = playerData.getJob();
        World jobWorld = worlds.get(playerJob.getName());
        if(!playerJob.isWorldGen()) {
            //Exception - 이동할 수 없음
            new GeneralMCPlayerException(playerData.getUuid(), "해당 직업은 별도의 작업장이 없습니다.");
        }
        player.teleport(jobWorld.getSpawnLocation());
    }
}
