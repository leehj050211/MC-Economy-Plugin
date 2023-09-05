package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.dao.ShopItemDao;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
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

@RequiredArgsConstructor
public class ShopItemManageGui implements Listener {

    private static final int ROWS = 5;
    private SGMenu sgMenu = MenuProvider.pageableMenuGui().create("아이템 가격 설정", ROWS);;
    private final Player player;
    private final ShopItemDao shopItemDao = ShopItemDao.getInstance();

    private final ShopItemData itemData;
    private ShopPriceCategory priceCategory;
    private long price;

    public Inventory getInventory() {
        price = itemData.getPrice();
        priceCategory = itemData.getPriceCategory();

        sgMenu.setAutomaticPaginationEnabled(true);
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton()),
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(buttons));

        refresh();
        return sgMenu.getInventory();
    }

    private void refresh() {
        sgMenu.setButton(22, getItem());

        sgMenu.setButton(1, getDecreasePriceButton(50));
        sgMenu.setButton(10, getDecreasePriceButton(100));
        sgMenu.setButton(19, getDecreasePriceButton(1000));
        sgMenu.setButton(28, getDecreasePriceButton(10000));
        sgMenu.setButton(37, getDecreasePriceButton(100000));

        sgMenu.setButton(7, getIncreasePriceButton(50));
        sgMenu.setButton(16, getIncreasePriceButton(100));
        sgMenu.setButton(25, getIncreasePriceButton(1000));
        sgMenu.setButton(34, getIncreasePriceButton(10000));
        sgMenu.setButton(43, getIncreasePriceButton(100000));

        sgMenu.setName("아이템 가격 설정 - " + Formatter.formatMoney(this.price + priceCategory.getBasePrice()));
        sgMenu.refreshInventory(player);
    }

    private SGButton getItem() {
        ItemStack item = new ItemBuilder(itemData.getMaterial())
                .lore(
                        "&r총 가격: " + Formatter.formatMoney(this.price + priceCategory.getBasePrice()),
                        "&r원자재 가격: " + Formatter.formatMoney(priceCategory.getBasePrice()),
                        "&r아이템 개별 가격: " + Formatter.formatMoney(this.price),
                        String.format("&r&b원자재: %s", priceCategory.getName()),
                        "&r클릭해서 가격 적용"
                )
                .build();
        return new SGButton(item)
                .withListener(event -> {
                    event.setResult(Event.Result.DENY);
                    itemData.setPrice(this.price);
                    shopItemDao.update(itemData);
                    Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemData.getItemCategory(), true));
                });
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemData.getItemCategory(), true));
        });
    }

    private SGButton getIncreasePriceButton(int price) {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.PLUS))
                .name(Formatter.formatMoney(price) + " 추가")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            this.price += price;
            refresh();
        });
    }

    private SGButton getDecreasePriceButton(int price) {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.MINUS))
                .name(Formatter.formatMoney(price) + " 감소")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            this.price -= price;
            refresh();
        });
    }
}
