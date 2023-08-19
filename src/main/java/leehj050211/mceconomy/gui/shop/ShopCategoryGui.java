package leehj050211.mceconomy.gui.shop;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.MenuConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import leehj050211.mceconomy.event.shop.OpenShopPurchaseEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.gui.CustomGui;
import leehj050211.mceconomy.gui.ItemMenu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ShopCategoryGui extends CustomGui {

    private final int pageSize = 1;

    public ShopCategoryGui() {
        super(MenuId.SELECT_SHOP_CATEGORY);
    }

    @EventHandler
    public void onOpenShop(OpenShopPurchaseEvent event) {
        openPage(event.player, null, 1);
    }

    @Override
    protected void openPage(Player player, String subId, int currentPage) {
        ItemMenu[] itemMenus = new ItemMenu[ShopCategory.values().length];
        for (int i=0; i<ShopCategory.values().length; i++) {
            itemMenus[i] = new ItemMenu(i, getCategoryIcon(ShopCategory.values()[i]));
        }

        openMenu(player, pageSize, currentPage, subId,"상품 카테고리 선택", itemMenus);
    }

    private static ItemStack getCategoryIcon(ShopCategory category) {
        ItemStack icon = new ItemStack(category.getIcon(), 1);
        ItemMeta meta = icon.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), MenuConstant.SELECT_SHOP_CATEGORY_KEY);

        meta.setDisplayName(category.getName());
        data.set(key, PersistentDataType.STRING, category.name());
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Player player, ItemStack item) {
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), MenuConstant.SELECT_SHOP_CATEGORY_KEY);

        ShopCategory category = ShopCategory.valueOf(data.get(key, PersistentDataType.STRING));
        if (category.hasChildCategory()) {
            Bukkit.getPluginManager().callEvent(new SelectShopCategoryEvent(player, category));
        } else {
            //TODO 아이템 목록 메뉴 열기
        }
    }

}
