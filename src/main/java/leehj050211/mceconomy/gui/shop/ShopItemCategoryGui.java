package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.shop.SelectShopItemCategoryEvent;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuProvider;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public class ShopItemCategoryGui {

    private static final int ROWS = 3;
    private SGMenu sgMenu;

    private final Player player;
    private final ShopCategory category;

    public Inventory getInventory() {
        sgMenu = MenuProvider.pageableMenuGui().create(getMenuDepthTitle() + " ({currentPage}/{maxPage})", ROWS);
        sgMenu.setAutomaticPaginationEnabled(true);

        List<ShopItemCategory> filteredCategories = ShopItemCategory.getItemCategories(category);
        for (int i=0; i<filteredCategories.size(); i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getItemCategoryIcon(filteredCategories.get(i)));
        }
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getItemCategoryIcon(ShopItemCategory itemCategory) {
        ItemStack icon = new ItemBuilder(itemCategory.getIcon())
                .name(itemCategory.getName())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectItemCategory(itemCategory));
    }

    private void selectItemCategory(ShopItemCategory itemCategory) {
        Bukkit.getPluginManager().callEvent(new SelectShopItemCategoryEvent(player, itemCategory));
    }

    private String getMenuDepthTitle() {
        return "메뉴 > 상점 > " + category.getName();
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            Bukkit.getPluginManager().callEvent(new OpenShopEvent(player));
        });
    }
}
