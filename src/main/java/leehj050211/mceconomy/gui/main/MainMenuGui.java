package leehj050211.mceconomy.gui.main;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.event.job.OpenSelectJobEvent;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class MainMenuGui {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = spiGUI.create("메뉴", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setButton(11, getProfileMenu());
        sgMenu.setButton(13, getJobMenu());
        sgMenu.setButton(15, getShop());
        return sgMenu.getInventory();
    }

    private SGButton getProfileMenu() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(player))
                .name("프로필")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenSelectJobEvent(player));
                });
    }

    private SGButton getJobMenu() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name("직업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenSelectJobEvent(player));
                });
    }

    private SGButton getShop() {
        ItemStack icon = new ItemBuilder(Material.EMERALD)
                .name("상점")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenShopEvent(player));
                });
    }
}
