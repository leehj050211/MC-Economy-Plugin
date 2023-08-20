package leehj050211.mceconomy.domain.shop.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ShopItemCategory {
    DIRT(1, ShopCategory.DIRT, "흙", Material.DIRT),

    STONE(101, ShopCategory.STONE, "돌", Material.STONE),
    COBBLESTONE(102, ShopCategory.STONE, "조약돌", Material.COBBLESTONE),
    CONCRETE(103, ShopCategory.STONE, "안산암", Material.ANDESITE),
    TERRACOTTA(104, ShopCategory.STONE, "섬록암", Material.DIORITE),
    GRANITE(105, ShopCategory.STONE, "화강암", Material.GRANITE),
    SANDSTONE(106, ShopCategory.STONE, "사암", Material.SANDSTONE),
    DEEPSLATE(107, ShopCategory.STONE, "심층암", Material.DEEPSLATE),
    STONE_BRICK(108, ShopCategory.STONE, "석재", Material.STONE_BRICKS),
    MOSSY_COBBLESTONE(109, ShopCategory.STONE, "이끼 낀 조약돌", Material.MOSSY_COBBLESTONE),
    MOSSY_STONE(110, ShopCategory.STONE, "이끼 낀 석재", Material.MOSSY_STONE_BRICKS),
    BRICK(111, ShopCategory.STONE, "벽돌", Material.BRICKS),
    MUD_BRICKS(112, ShopCategory.STONE, "진흙 벽돌", Material.MUD_BRICKS),
    DRIPSTONE(113, ShopCategory.STONE, "점적석", Material.DRIPSTONE_BLOCK),;

    private final int value;
    private final ShopCategory parentCategory;
    private final String name;
    private final Material icon;

    public static List<ShopItemCategory> getItemCategories(ShopCategory category) {
        return Arrays.stream(ShopItemCategory.values())
                .filter(item -> item.parentCategory.equals(category))
                .toList();
    }
}
