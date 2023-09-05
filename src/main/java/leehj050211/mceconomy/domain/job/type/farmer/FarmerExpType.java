package leehj050211.mceconomy.domain.job.type.farmer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum FarmerExpType {
    SUGAR_CANE(0, "사탕수수", 1L, Material.SUGAR_CANE),
    WHEAT(1, "밀", 5L, Material.WHEAT),
    CARROT(2, "당근", 5L, Material.CARROT),
    POTATO(3, "감자", 5L, Material.POTATO),
    BEETROOT(4, "비트루트", 5L, Material.BEETROOT),
    COCOA_BEANS(5, "코코아 콩", 5L, Material.COCOA_BEANS),
    SWEET_BERRIES(6, "달콤한 열매", 5L, Material.SWEET_BERRIES),
    APPLE(7, "사과", 10L, Material.APPLE),
    MELON(8, "수박", 10L, Material.MELON),
    PUMPKIN(9, "호박", 10L, Material.PUMPKIN),
    NETHER_WART(10, "네더 사마귀", 10L, Material.NETHER_WART);

    private final int value;
    private final String name;
    private final Long exp;
    private final Material icon;

}