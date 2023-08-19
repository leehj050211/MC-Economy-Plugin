package leehj050211.mceconomy.domain.shop.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum ShopCategory {
    DIRT(0, false, "흙 블록", Material.DIRT),
    STONE(1, true, "돌 블록", Material.COBBLESTONE),
    WOOD(2, true, "나무 블록", Material.OAK_LOG),
    CONCRETE(3, true, "콘크리트 블록", Material.WHITE_CONCRETE),
    TERRACOTTA(4, true, "테라코타 블록", Material.TERRACOTTA),
    REDSTONE(5, true, "레드스톤 블록", Material.REDSTONE);

    private final int value;

    @Getter(AccessLevel.NONE)
    private final boolean hasChildCategory;

    public boolean hasChildCategory() {
        return hasChildCategory;
    }

    private final String name;
    private final Material icon;
}
