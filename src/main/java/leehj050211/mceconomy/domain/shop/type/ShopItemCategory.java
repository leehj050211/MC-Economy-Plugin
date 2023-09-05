package leehj050211.mceconomy.domain.shop.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ShopItemCategory {

    DIRT(1, ShopCategory.DIRT, "흙, 모래", Material.DIRT),

    STONE(101, ShopCategory.STONE, "돌", Material.STONE),
    COBBLESTONE(102, ShopCategory.STONE, "조약돌", Material.COBBLESTONE),
    ANDESITE(103, ShopCategory.STONE, "안산암", Material.ANDESITE),
    DIORITE(104, ShopCategory.STONE, "섬록암", Material.DIORITE),
    GRANITE(105, ShopCategory.STONE, "화강암", Material.GRANITE),
    SANDSTONE(106, ShopCategory.STONE, "사암", Material.SANDSTONE),
    DEEPSLATE(107, ShopCategory.STONE, "심층암", Material.DEEPSLATE),
    STONE_BRICK(108, ShopCategory.STONE, "석재", Material.STONE_BRICKS),
    MOSSY_COBBLESTONE(109, ShopCategory.STONE, "이끼 낀 조약돌", Material.MOSSY_COBBLESTONE),
    MOSSY_STONE(110, ShopCategory.STONE, "이끼 낀 석재", Material.MOSSY_STONE_BRICKS),
    BRICK(111, ShopCategory.STONE, "벽돌", Material.BRICKS),
    MUD_BRICKS(112, ShopCategory.STONE, "진흙 벽돌", Material.MUD_BRICKS),
    DRIPSTONE(113, ShopCategory.STONE, "점적석", Material.DRIPSTONE_BLOCK),

    ACACIA(201, ShopCategory.WOOD, "아카시아 나무", Material.ACACIA_LOG),
    BIRCH(202, ShopCategory.WOOD, "자작나무", Material.BIRCH_LOG),
    DARK_OAK(203, ShopCategory.WOOD, "짙은 참나무", Material.DARK_OAK_LOG),
    JUNGLE(204, ShopCategory.WOOD, "정글 나무", Material.JUNGLE_LOG),
    MANGROVE(205, ShopCategory.WOOD, "맹그로브 나무", Material.MANGROVE_LOG),
    OAK(206, ShopCategory.WOOD, "참나무", Material.OAK_LOG),
    SPRUCE(207, ShopCategory.WOOD, "가문비나무", Material.SPRUCE_LOG),

    CONCRETE(301, ShopCategory.CONCRETE, "콘크리트", Material.WHITE_CONCRETE),

    TERRACOTTA(401, ShopCategory.TERRACOTTA, "테라코타", Material.TERRACOTTA),

    REDSTONE_WIRE(501, ShopCategory.REDSTONE, "전선", Material.REPEATER),
    POWER_SOURCES(502, ShopCategory.REDSTONE, "전원", Material.REDSTONE_BLOCK),
    BUTTON(503, ShopCategory.REDSTONE, "버튼", Material.OAK_BUTTON),
    PRESSURE_PLATE(504, ShopCategory.REDSTONE, "감압판", Material.STONE_PRESSURE_PLATE),

    COAL(601, ShopCategory.MINERAL, "석탄", Material.COAL),
    IRON(602, ShopCategory.MINERAL, "철", Material.IRON_INGOT),
    GOLD(603, ShopCategory.MINERAL, "금", Material.GOLD_INGOT),
    DIAMOND(604, ShopCategory.MINERAL, "다이아몬드", Material.DIAMOND),
    NETHERITE(605, ShopCategory.MINERAL, "네더라이트", Material.NETHERITE_INGOT),
    EMERALD(606, ShopCategory.MINERAL, "에메랄드", Material.EMERALD),
    AMETHYST(607, ShopCategory.MINERAL, "자수정", Material.AMETHYST_SHARD),
    LAPIS_LAZULI(608, ShopCategory.MINERAL, "청금석", Material.LAPIS_LAZULI),
    OBSIDIAN(609, ShopCategory.MINERAL, "흑요석", Material.OBSIDIAN),
    COPPER(610, ShopCategory.MINERAL, "구리", Material.COPPER_INGOT),

    GLASS(701, ShopCategory.GLASS, "통유리", Material.GLASS),
    GLASS_PANES(702, ShopCategory.GLASS, "유리판", Material.GLASS_PANE),

    WOOL(801, ShopCategory.WOOL, "양털", Material.WHITE_WOOL),
    CARPET(802, ShopCategory.WOOL, "카펫", Material.WHITE_CARPET),

    LEAVES(901, ShopCategory.PLANT, "나뭇잎", Material.OAK_LEAVES),
    SAPLINGS(902, ShopCategory.PLANT, "묘목", Material.OAK_SAPLING),
    FLOWERS(903, ShopCategory.PLANT, "꽃", Material.POPPY),
    MUSHROOMS(904, ShopCategory.PLANT, "버섯", Material.BROWN_MUSHROOM),
    PLANT_CROPS(905, ShopCategory.PLANT, "작물", Material.PUMPKIN),
    CAVE_PLANTS(906, ShopCategory.PLANT, "동굴 식물", Material.BIG_DRIPLEAF),
    SHRUBBERY(907, ShopCategory.PLANT, "관목숲", Material.GRASS),
    SEED(908, ShopCategory.PLANT, "씨앗", Material.BEETROOT_SEEDS),

    BASALT(1001, ShopCategory.NETHER, "현무암", Material.BASALT),
    BLACKSTONE(1002, ShopCategory.NETHER, "흑암", Material.BLACKSTONE),
    CRIMSON(1003, ShopCategory.NETHER, "진홍빛 네사체", Material.CRIMSON_NYLIUM),
    NETHER_QUARTZ(1004, ShopCategory.NETHER, "석영", Material.QUARTZ_BLOCK),
    WRAPED(1005, ShopCategory.NETHER, "뒤틀린 네사체", Material.WARPED_NYLIUM),
    ETC_NETHER_BLOCKS(1006, ShopCategory.NETHER, "기타", Material.STICK),

    CHORUS(1101, ShopCategory.END, "후렴", Material.CHORUS_FRUIT),
    END_STONE(1102, ShopCategory.END, "엔드 돌", Material.END_STONE),
    PURPUR(1103, ShopCategory.END, "퍼퍼", Material.PURPUR_BLOCK),

    CORAL(1201, ShopCategory.AQUATIC, "산호", Material.FIRE_CORAL),
    CORAL_BLOCK(1202, ShopCategory.AQUATIC, "산호 블록", Material.BRAIN_CORAL_BLOCK),
    SPONGE(1203, ShopCategory.AQUATIC, "스펀지", Material.SPONGE),
    PRISMARINE(1204, ShopCategory.AQUATIC, "프리즈머린", Material.PRISMARINE),
    ICE(1205, ShopCategory.AQUATIC, "얼음", Material.ICE),
    SEAGRASS(1206, ShopCategory.AQUATIC, "해초", Material.SEAGRASS),

    INFESTED_BLOCKS(1302, ShopCategory.MOBS, "벌레먹은 블록", Material.INFESTED_STONE),
    SCULK_BLOCKS(1303, ShopCategory.MOBS, "스컬크", Material.SCULK_SENSOR),
    BLOCKS_RELATED_TO_BEES(1304, ShopCategory.MOBS, "벌", Material.BEEHIVE),

    CONTAINERS(1401, ShopCategory.UTILITY, "상자", Material.CHEST),
    CRAFTING_BLOCKS(1403, ShopCategory.UTILITY, "작업대", Material.CRAFTING_TABLE),
    VILLAGE_BLOCKS(1404, ShopCategory.UTILITY, "마을", Material.ANVIL),
    BED(1405, ShopCategory.UTILITY, "침대", Material.RED_BED),
    ETC_UTILLITY_BLOCKS(1406, ShopCategory.UTILITY, "기타", Material.STICK),

    CANDLES(1501, ShopCategory.LIGHT, "초", Material.CANDLE),
    CAMPFIRES(1502, ShopCategory.LIGHT, "캠프파이어", Material.CAMPFIRE),
    FROGLIGHTS(1503, ShopCategory.LIGHT, "개구리불", Material.OCHRE_FROGLIGHT),
    LAMPS(1504, ShopCategory.LIGHT, "램프", Material.LANTERN),
    UNDERWATER_LIGHT_BLOCKS(1505, ShopCategory.LIGHT, "바다랜턴", Material.SEA_LANTERN),
    TORCHES(1506, ShopCategory.LIGHT, "횃불", Material.TORCH),

    FOOD_CROPS(1601, ShopCategory.FOOD, "작물", Material.POTATO),
    MEAT(1602, ShopCategory.FOOD, "생고기", Material.BEEF),
    FISH(1603, ShopCategory.FOOD, "물고기", Material.SALMON),
    COOKED_FOOD(1604, ShopCategory.FOOD, "구운 고기", Material.COOKED_BEEF),
    CRAFTED_DISHES(1605, ShopCategory.FOOD, "요리", Material.RABBIT_STEW),
    ETC_FOOD(1606, ShopCategory.FOOD, "기타", Material.STICK),

    SHOVELS(1701, ShopCategory.TOOLS, "삽", Material.DIAMOND_SHOVEL),
    HOES(1702, ShopCategory.TOOLS, "괭이", Material.DIAMOND_HOE),
    AXES(1703, ShopCategory.TOOLS, "도끼", Material.DIAMOND_AXE),
    PICKAXES(1704, ShopCategory.TOOLS, "곡괭이", Material.DIAMOND_PICKAXE),
    BUCKETS(1705, ShopCategory.TOOLS, "양동이", Material.BUCKET),
    FIREWORKS(1706, ShopCategory.TOOLS, "로켓", Material.FIREWORK_ROCKET),
    HELMETS(1707, ShopCategory.TOOLS, "헬멧", Material.DIAMOND_HELMET),
    CHESTPLATES(1708, ShopCategory.TOOLS, "흉갑", Material.DIAMOND_CHESTPLATE),
    LEGGINGS(1709, ShopCategory.TOOLS, "레깅스", Material.DIAMOND_LEGGINGS),
    BOOTS(1710, ShopCategory.TOOLS, "부츠", Material.DIAMOND_BOOTS),
    SWORDS(1711, ShopCategory.TOOLS, "칼", Material.DIAMOND_SWORD),
    PROJECTILE(1712, ShopCategory.TOOLS, "투사체", Material.ARROW),

    BOATS(1801, ShopCategory.TRANSPORTATION, "보트", Material.OAK_BOAT),
    MINECARTS(1802, ShopCategory.TRANSPORTATION, "마인카트", Material.MINECART),

    ETC(1901, ShopCategory.ETC, "기타 아이템", Material.STICK);;

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
