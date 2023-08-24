package leehj050211.mceconomy.gui.job;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.event.job.OpenSelectJobEvent;
import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.world.WorldManager;
import leehj050211.mceconomy.gui.MenuProvider;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class JobMenuGui {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = MenuProvider.pageableMenuGui().create("메뉴 > 직업", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setButton(12, getSelectJobMenu());
        sgMenu.setButton(14, getTpWorkspaceMenu());
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getSelectJobMenu() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name("직업 선택")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenSelectJobEvent(player));
                });
    }

    private SGButton getTpWorkspaceMenu() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name("작업장으로 이동")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    WorldManager.getInstance().tpToWorkspace(player);
                    player.closeInventory();
                });
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
