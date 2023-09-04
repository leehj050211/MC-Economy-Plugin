package leehj050211.mceconomy.gui.stat;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import leehj050211.mceconomy.event.job.OpenJobMenuEvent;
import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import leehj050211.mceconomy.event.stat.OpenStatEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.StatUtil;
import leehj050211.mceconomy.gui.MenuProvider;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class StatGui {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = MenuProvider.pageableMenuGui().create("메뉴 > 스탯", ROWS);

    private final Player player;
    private PlayerData playerData;

    public Inventory getInventory() {
        playerData = PlayerManager.getInstance().getData(player.getUniqueId());

        sgMenu.setAutomaticPaginationEnabled(true);
        refresh();
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(buttons));
        return sgMenu.getInventory();
    }

    private void refresh() {
        sgMenu.setButton(10, getHealth());
        sgMenu.setButton(12, getArmor());
        sgMenu.setButton(14, getAttack());
        sgMenu.setButton(16, getSpeed());
        sgMenu.refreshInventory(player);
    }

    private SGButton getHealth() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(IconConstant.HEART))
                .name("&r체력")
                .lore(
                        "레벨: " + playerData.getHealthLevel(),
                        "레벨업 비용: " + Formatter.formatMoney(StatUtil.calcPrice(playerData.getHealthLevel()))
                )
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    StatUtil.levelUpHealth(player, playerData);
                    refresh();
                });
    }

    private SGButton getArmor() {
        ItemStack icon = new ItemBuilder(Material.SHIELD)
                .name("&r방어력")
                .lore(
                        "레벨: " + playerData.getArmorLevel(),
                        "레벨업 비용: " + Formatter.formatMoney(StatUtil.calcPrice(playerData.getArmorLevel())))
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    StatUtil.levelUpArmor(player, playerData);
                    refresh();
                });
    }

    private SGButton getAttack() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_SWORD)
                .name("&r공격력")
                .lore(
                        "레벨: " + playerData.getAttackLevel(),
                        "레벨업 비용: " + Formatter.formatMoney(StatUtil.calcPrice(playerData.getAttackLevel())))
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    StatUtil.levelUpAttack(player, playerData);
                    refresh();
                });
    }

    private SGButton getSpeed() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_BOOTS)
                .name("&r스피드")
                .lore(
                        "레벨: " + playerData.getSpeedLevel(),
                        "레벨업 비용: " + Formatter.formatMoney(StatUtil.calcPrice(playerData.getSpeedLevel())))
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    StatUtil.levelUpSpeed(player, playerData);
                    refresh();
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
