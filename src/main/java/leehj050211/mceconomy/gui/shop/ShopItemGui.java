package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

public class ShopItemGui implements Listener {

    private static final ShopManager shopManager = ShopManager.getInstance();
    private static final int ROWS = 3;

    @EventHandler
    public void onSelectShopCategory(SelectShopItemCategoryEvent event) {
        openMenu(event.getPlayer(), event.getItemCategory());
    }

    private void openMenu(Player player, ShopItemCategory itemCategory) {
        SGMenu sgMenu = spiGUI.create("구매할 상품 선택 ({currentPage}/{maxPage})", ROWS);
        sgMenu.setAutomaticPaginationEnabled(true);
        AtomicReference<BukkitTask> refreshTask = new AtomicReference<>(new BukkitRunnable(){
            @Override
            public void run() {
                refresh(player, sgMenu, itemCategory);
            }
        }.runTaskTimer(MCEconomy.getInstance(), 0, 20));
        player.openInventory(sgMenu.getInventory());

        sgMenu.setOnClose(menu -> {
            if (refreshTask.get() != null) refreshTask.get().cancel();
        });
    }

    private void refresh(Player player, SGMenu sgMenu, ShopItemCategory itemCategory) {
        ShopPriceCategory priceCategory = shopManager.getPriceCategory(itemCategory);
        List<ShopItemData> filteredItemList = shopManager.getItemList(itemCategory);
        for (int i=0; i<filteredItemList.size(); i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getItem(filteredItemList.get(i), sgMenu));
        }
        sgMenu.setToolbarBuilder(new ShopItemToolbarProvider(priceCategory));

        sgMenu.refreshInventory(player);
    }

    private SGButton getItem(ShopItemData itemData, SGMenu sgMenu) {
        ItemStack item = new ItemBuilder(itemData.getMaterial())
                .lore(
                        "현재 가격: " + Formatter.formatMoney(itemData.getCurrentPrice()),
                        "정상 가격: " + Formatter.formatMoney(itemData.getPrice()))
                .build();
        return new SGButton(item)
                .withListener(event -> selectItem(event, sgMenu, itemData));
    }


    private void selectItem(InventoryClickEvent event, SGMenu sgMenu, ShopItemData itemData) {
        Player player = (Player) event.getWhoClicked();
        shopManager.buyItem(player, itemData.getMaterial());
        player.getInventory().addItem(new ItemStack(itemData.getMaterial()));
        refresh(player, sgMenu, itemData.getItemCategory());
    }
}
