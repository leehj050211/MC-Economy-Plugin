package leehj050211.mceconomy.global.util;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import leehj050211.mceconomy.global.world.WorldManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;

@UtilityClass
public class EstateUtil {

    private static final World mainWorld = WorldManager.getInstance().getMainWorld();

    public long getSize(ProtectedRegion region) {
        BlockVector3 delta = region.getMaximumPoint().add(1, 1, 1).subtract(region.getMinimumPoint());
        long dX = delta.getX();
        long dZ = delta.getZ();
        return dX * dZ;
    }

    public long getSize(BlockVector3 point1, BlockVector3 point2) {
        long dX = Math.abs(point1.getX() - point2.getX()) + 1;
        long dZ = Math.abs(point1.getZ() - point2.getZ()) + 1;
        return dX * dZ;
    }

    public long getEstatePrice(long estateSize) {
        return estateSize * 10000;
    }

    public void displayEstate(ProtectedRegion region) {
        BlockVector3 minPoint = region.getMinimumPoint();
        BlockVector3 maxPoint = region.getMaximumPoint();

        double minX = Math.min(minPoint.getX(), maxPoint.getX());
        double minY = Math.min(minPoint.getY(), maxPoint.getY());
        double minZ = Math.min(minPoint.getZ(), maxPoint.getZ());
        double maxX = Math.max(minPoint.getX(), maxPoint.getX());
        double maxY = Math.max(minPoint.getY(), maxPoint.getY());
        double maxZ = Math.max(minPoint.getZ(), maxPoint.getZ());

        for (double y = minY; y <= maxY; y += 4) {
            for (double x = minX; x <= maxX + 1; x += 1) {
                mainWorld.spawnParticle(Particle.FLAME, x, y, minZ, 0, 0, 0, 0, 1);
                mainWorld.spawnParticle(Particle.FLAME, x, y, maxZ + 1, 0, 0, 0, 0, 1);
            }
            for (double z = minZ + 1; z <= maxZ + 1; z += 1) {
                mainWorld.spawnParticle(Particle.FLAME, minX, y, z, 0, 0, 0, 0, 1);
                mainWorld.spawnParticle(Particle.FLAME, maxX + 1, y, z, 0, 0, 0, 0, 1);
            }
        }
    }

}
