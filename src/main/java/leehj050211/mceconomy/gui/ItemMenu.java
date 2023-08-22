package leehj050211.mceconomy.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class ItemMenu {

    private final int index;
    private final ItemStack item;
    private final boolean isBottomMenu;

    public ItemMenu(int index, ItemStack item) {
        this.index = index;
        this.item = item;
        this.isBottomMenu = false;
    }
}
