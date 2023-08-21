package leehj050211.mceconomy.event.shop;

import leehj050211.mceconomy.gui.shop.ShopCategoryGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenShopListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(OpenShopEvent event) {
        Inventory menu = new ShopCategoryGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}