package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import leehj050211.mceconomy.event.shop.SelectShopCategoryEvent;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class ShopCategoryGui {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = spiGUI.create("메뉴 > 상점 ({currentPage}/{maxPage})", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i=0; i<ShopCategory.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getCategoryIcon(ShopCategory.values()[i]));
        }
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getCategoryIcon(ShopCategory category) {
        ItemStack icon = new ItemBuilder(category.getIcon())
                .name(category.getName())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectCategory(category));
    }

    private void selectCategory(ShopCategory category) {
        if (category.hasChildCategory()) {
            Bukkit.getPluginManager().callEvent(new SelectShopCategoryEvent(player, category));
        } else {
            ShopItemCategory itemCategory = ShopItemCategory.valueOf(category.name());
            Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemCategory));
        }
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            Bukkit.getPluginManager().callEvent(new OpenMenuEvent(player));
        });
    }
}
