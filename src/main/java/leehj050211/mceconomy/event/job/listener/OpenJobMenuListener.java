package leehj050211.mceconomy.event.job.listener;

import leehj050211.mceconomy.event.job.OpenJobMenuEvent;
import leehj050211.mceconomy.gui.job.JobMenuGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenJobMenuListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(OpenJobMenuEvent event) {
        Inventory menu = new JobMenuGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}