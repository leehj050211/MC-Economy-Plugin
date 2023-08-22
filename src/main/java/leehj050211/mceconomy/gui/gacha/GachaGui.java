package leehj050211.mceconomy.gui.gacha;

import java.util.List;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.MenuConstant;
import leehj050211.mceconomy.constant.MenuId;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.domain.gacha.GachaItem;
import leehj050211.mceconomy.domain.gacha.diamond.DiamondGachaItem;
import leehj050211.mceconomy.domain.gacha.emerald.EmeraldGachaItem;
import leehj050211.mceconomy.domain.gacha.normal.NormalGachaItem;
import leehj050211.mceconomy.domain.gacha.type.GachaType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.CustomGui;
import leehj050211.mceconomy.gui.ItemMenu;
import leehj050211.mceconomy.util.CountDownTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        for (int i = 0; i < GachaType.values().length; i++) {
            itemMenus[i] = new ItemMenu(i, getGachaIcon(GachaType.values()[i]));
        }

        openMenu(player, pageSize, currentPage, subId, "가챠 선택", itemMenus);
    }

    private static ItemStack getGachaIcon(GachaType gachaType) {
        ItemStack icon = new ItemStack(gachaType.getIcon(), 1);
        ItemMeta meta = icon.getItemMeta();

        ItemUtil.setItemData(meta, MenuConstant.SELECT_GACHA_KEY, PersistentDataType.STRING,
            gachaType.name());
        meta.setDisplayName(gachaType.getName());
        meta.setLore(List.of(gachaType.getPrice() + "원"));
        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Player player,
        ItemStack item, String subId, int currentPage) {
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(MCEconomy.getInstance(),
            MenuConstant.SELECT_GACHA_KEY);

        GachaType gachaType = GachaType.valueOf(data.get(key, PersistentDataType.STRING));

        PlayerData playerData = playerManager.getData(player.getUniqueId());

        if (playerData.getMoney() < gachaType.getPrice()) {
            player.closeInventory();
            long lackMoney = gachaType.getPrice() - playerData.getMoney();
            player.sendMessage(ChatColor.DARK_RED + "에러: " + "돈이 " + lackMoney + "원 부족합니다.");
            return;
        }

        playerData.decreaseMoney(gachaType.getPrice());
        playerDao.update(playerData);

        player.sendMessage(gachaType.getName() + "가 시작됩니다.");
        player.closeInventory();

        CountDownTimer timer = new CountDownTimer(MCEconomy.getInstance(),
            3,
            () -> Bukkit.broadcastMessage(
                ChatColor.YELLOW + playerData.getNickname() + "님이 " + gachaType.getName()
                    + "를 시작했습니다!"),
            () -> {
                GachaItem gachaItem = getRandomGachaItem(gachaType);

                ItemStack itemStack = new ItemStack(gachaItem.getIcon());
                player.getInventory().addItem(itemStack);

                String gachaMessage = "";
                if (gachaItem.getProbability() <= 0.1) {
                    gachaMessage += ChatColor.DARK_AQUA + playerData.getNickname() + "님이 "
                        + gachaItem.getProbability() * 100 + "%의 확률을 뚫고 \"" + gachaItem.getName()
                        + "\" 을/를 획득했습니다.";
                } else {
                    gachaMessage +=
                        ChatColor.YELLOW + playerData.getNickname() + "님이 \"" + gachaItem.getName()
                            + "\" 을/를 획득했습니다.";
                }

                Bukkit.broadcastMessage(
                    gachaMessage);

            },
            (t) -> player.sendMessage(ChatColor.BLUE + "가챠 뽑는중 " + t.getSecondsLeft())
        );

        timer.scheduleTimer();
    }

    private GachaItem getRandomGachaItem(GachaType gachaType) {
        switch (gachaType) {
            case NORMAL_GACHA:
                return getRandomItem(NormalGachaItem.values());
            case EMERALD_GACHA:
                return getRandomItem(EmeraldGachaItem.values());
            case DIAMOND_GACHA:
                return getRandomItem(DiamondGachaItem.values());
            default:
                return null;
        }
    }

    private <T extends GachaItem> GachaItem getRandomItem(T[] possibleItems) {
        double randomValue = Math.random();
        double cumulativeProbability = 0.0;

        for (T gachaItem : possibleItems) {
            cumulativeProbability += gachaItem.getProbability();

            if (randomValue <= cumulativeProbability) {
                return gachaItem;
            }
        }

        return null;
    }

}
