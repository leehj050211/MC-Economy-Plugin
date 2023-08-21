package leehj050211.mceconomy.gui.shop;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import com.samjakob.spigui.toolbar.SGToolbarButtonType;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;

@RequiredArgsConstructor
public class ShopItemToolbarProvider implements SGToolbarBuilder {

    private final ShopPriceCategory category;

    @Override
    public SGButton buildToolbarButton(int slot, int page, SGToolbarButtonType defaultType, SGMenu menu) {
        if (slot == 2 && menu.getCurrentPage() > 0) {
            return MenuToolbarProvider.getPrevButton(menu);
        }
        if (slot == 6 && menu.getCurrentPage() < menu.getMaxPage() - 1) {
            return MenuToolbarProvider.getNextButton(menu);
        }

        if (slot == 4) {
            return new SGButton(new ItemBuilder(Material.EMERALD)
                    .name(String.format("%s%s상품 종류: %s", ChatColor.RESET, ChatColor.BOLD, category.getName()))
                    .lore(
                            String.format("%s남은 물량: %s", (category.getAmount() > 0 ? ChatColor.GOLD : ChatColor.DARK_RED), Formatter.formatAmount(category.getAmount())),
                            String.format("%s수요: %s", ChatColor.RED, Formatter.formatAmount(category.getDemand())),
                            String.format("%s지난 주 수요: %s", ChatColor.RED, Formatter.formatAmount(category.getLastDemand())),
                            String.format("%s공급: %s",ChatColor.GREEN, Formatter.formatAmount(category.getSupply())),
                            String.format("%s지난 주 공급: %s", ChatColor.GREEN, Formatter.formatAmount(category.getLastSupply()))
                    )
                    .build()
            ).withListener(event -> {
                event.setResult(Event.Result.DENY);
            });
        }
        return null;
    }
}
