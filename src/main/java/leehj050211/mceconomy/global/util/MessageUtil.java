package leehj050211.mceconomy.global.util;

import leehj050211.mceconomy.domain.player.PlayerData;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class MessageUtil {

    public String getRemainingMoney(PlayerData playerData) {
        return ChatColor.GOLD + "잔액: " + Formatter.formatMoney(playerData.getMoney());
    }
}
