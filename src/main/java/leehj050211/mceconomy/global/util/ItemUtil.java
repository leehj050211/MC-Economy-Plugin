package leehj050211.mceconomy.global.util;

import leehj050211.mceconomy.MCEconomy;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@UtilityClass
public class ItemUtil {

    public <T, Z> void setItemData(ItemMeta meta, String keyName,  PersistentDataType<T, Z> type, Z value) {
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), keyName);
        data.set(key, type, value);
    }

    public int getPage(int index, int rows) {
        return index / (9 * rows);
    }

    public int getSlot(int index, int rows) {
        return index % (9 * rows);
    }
}
