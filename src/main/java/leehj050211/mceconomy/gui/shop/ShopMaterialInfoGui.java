package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuProvider;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ShopMaterialInfoGui {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = MenuProvider.pageableMenuGui().create("메뉴 > 상점 > 원자재 정보 ({currentPage}/{maxPage})", ROWS);
    private final Player player;
    private final boolean manageMode;

    private final ShopManager shopManager = ShopManager.getInstance();

    public Inventory getInventory() {
        List<ShopPriceCategory> priceCategories = shopManager.getAllPriceCategory();
        IntStream.range(0, priceCategories.size())
                .forEach(i  -> sgMenu.setButton(
                        ItemUtil.getPage(i, ROWS),
                        ItemUtil.getSlot(i, ROWS),
                        getCategoryIcon(priceCategories.get(i))));

        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getCategoryIcon(ShopPriceCategory priceCategory) {
        ItemStack icon = new ItemBuilder(priceCategory.getIcon())
                .name("&r&b원자재: " + priceCategory.getName())
                .lore(
                        "&r현재 가격: " + Formatter.formatMoney(priceCategory.getCurrentPrice(1)),
                        "&r정상 가격: " + Formatter.formatMoney(priceCategory.getNormalPrice(1)),
                        String.format("%s남은 물량: %s", (priceCategory.getAmount() > 0 ? "&6" : "&4"), Formatter.formatAmount(priceCategory.getAmount()))
                )
                .build();
        return new SGButton(icon)
                .withListener(event -> event.setResult(Event.Result.DENY));
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            Bukkit.getPluginManager().callEvent(new OpenShopEvent(player, manageMode));
        });
    }
}
