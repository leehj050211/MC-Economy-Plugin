package leehj050211.mceconomy.gui;

import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.global.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public abstract class CustomGui implements Listener {

    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final MenuId menuId;

    protected CustomGui(MenuId menuId) {
        this.menuId = menuId;
    }

    protected void openMenu(Player player, int pageSize, int currentPage,
                            String subId, String name, ItemMenu[] itemMenus) {
        String title = String.format("%s (%d/%d)", name, currentPage, pageSize);
        CustomInventoryHolder holder = new CustomInventoryHolder(menuId, subId, currentPage);
        Inventory inventory = Bukkit.createInventory(holder, Math.max(27, itemMenus.length), title);

        ItemStack prevPageItem = new ItemStack(Material.ARROW);
        ItemStack nextPageItem = new ItemStack(Material.ARROW);
        if (currentPage > 1) {
            inventory.setItem(18, prevPageItem);
        }
        if (currentPage < pageSize) {
            inventory.setItem(26, nextPageItem);
        }
        for (ItemMenu itemMenu : itemMenus) {
            inventory.setItem(itemMenu.getIndex(), itemMenu.getItem());
        }
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        InventoryHolder holder = Objects.requireNonNull(inventory).getHolder();
        if (!inventoryCheck(holder)) {
            return;
        }
        CustomInventoryHolder customHolder = (CustomInventoryHolder) holder;
        int currentPage = customHolder.currentPage();
        String subId = customHolder.subId();
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        String itemName = item.getType().name();
        if (itemName.equals(Material.ARROW.name())) {
            if (event.getSlot() == 18) {
                openPage(player, subId, currentPage - 1);
                return;
            } else if (event.getSlot() == 26) {
                openPage(player, subId, currentPage + 1);
                return;
            }
        }

        onClick(event, player, item, subId, currentPage);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        if (!inventoryCheck(holder)) {
            return;
        }
        event.setCancelled(true);
    }

    private boolean inventoryCheck(InventoryHolder holder) {
        return holder instanceof CustomInventoryHolder customHolder
                && customHolder.menuId() == this.menuId;
    }

    protected abstract void openPage(Player player, String subId, int currentPage);

    protected abstract void onClick(InventoryClickEvent event, Player player, ItemStack item, String subId, int currentPage);
}
