package leehj050211.mceconomy.gui.main;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import leehj050211.mceconomy.event.job.OpenJobMenuEvent;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.stat.OpenStatEvent;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.gui.MenuProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MainMenuGui {

    private static final int ROWS = 5;
    private final SGMenu sgMenu = MenuProvider.menuGui().create("메뉴", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setButton(11, getProfileMenu());
        sgMenu.setButton(13, getStat());
        sgMenu.setButton(15, getJobMenu());
        sgMenu.setButton(30, getShop());
        sgMenu.setButton(32, getGacha());
        return sgMenu.getInventory();
    }

    private SGButton getProfileMenu() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(player))
                .name("프로필")
                .build();
        return new SGButton(icon)
                .withListener(event -> {});
    }

    private SGButton getStat() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(IconConstant.HEART))
                .name("스탯")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenStatEvent(player));
                });
    }

    private SGButton getJobMenu() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name("직업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenJobMenuEvent(player));
                });
    }

    private SGButton getShop() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(IconConstant.SHOP))
                .name("상점")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenShopEvent(player, false));
                });
    }

    private SGButton getGacha() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND)
                .name("가챠(뽑기)")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    Bukkit.getPluginManager().callEvent(new OpenGachaEvent(player));
                });
    }
}
