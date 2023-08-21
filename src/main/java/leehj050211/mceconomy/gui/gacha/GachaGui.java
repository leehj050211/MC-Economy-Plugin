package leehj050211.mceconomy.gui.gacha;

import java.util.List;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.MenuConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.gacha.type.GachaType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import leehj050211.mceconomy.exception.money.NotEnoughMoneyException;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.ItemUtil;
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

public class GachaGui extends CustomGui {

    private final PlayerManager playerManager = PlayerManager.getInstance();

    private final PlayerDao playerDao = PlayerDao.getInstance();

    private final int pageSize = 1;

    public GachaGui() {
        super(MenuId.SELECT_GACHA);
    }

    @EventHandler
    public void onOpenGacha(OpenGachaEvent event) {
        openPage(event.player, null, 1);
    }

    @Override
    protected void openPage(Player player, String subId, int currentPage) {
        ItemMenu[] itemMenus = new ItemMenu[GachaType.values().length];
        for (int i=0; i<GachaType.values().length; i++) {
            itemMenus[i] = new ItemMenu(i, getGachaIcon(GachaType.values()[i]));
        }

        openMenu(player, pageSize, currentPage, subId, "가챠 선택", itemMenus);
    }

    private static ItemStack getGachaIcon(GachaType gachaType) {
        ItemStack icon = new ItemStack(gachaType.getIcon(), 1);
        ItemMeta meta = icon.getItemMeta();

        ItemUtil.setItemData(meta, MenuConstant.SELECT_JOB_KEY, PersistentDataType.STRING, gachaType.name());
        meta.setDisplayName(gachaType.getName());
        meta.setLore(List.of(gachaType.getDescription()));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Player player,
        ItemStack item, String subId, int currentPage) {
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(), MenuConstant.SELECT_JOB_KEY);

        GachaType gachaType = GachaType.valueOf(data.get(key, PersistentDataType.STRING));

        PlayerData playerData = playerManager.getData(player.getUniqueId());
        if (gachaType == GachaType.NORMAL_GACHA) {
            if (playerData.getMoney() < 100L) {
                throw new NotEnoughMoneyException(player.getUniqueId(), 100L - playerData.getMoney());
            }
            playerData.decreaseMoney(100L);
        }
        if (gachaType == GachaType.EMERALD_GACHA) {
            if (playerData.getMoney() < 1000L) {
                throw new NotEnoughMoneyException(player.getUniqueId(), 1000L - playerData.getMoney());
            }
            playerData.decreaseMoney(1000L);
        }
        if (gachaType == GachaType.DIAMOND_GACHA) {
            if (playerData.getMoney() < 10000L) {
                throw new NotEnoughMoneyException(player.getUniqueId(), 10000L - playerData.getMoney());
            }
            playerData.decreaseMoney(10000L);
        }

        playerDao.update(playerData);
        player.sendMessage(gachaType.getName() + "가 시작됩니다.");
        player.closeInventory();
    }

}
