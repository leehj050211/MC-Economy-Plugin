package leehj050211.mceconomy.domain.job.type.miner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum MinerExpType {
    STONE(0, "돌", 2L, Material.STONE),
    COAL_ORE(1, "석탄 광석", 3L, Material.COAL_ORE),
    IRON_ORE(2, "철 광석", 10L, Material.IRON_ORE),
    GOLD_ORE(3, "금 광석", 15L, Material.GOLD_ORE),
    DEEPSLATE_GOLD_ORE(4, "심층암 금 광석", 30L, Material.DEEPSLATE_GOLD_ORE),
    DIAMOND_ORE(5, "다이아몬드 광석", 500L, Material.DIAMOND_ORE),
    SAND(6, "test", 500L, Material.SAND);

    private final int value;
    private final String name;
    private final Long exp;
    private final Material icon;
}