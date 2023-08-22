package leehj050211.mceconomy.event.job.listener;

import leehj050211.mceconomy.event.job.OpenSelectJobEvent;
import leehj050211.mceconomy.gui.job.SelectJobGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenSelectJobListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(OpenSelectJobEvent event) {
        Inventory menu = new SelectJobGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}