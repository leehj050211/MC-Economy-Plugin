package leehj050211.mceconomy.gui.job;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class SelectJobGui {

    private static final PlayerManager playerManager = PlayerManager.getInstance();
    private static final PlayerDao playerDao = PlayerDao.getInstance();
    private static final int ROWS = 1;
    private final SGMenu sgMenu = spiGUI.create("직업 선택 test (Page {currentPage}/{maxPage})", ROWS);

    private final Player player;

    public Inventory getInventory() {
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i=0; i<JobType.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getJobIcon(JobType.values()[i]));
        }
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
}
