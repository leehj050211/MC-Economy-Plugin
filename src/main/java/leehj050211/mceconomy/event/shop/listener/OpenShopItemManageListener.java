package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.OpenShopItemManageEvent;
import leehj050211.mceconomy.gui.shop.ShopItemManageGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenShopItemManageListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(OpenShopItemManageEvent event) {
        Inventory menu = new ShopItemManageGui(event.getPlayer(), event.getItemData())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }

}