package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.OpenWholeSaleEvent;
import leehj050211.mceconomy.gui.shop.WholeSaleGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Getter
@RequiredArgsConstructor
public class OpenWholeSaleListener implements Listener {

    @EventHandler
    public void onOpenWholeSale(OpenWholeSaleEvent event) {
        new WholeSaleGui(event.getPlayer()).open();
    }
}