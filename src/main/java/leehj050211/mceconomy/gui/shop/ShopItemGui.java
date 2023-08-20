package leehj050211.mceconomy.gui.shop;

import leehj050211.mceconomy.constant.MenuConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.CustomGui;
import leehj050211.mceconomy.gui.ItemMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ShopItemGui extends CustomGui {

    private final ShopManager shopManager = ShopManager.getInstance();
    private final int pageSize = 3;

    public ShopItemGui() {
        super(MenuId.SELECT_SHOP_ITEM);
    }

    @EventHandler
    public void onSelectShopCategory(SelectShopItemCategoryEvent event) {
        openPage(event.getPlayer(), event.getCategory().name(), 1);
    }

    @Override
    protected void openPage(Player player, String subId, int currentPage) {
        ShopItemCategory category = ShopItemCategory.valueOf(subId);
        ShopPriceCategory priceCategory = shopManager.getPriceCategory(category);
        List<ShopItemData> filteredItemList = shopManager.getItemList(category);
        ItemMenu[] itemMenus = new ItemMenu[filteredItemList.size() + 1];
        for (int i=0; i<filteredItemList.size(); i++) {
            itemMenus[i] = new ItemMenu(i, getItemIcon(filteredItemList.get(i)));
        }
        itemMenus[filteredItemList.size()] = new ItemMenu(1, getCategoryInfoIcon(priceCategory), true);

        openMenu(player, pageSize, currentPage, subId,"구매할 상품 선택", itemMenus);
    }

    private static ItemStack getItemIcon(ShopItemData itemData) {
        ItemStack icon = new ItemStack(itemData.getMaterial(), 1);
        ItemMeta meta = icon.getItemMeta();
        List<String> description = List.of(
                "현재 가격: " + Formatter.formatMoney(itemData.getCurrentPrice()),
                "정상 가격: " + Formatter.formatMoney(itemData.getPrice()));
        meta.setLore(description);
        icon.setItemMeta(meta);
        return icon;
    }

    private static ItemStack getCategoryInfoIcon(ShopPriceCategory category) {
        ItemStack icon = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = icon.getItemMeta();

        ItemUtil.setItemData(meta, MenuConstant.ONLY_VIEW_ITEM_KEY, PersistentDataType.BOOLEAN, true);
        meta.setDisplayName(String.format("%s%s상품정보", ChatColor.RESET, ChatColor.BOLD));
        List<String> description = List.of(
                String.format("%s남은 물량: %s", (category.getAmount() > 0 ? ChatColor.GOLD : ChatColor.DARK_RED), Formatter.formatAmount(category.getAmount())),
                String.format("%s수요: %s", ChatColor.RED, Formatter.formatAmount(category.getDemand())),
                String.format("%s지난 주 수요: %s", ChatColor.RED, Formatter.formatAmount(category.getLastDemand())),
                String.format("%s공급: %s",ChatColor.GREEN, Formatter.formatAmount(category.getSupply())),
                String.format("%s지난 주 공급: %s", ChatColor.GREEN, Formatter.formatAmount(category.getLastSupply())));
        meta.setLore(description);
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Player player,
                           ItemStack item, String subId, int currentPage) {
        shopManager.buyItem(player, item.getType());
        ItemStack itemStack = new ItemStack(item.getType(), item.getAmount());
        player.getInventory().addItem(itemStack);

        openPage(player, subId, currentPage);
    }
}
