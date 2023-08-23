package leehj050211.mceconomy.event.shop.listener;

import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.gui.shop.ShopItemGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public class SelectShopItemCategoryListener implements Listener {

    @EventHandler
    public void onSelectShopCategory(SelectShopItemCategoryEvent event) {
        Inventory menu = new ShopItemGui(event.getPlayer(), event.getItemCategory())
                .getInventory();
        event.getPlayer().openInventory(menu);
    }


}