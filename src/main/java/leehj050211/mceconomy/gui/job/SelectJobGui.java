package leehj050211.mceconomy.gui.job;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.contant.JobConstant;
import leehj050211.mceconomy.domain.type.JobType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SelectJobGui implements Listener {

    private static SelectJobGui instance;
    public static SelectJobGui getInstance() {
        if (instance == null) {
            instance = new SelectJobGui();
        }
        return instance;
    }

    @Getter
    private static final Inventory inventory;

    static {
        inventory = Bukkit.createInventory(null, Math.max(9, JobType.values().length), "직업 선택");

        for (JobType jobType : JobType.values()) {
            inventory.addItem(getJobItem(jobType));
        }
    }

    private static ItemStack getJobItem(JobType jobType) {
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
}
