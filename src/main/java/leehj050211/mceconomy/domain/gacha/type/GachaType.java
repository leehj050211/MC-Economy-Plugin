package leehj050211.mceconomy.domain.gacha.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum GachaType {

    NORMAL_GACHA(0, "일반 가챠", 5000L, Material.CHEST),
    EMERALD_GACHA(1, "에메랄드 가챠", 20000L, Material.EMERALD_BLOCK),
    DIAMOND_GACHA(2, "이구병 가챠", 100000L, Material.DIAMOND_BLOCK);

    private final int value;
    private final String name;
    private final Long price;
    private final Material icon;
}
