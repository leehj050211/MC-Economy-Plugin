package leehj050211.mceconomy.event.shop;

import leehj050211.mceconomy.domain.shop.type.ShopCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class SelectShopCategoryEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ShopCategory category;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}