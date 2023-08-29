package leehj050211.mceconomy.global.estate;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import leehj050211.mceconomy.global.world.WorldManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstateManager {

    private static EstateManager instance;
    public static EstateManager getInstance() {
        if (instance == null) {
            instance = new EstateManager();
        }
        return instance;
    }

    private final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    private RegionManager regions = container.get(new BukkitWorld(WorldManager.getInstance().getMainWorld()));

    private static final HashMap<Player, EstatePointWrapper> playerPointMap = new HashMap<>();

    public void setPoint(Player player, BlockVector3 point1, BlockVector3 point2) {
        EstatePointWrapper points = playerPointMap.get(player);
        if (points == null) {
            points = new EstatePointWrapper(player);
            playerPointMap.put(player, points);
        }

        if (point1 != null) {
            points.setPoint1(point1);
            player.sendMessage(String.format("좌표 1 설정됨 (X:%d, Z:%d)", point1.getBlockX(), point1.getBlockZ()));
            return;
        }
        points.setPoint2(point2);
        player.sendMessage(String.format("좌표 2 설정됨 (X:%d, Z:%d)", point2.getBlockX(), point2.getBlockZ()));
    }

}
