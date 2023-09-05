package leehj050211.mceconomy.domain.shop.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum ShopCategory {

    DIRT("흙", Material.DIRT, false),
    STONE("돌", Material.COBBLESTONE, true),
    WOOD("나무", Material.OAK_LOG, true),
    CONCRETE("콘크리트", Material.WHITE_CONCRETE, false),
    TERRACOTTA("테라코타", Material.TERRACOTTA, false),
    REDSTONE("레드스톤", Material.REDSTONE_BLOCK, true),
    MINERAL("광물", Material.IRON_BLOCK, true),
    GLASS("유리", Material.GLASS, true),
    WOOL("양털", Material.WHITE_WOOL, false),
    PLANT("식물", Material.OAK_SAPLING, true),
    NETHER("네더", Material.NETHERRACK, true),
    END("엔더", Material.END_STONE, true),
    AQUATIC("수중", Material.ICE, true),
    MOBS("몹 관련", Material.SPAWNER, true),
    UTILITY("기능", Material.CRAFTING_TABLE, true),
    LIGHT("빛", Material.TORCH, true),
    FOOD("음식", Material.APPLE, true),
    TOOLS("장비", Material.DIAMOND_PICKAXE, true),
    TRANSPORTATION("수송", Material.MINECART, true),
    ETC("기타", Material.STICK, false);

    private final String name;
    private final Material icon;

    @Getter(AccessLevel.NONE)
    private final boolean hasChildCategory;

    public boolean hasChildCategory() {
        return hasChildCategory;
    }
}
