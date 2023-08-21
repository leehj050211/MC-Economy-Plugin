package leehj050211.mceconomy.gui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import com.samjakob.spigui.toolbar.SGToolbarButtonType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.Event;

@NoArgsConstructor
public class MenuToolbarProvider implements SGToolbarBuilder {

    private Integer prevButtonSlot = 3;
    private Integer nextButtonSlot = 5;
    private ToolbarButton[] toolbarButtons = {};

    public MenuToolbarProvider(Integer prevButtonSlot, Integer nextButtonSlot) {
        this.prevButtonSlot = prevButtonSlot;
        this.nextButtonSlot = nextButtonSlot;
    }

    public MenuToolbarProvider(Integer prevButtonSlot, Integer nextButtonSlot, ToolbarButton[] toolbarButtons) {
        this.prevButtonSlot = prevButtonSlot;
        this.nextButtonSlot = nextButtonSlot;
        this.toolbarButtons = toolbarButtons;
    }

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
        if (slot == prevButtonSlot && menu.getCurrentPage() > 0) {
            return getPrevButton(menu);
        }
        if (slot == nextButtonSlot && menu.getCurrentPage() < menu.getMaxPage() - 1) {
            return getNextButton(menu);
        }
        for (ToolbarButton button : toolbarButtons) {
            if (slot == button.getSlot()) {
                return button.getButton();
            }
        }
        return null;
    }
}
