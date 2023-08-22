package leehj050211.mceconomy.domain.gacha;

import org.bukkit.Material;

public interface GachaItem {
    double getProbability();
    String getName();
    Material getIcon();

}