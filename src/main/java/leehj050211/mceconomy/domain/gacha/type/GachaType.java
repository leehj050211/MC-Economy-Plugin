package leehj050211.mceconomy.domain.gacha.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum GachaType {

    NORMAL_GACHA(0, "일반 가챠", "100원", Material.STONE),
    EMERALD_GACHA(1, "에메랄드 가챠", "1000원", Material.EMERALD_BLOCK),
    DIAMOND_GACHA(2, "이구병 가챠", "10000원", Material.DIAMOND_BLOCK);

    private final int value;
    private final String name;
    private final String description;
    private final Material icon;
}
