package leehj050211.mceconomy.gui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import com.samjakob.spigui.toolbar.SGToolbarButtonType;
import org.bukkit.Material;
import org.bukkit.event.Event;

public class MenuToolbarProvider implements SGToolbarBuilder {

    public static SGButton getPrevButton(SGMenu menu) {
        return new SGButton(new ItemBuilder(Material.ARROW)
                .name("&a&l← 이전 페이지")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            menu.previousPage(event.getWhoClicked());
        });
    }

    public static SGButton getNextButton(SGMenu menu) {
        return new SGButton(new ItemBuilder(Material.ARROW)
                .name("&a&l 다음 페이지 →")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            menu.nextPage(event.getWhoClicked());
        });
    }

    @Override
    public SGButton buildToolbarButton(int slot, int page, SGToolbarButtonType defaultType, SGMenu menu) {
        if (slot == 3 && menu.getCurrentPage() > 0) {
            return getPrevButton(menu);
        }
        if (slot == 5 && menu.getCurrentPage() < menu.getMaxPage() - 1) {
            return getNextButton(menu);
        }
        return null;
    }
}
