package leehj050211.mceconomy.gui;

import leehj050211.mceconomy.constant.MenuId;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

record CustomInventoryHolder(MenuId menuId, int currentPage) implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}