package leehj050211.mceconomy.gui.job;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.event.job.OpenJobMenuEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class SelectJobGui {

    private static final PlayerManager playerManager = PlayerManager.getInstance();
    private static final PlayerDao playerDao = PlayerDao.getInstance();
    private static final int ROWS = 1;
    private final SGMenu sgMenu = spiGUI.create("메뉴 > 직업 > 직업 선택 (Page {currentPage}/{maxPage})", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i=0; i<JobType.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getJobIcon(JobType.values()[i]));
        }
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getJobIcon(JobType jobType) {
        ItemStack icon = new ItemBuilder(jobType.getIcon())
                .name(jobType.getName())
                .lore(jobType.getDescription())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectJob(jobType));
    }

    private void selectJob(JobType jobType) {
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        playerData.updateJob(jobType);
        playerDao.update(playerData);

        player.sendMessage(jobType.getName() + " 선택됨");
        player.closeInventory();
    }

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            Bukkit.getPluginManager().callEvent(new OpenJobMenuEvent(player));
        });
    }
}
