package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.OpenShopMaterialInfoEvent;
import leehj050211.mceconomy.gui.shop.ShopMaterialInfoGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenShopMaterialInfoListener implements Listener {

    @EventHandler
    public void onOpenShopMaterialInfo(OpenShopMaterialInfoEvent event) {
        Inventory menu = new ShopMaterialInfoGui(event.getPlayer(), event.isManageMode())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}