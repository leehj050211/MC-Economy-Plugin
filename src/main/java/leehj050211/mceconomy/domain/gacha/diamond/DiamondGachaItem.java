package leehj050211.mceconomy.domain.gacha.diamond;

import leehj050211.mceconomy.domain.gacha.GachaItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum DiamondGachaItem implements GachaItem {

    EXPERIENCE_BOTTLE(0, "경험치 병", Material.EXPERIENCE_BOTTLE, 0.4),
    EMERALD(1, "에메랄드", Material.EMERALD, 0.26),
    DIAMOND(2, "다이아몬드", Material.DIAMOND, 0.20),
    DRAGON_HEAD(3, "드래곤 머리", Material.DRAGON_HEAD, 0.1),
    ELYTRA(4, "겉날개", Material.ELYTRA, 0.03999),
    NETHERITE_INGOT(5, "네더라이트 주괴", Material.NETHERITE_INGOT, 0.00001);

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