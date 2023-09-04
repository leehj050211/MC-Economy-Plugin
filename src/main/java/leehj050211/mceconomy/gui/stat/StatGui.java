package leehj050211.mceconomy.gui.stat;

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
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class StatGui {

    private static final int ROWS = 5;
    private final SGMenu sgMenu = MenuProvider.menuGui().create("메뉴 > 스탯", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setButton(10, getHp());
        sgMenu.setButton(12, getDef());
        sgMenu.setButton(14, getAtk());
        sgMenu.setButton(16, getSpeed());
        return sgMenu.getInventory();
    }

    private SGButton getHp() {
        ItemStack icon = new ItemBuilder(CustomHeadUtil.getHead(IconConstant.HEART))
                .name("체력")
                .lore("클릭해서 레벨 업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                });
    }

    private SGButton getDef() {
        ItemStack icon = new ItemBuilder(Material.SHIELD)
                .name("방어력")
                .lore("클릭해서 레벨 업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(10);
                });
    }

    private SGButton getAtk() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_SWORD)
                .name("공격력")
                .lore("클릭해서 레벨 업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2);
                });
    }

    private SGButton getSpeed() {
        ItemStack icon = new ItemBuilder(Material.DIAMOND_BOOTS)
                .name("스피드")
                .lore("클릭해서 레벨 업")
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
                });
    }
}
