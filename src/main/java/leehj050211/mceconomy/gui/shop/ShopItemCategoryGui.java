package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

public class ShopItemCategoryGui implements Listener {

    private static final int ROWS = 3;

    @EventHandler
    public void onSelectShopCategory(SelectShopCategoryEvent event) {
        openPage(event.getPlayer(), event.getCategory());
    }

    protected void openPage(Player player, ShopCategory shopCategory) {
        SGMenu sgMenu = spiGUI.create("상품 카테고리 선택 ({currentPage}/{maxPage})", ROWS);
        sgMenu.setAutomaticPaginationEnabled(true);

        List<ShopItemCategory> filteredCategories = ShopItemCategory.getItemCategories(shopCategory);
        for (int i=0; i<filteredCategories.size(); i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getItemCategoryIcon(filteredCategories.get(i)));
        }
        player.openInventory(sgMenu.getInventory());
    }

    private static SGButton getItemCategoryIcon(ShopItemCategory itemCategory) {
        ItemStack icon = new ItemBuilder(itemCategory.getIcon())
                .name(itemCategory.getName())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectItemCategory(event, itemCategory));
    }

    private static void selectItemCategory(InventoryClickEvent event, ShopItemCategory itemCategory) {
        Player player = (Player) event.getWhoClicked();
        Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemCategory));
    }
}
