package leehj050211.mceconomy.global.util;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EstateUtil {

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

}
