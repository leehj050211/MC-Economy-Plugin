package leehj050211.mceconomy.gui.shop;

import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.Formatter;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import mc.obliviate.inventory.advancedslot.AdvancedSlot;
import mc.obliviate.inventory.advancedslot.AdvancedSlotManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class WholeSaleGui extends Gui {

    private static final int ROWS = 5;
    private final AdvancedSlotManager advancedSlotManager = new AdvancedSlotManager(this);
    private final ShopManager shopManager = ShopManager.getInstance();

    private final Map<Integer, ItemStack> itemStackMap = new HashMap<>();
    private List<ItemStack> itemStacks = new ArrayList<>();
    private long totalPrice = 0;

    public WholeSaleGui(Player player) {
        super(player, "WHOLE_SALE", "메뉴 > 상점 > 판매", ROWS + 1);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        fillGui(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        IntStream.range(0, ROWS * 9).forEach(idx -> {
            AdvancedSlot slot = advancedSlotManager.addAdvancedIcon(idx, new Icon(Material.AIR));
            setSlotEventListener(slot, idx);
        });

        updateTask(0, 20, task -> refreshGui());
    }

    private void refreshGui() {
        totalPrice = itemStacks.stream()
                .mapToLong(item -> shopManager.calcSellItem(item.getType(), item.getAmount()))
                .sum();
        addItem((ROWS * 9) + 4, getSellButton());
    }

    private void updateItemStacks() {
        itemStacks = itemStackMap.values().stream()
                .filter(Objects::nonNull)
                .toList();
    }

    private void setSlotEventListener(AdvancedSlot slot, int idx) {
        slot.onPrePutClick((e, item) -> {
            if (!shopManager.hasItem(item.getType())) {
                player.sendMessage(ErrorMsgConstant.InvalidSellItem);
                return true;
            }
            return false;
        }).onPut((e, item) -> {
            itemStackMap.put(idx, item);
            updateItemStacks();
            refreshGui();
        });
        slot.onPickup((e, item) -> {
            itemStackMap.remove(idx);
            updateItemStacks();
            refreshGui();
        });
    }

    private Icon getSellButton() {
        if (itemStacks.isEmpty()) {
            return new Icon(CustomHeadUtil.getHead(IconConstant.WRONG_MARK))
                    .setName("판매할 아이템을 선택해주세요.");
        }
        Icon icon = new Icon(CustomHeadUtil.getHead(IconConstant.CHECK_MARK))
                .setName("판매")
                .appendLore("판매 가격: " + Formatter.formatMoney(totalPrice));
        icon.onClick(event -> {
            Inventory inventory = getInventory();
            shopManager.sellItems(player, itemStacks);
            itemStackMap.clear();
            updateItemStacks();
            IntStream.range(0, ROWS * 9).forEach(inventory::clear);
        });
        return icon;
    }

}
