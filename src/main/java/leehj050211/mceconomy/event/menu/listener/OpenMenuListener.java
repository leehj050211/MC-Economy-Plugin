package leehj050211.mceconomy.event.menu.listener;

import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import leehj050211.mceconomy.gui.main.MainMenuGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenMenuListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(OpenMenuEvent event) {
        Inventory menu = new MainMenuGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}