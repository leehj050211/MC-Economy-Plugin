package leehj050211.mceconomy.gui.shop;

import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.gui.CustomGui;
import leehj050211.mceconomy.gui.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopItemGui extends CustomGui {

    private final ShopManager shopManager = ShopManager.getInstance();
    private final int pageSize = 1;

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
        List<ShopItemData> filteredItemList = shopManager.getItemList(category);
        ItemMenu[] itemMenus = new ItemMenu[filteredItemList.size()];
        for (int i=0; i<filteredItemList.size(); i++) {
            itemMenus[i] = new ItemMenu(i, getItemIcon(filteredItemList.get(i)));
        }

        openMenu(player, pageSize, currentPage, subId,"구매할 상품 선택", itemMenus);
    }

    private static ItemStack getItemIcon(ShopItemData itemData) {
        ItemStack icon = new ItemStack(itemData.getMaterial(), 1);
        ItemMeta meta = icon.getItemMeta();
        List<String> description = List.of(
                "현재 가격: " + Formatter.formatMoney(itemData.getCurrentPrice()),
                "정상 가격: " + Formatter.formatMoney(itemData.getPrice()),
                "물량: " + Formatter.formatMoney(itemData.getPrice()));
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
