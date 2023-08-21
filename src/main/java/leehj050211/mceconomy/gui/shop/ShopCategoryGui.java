package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.OpenShopPurchaseEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

public class ShopCategoryGui implements Listener {

    private static final int ROWS = 3;

    @EventHandler
    public void onOpenShop(OpenShopPurchaseEvent event) {
        openMenu(event.player);
    }

    private void openMenu(Player player) {
        SGMenu sgMenu = spiGUI.create("상품 카테고리 선택 ({currentPage}/{maxPage})", ROWS);
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i=0; i<ShopCategory.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getCategoryIcon(ShopCategory.values()[i]));
        }
        player.openInventory(sgMenu.getInventory());
    }

    private static SGButton getCategoryIcon(ShopCategory category) {
        ItemStack icon = new ItemBuilder(category.getIcon())
                .name(category.getName())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectCategory(event, category));
    }

    private static void selectCategory(InventoryClickEvent event, ShopCategory category) {
        Player player = (Player) event.getWhoClicked();
        if (category.hasChildCategory()) {
            Bukkit.getPluginManager().callEvent(new SelectShopCategoryEvent(player, category));
        } else {
            ShopItemCategory itemCategory = ShopItemCategory.valueOf(category.name());
            Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemCategory));
        }
    }
}
