package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.shop.OpenShopItemManageEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.global.util.ShopUtil;
import leehj050211.mceconomy.gui.MenuProvider;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class ShopItemGui implements Listener {

    private static final int ROWS = 3;
    private SGMenu sgMenu;
    private final Player player;

    private static final ShopManager shopManager = ShopManager.getInstance();
    private final ShopItemCategory itemCategory;
    private boolean manageMode;
    private int amount = 1;

    public ShopItemGui(Player player, ShopItemCategory itemCategory, boolean manageMode) {
        this.player = player;
        this.itemCategory = itemCategory;
        this.manageMode = manageMode;
    }

    public Inventory getInventory() {
        sgMenu = MenuProvider.pageableMenuGui().create(getMenuDepthTitle() + " ({currentPage}/{maxPage})", ROWS);
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
        List<ShopItemData> filteredItemList = shopManager.getItemList(itemCategory);
        IntStream.range(0, filteredItemList.size())
                .forEach(i  -> sgMenu.setButton(
                        ItemUtil.getPage(i, ROWS),
                        ItemUtil.getSlot(i, ROWS),
                        getItem(filteredItemList.get(i))));

        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton()),
                new ToolbarButton(4, getUpdateAmountButton(1)),
                new ToolbarButton(5, getUpdateAmountButton(8)),
                new ToolbarButton(6, getUpdateAmountButton(16)),
                new ToolbarButton(7, getUpdateAmountButton(64)),
                new ToolbarButton(8, getToggleManageModeButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        sgMenu.refreshInventory(player);
    }

    private SGButton getItem(ShopItemData itemData) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        ItemStack item = new ItemBuilder(itemData.getMaterial())
                .lore(
                        "&r현재 가격: " + Formatter.formatMoney(ShopUtil.getCurrentPrice(itemData, amount)),
                        "&r정상 가격: " + Formatter.formatMoney(ShopUtil.getNormalPrice(itemData, amount)),
                        String.format("%s남은 물량: %s", (priceCategory.getAmount() > 0 ? "&6" : "&4"), Formatter.formatAmount(priceCategory.getAmount())),
                        String.format("%s구매 후 물량: %s", (priceCategory.getAmount() - amount > 0 ? "&6" : "&4"), Formatter.formatAmount(priceCategory.getAmount() - amount)),
                        String.format("&r&b원자재: %s", priceCategory.getName()))
                .amount(amount)
                .build();
        return new SGButton(item)
                .withListener(event -> {
                    event.setResult(Event.Result.DENY);
                    selectItem(event, itemData, amount);
                });
    }

    private void selectItem(InventoryClickEvent event, ShopItemData itemData, int amount) {
        if (manageMode) {
            Bukkit.getPluginManager().callEvent(new OpenShopItemManageEvent(player, itemData));
            return;
        }

        shopManager.buyItem(player, itemData.getMaterial(), amount);
        player.getInventory().addItem(new ItemStack(itemData.getMaterial(), amount));
        refresh();
        event.setResult(Event.Result.DENY);
    }

    private String getMenuDepthTitle() {
        if (itemCategory.getParentCategory().hasChildCategory()) {
            return String.format("메뉴 > 상점 > %s > %s", itemCategory.getParentCategory().getName(), itemCategory.getName());
        }
        return "메뉴 > 상점 > " + itemCategory.getName();
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            if (itemCategory.getParentCategory().hasChildCategory()) {
                Bukkit.getPluginManager().callEvent(new SelectShopCategoryEvent(player, itemCategory.getParentCategory(), manageMode));
                return;
            }
            Bukkit.getPluginManager().callEvent(new OpenShopEvent(player, manageMode));
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

    private SGButton getToggleManageModeButton() {
        if (!player.isOp()) {
            return null;
        }
        return new SGButton(new ItemBuilder(Material.STICK)
                .name(manageMode ? "관리모드 켜짐" : "관리모드 꺼짐")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            manageMode = !manageMode;
            refresh();
        });
    }
}
