package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.gui.shop.ShopGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenShopListener implements Listener {

    @EventHandler
    public void onOpenShop(OpenShopEvent event) {
        Inventory menu = new ShopGui(event.getPlayer(), event.isManageMode())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}