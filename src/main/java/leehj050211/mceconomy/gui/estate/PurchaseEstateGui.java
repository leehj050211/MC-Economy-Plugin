package leehj050211.mceconomy.gui.estate;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import leehj050211.mceconomy.global.estate.EstateManager;
import leehj050211.mceconomy.global.util.EstateUtil;
import leehj050211.mceconomy.global.util.Formatter;
import leehj050211.mceconomy.gui.MenuProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PurchaseEstateGui implements Listener {

    private static final int ROWS = 3;
    private final SGMenu sgMenu = MenuProvider.menuGui().create("부동산 구매", ROWS);
    private final Player player;

    private static final EstateManager estateManager = EstateManager.getInstance();

    public Inventory getInventory() {
        sgMenu.setButton(13, getPurchaseButton());
        return sgMenu.getInventory();
    }

    private SGButton getPurchaseButton() {
        long estateSize = estateManager.getEstateSize(player);
        long estatePrice = EstateUtil.getEstatePrice(estateSize);

        ItemStack icon = new ItemBuilder(Material.EMERALD)
                .name("구매하기")
                .lore(
                        String.format("가격: %s", Formatter.formatMoney(estatePrice)),
                        String.format("땅 크기: %d", estateSize)
                )
                .build();
        return new SGButton(icon)
                .withListener(event -> {
                    estateManager.buyEstate(player);
                    player.closeInventory();
                });
    }

}
