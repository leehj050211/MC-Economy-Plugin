package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.OpenWholeSaleEvent;
import leehj050211.mceconomy.gui.shop.WholeSaleGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenWholeSaleListener implements Listener {

    @EventHandler
    public void onOpenWholeSale(OpenWholeSaleEvent event) {
        Inventory menu = new WholeSaleGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }

    @EventHandler()
    public void onInventoryDrag(InventoryDragEvent event) {
        System.out.println("test");
        event.setResult(Event.Result.ALLOW);
    }
}