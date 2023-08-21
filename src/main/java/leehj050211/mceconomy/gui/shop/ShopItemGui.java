package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class ShopItemGui implements Listener {

    private static final ShopManager shopManager = ShopManager.getInstance();
    private static final int ROWS = 3;
    private final SGMenu sgMenu = spiGUI.create("구매할 상품 선택 ({currentPage}/{maxPage})", ROWS);

    private final Player player;
    private final ShopItemCategory itemCategory;

    public Inventory getInventory() {
        sgMenu.setAutomaticPaginationEnabled(true);
        AtomicReference<BukkitTask> refreshTask = new AtomicReference<>(new BukkitRunnable(){
            @Override
            public void run() {
                refresh();
            }
        }.runTaskTimer(MCEconomy.getInstance(), 0, 20));

        sgMenu.setOnClose(menu -> {
            if (refreshTask.get() != null) refreshTask.get().cancel();
        });
        return sgMenu.getInventory();
    }

    private void refresh() {
        ShopPriceCategory priceCategory = shopManager.getPriceCategory(itemCategory);
        List<ShopItemData> filteredItemList = shopManager.getItemList(itemCategory);
        for (int i=0; i<filteredItemList.size(); i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getItem(filteredItemList.get(i)));
        }
        sgMenu.setToolbarBuilder(new ShopItemToolbarProvider(priceCategory));

        sgMenu.refreshInventory(player);
    }

    private SGButton getItem(ShopItemData itemData) {
        ItemStack item = new ItemBuilder(itemData.getMaterial())
                .lore(
                        "현재 가격: " + Formatter.formatMoney(itemData.getCurrentPrice()),
                        "정상 가격: " + Formatter.formatMoney(itemData.getPrice()))
                .build();
        return new SGButton(item)
                .withListener(event -> selectItem(itemData));
    }


    private void selectItem(ShopItemData itemData) {
        shopManager.buyItem(player, itemData.getMaterial());
        player.getInventory().addItem(new ItemStack(itemData.getMaterial()));
        refresh();
    }
}
