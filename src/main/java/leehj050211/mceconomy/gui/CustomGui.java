package leehj050211.mceconomy.gui;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.MenuConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.global.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public abstract class CustomGui implements Listener {

    private final MenuId menuId;
    private final int bottomMenuSize = 3;

    protected CustomGui(MenuId menuId) {
        this.menuId = menuId;
    }

    protected void openMenu(Player player, int pageSize, int currentPage,
                            String subId, String name, ItemMenu[] itemMenus) {
        String title = String.format("%s (%d/%d)", name, currentPage, pageSize);
        CustomInventoryHolder holder = new CustomInventoryHolder(menuId, subId, currentPage);

        int inventorySize = Math.max(27, itemMenus.length);
        int startBottomMenuIndex = inventorySize - 4 - Math.round(bottomMenuSize / 2f);
        Inventory inventory = Bukkit.createInventory(holder, inventorySize, title);

        if (currentPage > 1) {
            ItemStack prevPageIcon = new ItemStack(Material.ARROW);
            ItemMeta meta = prevPageIcon.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "이전 페이지");
            ItemUtil.setItemData(meta, MenuConstant.MOVE_PAGE_KEY, PersistentDataType.STRING, MenuConstant.PREV_PAGE);
            prevPageIcon.setItemMeta(meta);
            inventory.setItem(startBottomMenuIndex - 1, prevPageIcon);
        }
        if (currentPage < pageSize) {
            ItemStack nextPageIcon = new ItemStack(Material.ARROW);
            ItemMeta meta = nextPageIcon.getItemMeta();
            meta.setDisplayName(ChatColor.BOLD + "다음 페이지");
            ItemUtil.setItemData(meta, MenuConstant.MOVE_PAGE_KEY, PersistentDataType.STRING, MenuConstant.NEXT_PAGE);
            nextPageIcon.setItemMeta(meta);
            inventory.setItem(startBottomMenuIndex + bottomMenuSize, nextPageIcon);
        }
        for (ItemMenu itemMenu : itemMenus) {
            if (itemMenu.isBottomMenu()) {
                inventory.setItem(startBottomMenuIndex + itemMenu.getIndex(), itemMenu.getItem());
            } else {
                inventory.setItem(itemMenu.getIndex(), itemMenu.getItem());
            }
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
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();

        NamespacedKey onlyViewItemKey = new NamespacedKey(MCEconomy.getInstance(), MenuConstant.ONLY_VIEW_ITEM_KEY);
        if (Boolean.TRUE.equals(data.get(onlyViewItemKey, PersistentDataType.BOOLEAN))) {
            return;
        }

        NamespacedKey movePageKey = new NamespacedKey(MCEconomy.getInstance(), MenuConstant.MOVE_PAGE_KEY);
        String movePageData = data.get(movePageKey, PersistentDataType.STRING);
        if (Objects.equals(movePageData, MenuConstant.PREV_PAGE)) {
            openPage(player, subId, currentPage - 1);
            return;
        }
        if (Objects.equals(movePageData, MenuConstant.NEXT_PAGE)) {
            openPage(player, subId, currentPage + 1);
            return;
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
