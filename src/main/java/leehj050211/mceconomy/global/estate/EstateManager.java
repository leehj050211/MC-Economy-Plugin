package leehj050211.mceconomy.global.estate;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.EstateUtil;
import leehj050211.mceconomy.global.world.WorldManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
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

    private final StringFlag nameDisplayFlag = new StringFlag("bluemap-display");
    private final StateFlag extrudeFlag = new StateFlag("bluemap-extrude", false);
    private final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

    private final World mainWorld = WorldManager.getInstance().getMainWorld();
    private final RegionManager regionManager = container.get(new BukkitWorld(mainWorld));

    private static final HashMap<Player, EstatePointWrapper> playerPointMap = new HashMap<>();

    private final PlayerManager playerManager = PlayerManager.getInstance();


    public void setPoint(Player player, Location location1, Location location2) {
        EstatePointWrapper points = playerPointMap.get(player);
        if (points == null) {
            points = new EstatePointWrapper(player);
            playerPointMap.put(player, points);
        }

        if (location1 != null) {
            BlockVector3 point = BlockVector3.at(location1.getX(), mainWorld.getMinHeight(), location1.getZ());
            points.setPoint1(point);
            player.sendMessage(String.format("좌표 1 설정됨 (X:%d, Z:%d)", point.getBlockX(), point.getBlockZ()));
            return;
        }
        BlockVector3 point = BlockVector3.at(location2.getX(), mainWorld.getMaxHeight(), location2.getZ());
        points.setPoint2(point);
        player.sendMessage(String.format("좌표 2 설정됨 (X:%d, Z:%d)", point.getBlockX(), point.getBlockZ()));
    }

    public long getEstateSize(Player player) {
        EstatePointWrapper points = playerPointMap.get(player);
        if (points == null
                || points.getPoint1() == null
                || points.getPoint2() == null) {
            throw new GeneralMCPlayerException(player.getUniqueId(), "구매할 부동산의 좌표를 모두 설정해야합니다.");
        }
        return EstateUtil.getSize(points.getPoint1(), points.getPoint2());
    }

    public void buyEstate(Player player) {
        EstatePointWrapper points = playerPointMap.get(player);
        if (points == null
                || points.getPoint1() == null
                || points.getPoint2() == null) {
            throw new GeneralMCPlayerException(player.getUniqueId(), "구매할 부동산의 좌표를 모두 설정해야합니다.");
        }
        ProtectedRegion estate = new ProtectedCuboidRegion("estate_" + player.getUniqueId(), points.getPoint1(), points.getPoint2());
        estate.setFlag(nameDisplayFlag, player.getName() + "의 사유지");
        estate.setFlag(extrudeFlag, StateFlag.State.DENY);
        estate.setFlag(Flags.PASSTHROUGH, StateFlag.State.DENY);
        estate.setFlag(Flags.ENTRY, StateFlag.State.DENY);

        long estatePrice = EstateUtil.getEstatePrice(EstateUtil.getSize(estate));
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        playerData.decreaseMoney(estatePrice);

        DefaultDomain owners = estate.getOwners();
        owners.addPlayer(player.getUniqueId());

        regionManager.addRegion(estate);
    }

    public Collection<ProtectedRegion> getRegions() {
        return regionManager.getRegions().values();
    }
}
