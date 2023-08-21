package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
    private SGMenu sgMenu;

    private final Player player;
    private final ShopItemCategory itemCategory;
    private int amount = 1;

    public Inventory getInventory() {
        sgMenu = spiGUI.create(getMenuDepthTitle() + " ({currentPage}/{maxPage})", ROWS);
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

        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton()),
                new ToolbarButton(2, getUpdateAmountButton(1)),
                new ToolbarButton(3, getUpdateAmountButton(8)),
                new ToolbarButton(4, getPriceCategoryIcon(priceCategory)),
                new ToolbarButton(5, getUpdateAmountButton(16)),
                new ToolbarButton(6, getUpdateAmountButton(32)),
                new ToolbarButton(7, getUpdateAmountButton(64)),
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(0, 8, buttons));
        sgMenu.refreshInventory(player);
    }

    private SGButton getItem(ShopItemData itemData) {
        ItemStack item = new ItemBuilder(itemData.getMaterial())
                .lore(
                        "현재 가격: " + Formatter.formatMoney(itemData.getCurrentPrice(amount)),
                        "정상 가격: " + Formatter.formatMoney(itemData.getPrice(amount)))
                .amount(amount)
                .build();
        return new SGButton(item)
                .withListener(event -> {
                    event.setResult(Event.Result.DENY);
                    selectItem(itemData, amount);
                });
    }


    private void selectItem(ShopItemData itemData, int amount) {
        shopManager.buyItem(player, itemData.getMaterial(), amount);
        player.getInventory().addItem(new ItemStack(itemData.getMaterial(), amount));
        refresh();
    }

    private String getMenuDepthTitle() {
        if (itemCategory.getParentCategory().hasChildCategory()) {
            return String.format("상점 > %s > %s", itemCategory.getParentCategory().getName(), itemCategory.getName());
        } else {
            return "상점 > " + itemCategory.getName();
        }
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .name("이전 메뉴")
                .build()
        ).withListener(event -> {
            if (itemCategory.getParentCategory().hasChildCategory()) {
                Bukkit.getPluginManager().callEvent(new SelectShopCategoryEvent(player, itemCategory.getParentCategory()));
            } else {
                Bukkit.getPluginManager().callEvent(new OpenShopEvent(player));
            }
        });
    }

    private SGButton getUpdateAmountButton(int amount) {
        return new SGButton(new ItemBuilder(Material.CHEST_MINECART)
                .name(amount + "개")
                .amount(amount)
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            this.amount = amount;
            refresh();
        });
    }

    private SGButton getPriceCategoryIcon(ShopPriceCategory priceCategory) {
        return new SGButton(new ItemBuilder(Material.EMERALD)
                .name(String.format("%s%s상품 종류: %s", ChatColor.RESET, ChatColor.BOLD, priceCategory.getName()))
                .lore(
                        String.format("%s남은 물량: %s", (priceCategory.getAmount() > 0 ? ChatColor.GOLD : ChatColor.DARK_RED), Formatter.formatAmount(priceCategory.getAmount())),
                        String.format("%s수요: %s", ChatColor.RED, Formatter.formatAmount(priceCategory.getDemand())),
                        String.format("%s지난 주 수요: %s", ChatColor.RED, Formatter.formatAmount(priceCategory.getLastDemand())),
                        String.format("%s공급: %s",ChatColor.GREEN, Formatter.formatAmount(priceCategory.getSupply())),
                        String.format("%s지난 주 공급: %s", ChatColor.GREEN, Formatter.formatAmount(priceCategory.getLastSupply()))
                )
                .build()
        ).withListener(event -> event.setResult(Event.Result.DENY));
    }
}
