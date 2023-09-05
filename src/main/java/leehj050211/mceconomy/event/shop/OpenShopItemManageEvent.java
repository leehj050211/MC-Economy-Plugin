package leehj050211.mceconomy.event.shop;

import leehj050211.mceconomy.domain.shop.ShopItemData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class OpenShopItemManageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ShopItemData itemData;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}