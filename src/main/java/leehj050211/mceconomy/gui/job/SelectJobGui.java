package leehj050211.mceconomy.gui.job;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.JobConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.PlayerData;
import leehj050211.mceconomy.domain.type.JobType;
import leehj050211.mceconomy.event.job.OpenJobListEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.gui.CustomGui;
import leehj050211.mceconomy.gui.ItemMenu;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SelectJobGui extends CustomGui {

    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    private final int pageSize = 3;

    public SelectJobGui() {
        super(MenuId.SELECT_JOB);
    }

    @EventHandler
    public void onOpenJobList(OpenJobListEvent event) {
        openPage(event.player, 1);
    }

    @Override
    protected void openPage(Player player, int currentPage) {

        ItemMenu[] itemMenus = new ItemMenu[JobType.values().length];
        for (int i=0; i<JobType.values().length; i++) {
            itemMenus[i] = new ItemMenu(i, getJobIcon(i));
        }

        openMenu(player, pageSize, currentPage, "직업 선택", itemMenus);
    }

    private static ItemStack getJobIcon(int index) {
        JobType jobType = JobType.values()[index];
        ItemStack icon = new ItemStack(jobType.getIcon(), 1);
        ItemMeta meta = icon.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), JobConstant.SELECT_JOB_KEY);

        meta.setDisplayName(jobType.getName());
        icon.setLore(List.of(jobType.getDescription()));
        data.set(key, PersistentDataType.STRING, jobType.name());
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Player player, ItemStack item) {
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), JobConstant.SELECT_JOB_KEY);

        JobType jobType = JobType.valueOf(data.get(key, PersistentDataType.STRING));
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        playerData.updateJob(jobType);
        playerDao.update(playerData);

        player.sendMessage(jobType.getName() + " 선택됨");
        player.closeInventory();
    }

}
