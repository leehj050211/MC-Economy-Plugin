package leehj050211.mceconomy.domain.gacha.normal;

import leehj050211.mceconomy.domain.gacha.GachaItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum NormalGachaItem implements GachaItem {

    DIRT(0, "흙", Material.DIRT, 0.2),
    COAL(1, "석탄", Material.COAL, 0.15),
    RAW_IRON(2, "철 원석", Material.RAW_IRON, 0.1),
    BAT_SPAWN_EGG(3, "박쥐 생성 알", Material.BAT_SPAWN_EGG, 0.07),
    ENDER_EYE(4, "엔더드래곤의 눈", Material.ENDER_EYE, 0.05),
    EMERALD_BLOCK(5, "에메랄드 블록", Material.EMERALD_BLOCK, 0.01);

    private final int value;
    private final String name;
    private final Material icon;

    private final double probability;

    @Override
    public double getProbability() {
        return probability;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Material getIcon() {
        return icon;
    }

}