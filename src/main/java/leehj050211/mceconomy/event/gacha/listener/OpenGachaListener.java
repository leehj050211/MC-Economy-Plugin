package leehj050211.mceconomy.event.gacha.listener;

import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import leehj050211.mceconomy.gui.gacha.GachaGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class OpenGachaListener implements Listener {

    @EventHandler
    public void onOpenGacha(OpenGachaEvent event) {
        Inventory menu = new GachaGui(event.getPlayer())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }
}