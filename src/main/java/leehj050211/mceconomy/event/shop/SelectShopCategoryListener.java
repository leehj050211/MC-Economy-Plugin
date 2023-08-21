package leehj050211.mceconomy.event.shop;

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
        Inventory menu = new ShopItemCategoryGui(event.getPlayer(), event.getCategory())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}