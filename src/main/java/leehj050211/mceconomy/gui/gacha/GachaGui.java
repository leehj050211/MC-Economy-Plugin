package leehj050211.mceconomy.gui.gacha;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.constant.IconConstant;
import leehj050211.mceconomy.domain.gacha.GachaItem;
import leehj050211.mceconomy.domain.gacha.diamond.DiamondGachaItem;
import leehj050211.mceconomy.domain.gacha.emerald.EmeraldGachaItem;
import leehj050211.mceconomy.domain.gacha.normal.NormalGachaItem;
import leehj050211.mceconomy.domain.gacha.type.GachaType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.CustomHeadUtil;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.global.util.ItemUtil;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import leehj050211.mceconomy.gui.ToolbarButton;
import leehj050211.mceconomy.global.util.CountDownTimer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static leehj050211.mceconomy.MCEconomy.spiGUI;

@RequiredArgsConstructor
public class GachaGui {

    private final PlayerManager playerManager = PlayerManager.getInstance();

    private static final int ROWS = 1;
    private final SGMenu sgMenu = spiGUI.create("메뉴 > 가챠(뽑기) ({currentPage}/{maxPage})", ROWS);
    private final Player player;

    public Inventory getInventory() {
        sgMenu.setAutomaticPaginationEnabled(true);

        for (int i = 0; i< GachaType.values().length; i++) {
            sgMenu.setButton(
                    ItemUtil.getPage(i, ROWS),
                    ItemUtil.getSlot(i, ROWS),
                    getGachaIcon(GachaType.values()[i]));
        }
        ToolbarButton[] buttons = {
                new ToolbarButton(1, getPrevMenuButton())
        };
        sgMenu.setToolbarBuilder(new MenuToolbarProvider(2, 3, buttons));
        return sgMenu.getInventory();
    }

    private SGButton getGachaIcon(GachaType gachaType) {
        ItemStack icon = new ItemBuilder(gachaType.getIcon())
                .name(gachaType.getName())
                .lore(Formatter.formatMoney(gachaType.getPrice()))
                .build();
        return new SGButton(icon)
                .withListener(event -> onClickGacha(gachaType));
    }

    private void onClickGacha(GachaType gachaType) {
        PlayerData playerData = playerManager.getData(player.getUniqueId());

        if (playerData.getMoney() < gachaType.getPrice()) {
            player.closeInventory();
            long lackMoney = gachaType.getPrice() - playerData.getMoney();
            player.sendMessage(ChatColor.DARK_RED + "에러: " + "돈이 " + lackMoney + "원 부족합니다.");
            return;
        }

        playerData.decreaseMoney(gachaType.getPrice());
        player.sendMessage(gachaType.getName() + "가 시작됩니다.");
        player.closeInventory();

        CountDownTimer timer = new CountDownTimer(MCEconomy.getInstance(), 3,
            () -> {
                Bukkit.broadcastMessage(
                        String.format("%s%s님이 %s를 시작했습니다!", ChatColor.YELLOW, playerData.getNickname(), gachaType.getName())
                );
            },
            () -> {
                GachaItem gachaItem = getRandomGachaItem(gachaType);
                ItemStack itemStack = new ItemStack(gachaItem.getIcon());
                player.getInventory().addItem(itemStack);

                String gachaMessage;
                if (gachaItem.getProbability() <= 0.1) {
                    gachaMessage = String.format("%s%s님이 %d%의 확률을 뚫고 %s을(를) 획득했습니다!",
                            ChatColor.DARK_AQUA,
                            playerData.getNickname(),
                            gachaItem.getProbability() * 100,
                            gachaItem.getName());
                } else {
                    gachaMessage = String.format("%s%s님이 %s을(를) 획득했습니다.",
                            ChatColor.YELLOW,
                            playerData.getNickname(),
                            gachaItem.getName());
                }

                Bukkit.broadcastMessage(gachaMessage);
            },
            (t) -> player.sendMessage(ChatColor.BLUE + "가챠 뽑는 중 " + t.getSecondsLeft())
        );

        timer.scheduleTimer();
    }

    private GachaItem getRandomGachaItem(GachaType gachaType) {
        return switch (gachaType) {
            case NORMAL_GACHA -> getRandomItem(NormalGachaItem.values());
            case EMERALD_GACHA -> getRandomItem(EmeraldGachaItem.values());
            case DIAMOND_GACHA -> getRandomItem(DiamondGachaItem.values());
        };
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

    private SGButton getPrevMenuButton() {
        return new SGButton(new ItemBuilder(CustomHeadUtil.getHead(IconConstant.BACKWARD))
                .name("&l이전 메뉴")
                .build()
        ).withListener(event -> {
            Bukkit.getPluginManager().callEvent(new OpenMenuEvent(player));
        });
    }
}
