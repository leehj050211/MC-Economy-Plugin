package leehj050211.mceconomy.global.estate;

import com.sk89q.worldedit.math.BlockVector3;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class EstatePointWrapper {

    private final Player player;
    private BlockVector3 point1;
    private BlockVector3 point2;

    public EstatePointWrapper(Player player) {
        this.player = player;
    }

}
