package leehj050211.mceconomy.global.shop;

import leehj050211.mceconomy.constant.ShopConstant;
import leehj050211.mceconomy.dao.ShopItemDao;
import leehj050211.mceconomy.dao.ShopPriceCategoryDao;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.MessageUtil;
import leehj050211.mceconomy.global.util.ShopUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopManager {

    private static ShopManager instance;
    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    private static List<ShopPriceCategory> priceCategories = new ArrayList<>();
    private static final HashMap<ShopItemCategory, ShopPriceCategory> itemCategoryPrice = new HashMap<>();
    private static final HashMap<Material, ShopItemData> itemDataList = new HashMap<>();
    private static final HashMap<ShopItemCategory, List<ShopItemData>> itemCategoryList = new HashMap<>();

    private static final PlayerManager playerManager = PlayerManager.getInstance();
    private static final ShopPriceCategoryDao priceCategoryDao = ShopPriceCategoryDao.getInstance();
    private static final ShopItemDao shopItemDao = ShopItemDao.getInstance();

    static {
        initData();
    }

    private static void initData() {
        priceCategories = priceCategoryDao.findAll();
        shopItemDao.findAll().forEach(data -> {
            itemDataList.put(data.getMaterial(), data);
            List<ShopItemData> shopItemList = itemCategoryList.get(data.getItemCategory());
            if (shopItemList == null) {
                shopItemList = new ArrayList<>();
            }
            shopItemList.add(data);
            itemCategoryList.put(data.getItemCategory(), shopItemList);
            itemCategoryPrice.put(data.getItemCategory(), data.getPriceCategory());
        });
    }

    public List<ShopPriceCategory> getAllPriceCategory() {
        return priceCategories;
    }

    public List<ShopItemData> getAllItemList() {
        return itemDataList.values().stream()
                .toList();
    }

    public List<ShopItemData> getItemList(ShopItemCategory category) {
        List<ShopItemData> shopItemList = itemCategoryList.get(category);
        if (shopItemList == null) {
            shopItemList = new ArrayList<>();
        }
        return shopItemList;
    }

    public ShopPriceCategory getPriceCategory(ShopItemCategory itemCategory) {
        return itemCategoryPrice.get(itemCategory);
    }

    public void buyItem(Player player, Material material, int amount) {
        ShopItemData itemData = itemDataList.get(material);
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        PlayerData playerData = playerManager.getData(player.getUniqueId());

        playerData.decreaseMoney(ShopUtil.getCurrentPrice(itemData, amount));
        player.sendMessage(MessageUtil.getRemainingMoney(playerData));
        priceCategory.increaseDemand(amount);
    }

    public void sellItem(Player player, Material material, int amount) {
        ShopItemData itemData = itemDataList.get(material);
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        PlayerData playerData = playerManager.getData(player.getUniqueId());

        playerData.increaseMoney(ShopUtil.getCurrentPrice(itemData, amount));
        priceCategory.increaseSupply(amount);
    }

    public long calcSellItem(Material material, int amount) {
        ShopItemData itemData = itemDataList.get(material);
        return (long) (ShopUtil.getCurrentPrice(itemData, amount) * (1 - ShopConstant.TAX));
    }
}
