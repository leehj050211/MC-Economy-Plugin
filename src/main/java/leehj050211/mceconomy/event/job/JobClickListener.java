package leehj050211.mceconomy.event.job;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.contant.JobConstant;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.PlayerData;
import leehj050211.mceconomy.domain.type.JobType;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.gui.job.SelectJobGui;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JobClickListener implements Listener {

    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    @EventHandler
    public void onOpenJobList(OpenJobListEvent event) {
        event.player.openInventory(SelectJobGui.getInventory());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() != SelectJobGui.getInventory()) return;
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), JobConstant.SELECT_JOB_KEY);
        JobType jobType = JobType.valueOf(data.get(key, PersistentDataType.STRING));

        Player player = (Player) e.getWhoClicked();
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        playerData.updateJob(jobType);
        playerDao.update(playerData);

        player.sendMessage(jobType.getName() + " 선택됨");
        player.closeInventory();
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory() == SelectJobGui.getInventory()) {
            e.setCancelled(true);
        }
    }
}
