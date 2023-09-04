package leehj050211.mceconomy.gui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.toolbar.SGToolbarBuilder;
import com.samjakob.spigui.toolbar.SGToolbarButtonType;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import lombok.NoArgsConstructor;
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

    public MenuToolbarProvider(ToolbarButton[] toolbarButtons) {
        this.prevButtonSlot = -1;
        this.nextButtonSlot = -1;
        this.toolbarButtons = toolbarButtons;
    }

    public static SGButton getPrevButton(SGMenu menu) {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.LEFT_ARROW))
                .name("&a&l← 이전 페이지")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            menu.previousPage(event.getWhoClicked());
        });
    }

    public static SGButton getDisabledPrevButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.DISABLED_LEFT_ARROW))
                .name("&8&l← 이전 페이지")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
        });
    }

    public static SGButton getNextButton(SGMenu menu) {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.RIGHT_ARROW))
                .name("&a&l다음 페이지 →")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
            menu.nextPage(event.getWhoClicked());
        });
    }

    public static SGButton getDisabledNextButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.DISABLED_RIGHT_ARROW))
                .name("&8&l다음 페이지 →")
                .build()
        ).withListener(event -> {
            event.setResult(Event.Result.DENY);
        });
    }

    @Override
    public SGButton buildToolbarButton(int slot, int page, SGToolbarButtonType defaultType, SGMenu menu) {
        if (slot == prevButtonSlot) {
            if (menu.getCurrentPage() > 0) {
                return getPrevButton(menu);
            }
            return getDisabledPrevButton();
        }
        if (slot == nextButtonSlot) {
            if (menu.getCurrentPage() < menu.getMaxPage() - 1) {
                return getNextButton(menu);
            }
            return getDisabledNextButton();
        }
        for (ToolbarButton button : toolbarButtons) {
            if (slot == button.getSlot()) {
                return button.getButton();
            }
        }
        return null;
    }
}
