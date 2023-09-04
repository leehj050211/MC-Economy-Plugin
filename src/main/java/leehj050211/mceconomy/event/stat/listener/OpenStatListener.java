package leehj050211.mceconomy.event.stat.listener;

import leehj050211.mceconomy.event.stat.OpenStatEvent;
import leehj050211.mceconomy.gui.stat.StatGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenStatListener implements Listener {

    @EventHandler
    public void onOpenStat(OpenStatEvent event) {
        Inventory menu = new StatGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}