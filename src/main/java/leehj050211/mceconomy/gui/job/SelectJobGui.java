package leehj050211.mceconomy.gui.job;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.event.job.OpenJobListEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

public class SelectJobGui implements Listener {

    private static final PlayerManager playerManager = PlayerManager.getInstance();
    private static final PlayerDao playerDao = PlayerDao.getInstance();
    private static final int ROWS = 1;

    @EventHandler
    public void onOpenJobList(OpenJobListEvent event) {
        openMenu(event.player);
    }

    private void openMenu(Player player) {
        SGMenu sgMenu = spiGUI.create("직업 선택 test (Page {currentPage}/{maxPage})", ROWS);
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i=0; i<JobType.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getJobIcon(JobType.values()[i]));
        }
        player.openInventory(sgMenu.getInventory());
    }

    private static SGButton getJobIcon(JobType jobType) {
        ItemStack icon = new ItemBuilder(jobType.getIcon())
                .name(jobType.getName())
                .lore(jobType.getDescription())
                .build();
        return new SGButton(icon)
                .withListener(event -> selectJob(event, jobType));
    }

    private static void selectJob(InventoryClickEvent event, JobType jobType) {
        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        playerData.updateJob(jobType);
        playerDao.update(playerData);

        player.sendMessage(jobType.getName() + " 선택됨");
        player.closeInventory();
    }
}
