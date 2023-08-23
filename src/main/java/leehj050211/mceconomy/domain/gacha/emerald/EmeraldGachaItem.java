package leehj050211.mceconomy.domain.gacha.emerald;

import leehj050211.mceconomy.domain.gacha.GachaItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum EmeraldGachaItem implements GachaItem {

    STONE(0, "돌", Material.STONE, 0.4),
    FIREWORK_ROCKET(1, "폭죽 로켓", Material.FIREWORK_ROCKET, 0.26),
    GOLD_ORE(2, "금 광석", Material.GOLD_ORE, 0.20),
    ZOMBIFIED_PIGLIN_SPAWN_EGG(3, "좀비화 피글린 생성 알", Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, 0.1),
    DIAMOND(4, "다이아몬드", Material.DIAMOND, 0.03999),
    ELYTRA(5, "겉날개", Material.ELYTRA, 0.00001);

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