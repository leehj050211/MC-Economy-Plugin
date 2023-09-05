package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.gui.shop.ShopItemCategoryGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class SelectShopCategoryListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(SelectShopCategoryEvent event) {
        Inventory menu = new ShopItemCategoryGui(event.getPlayer(), event.getCategory(), event.isManageMode())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}