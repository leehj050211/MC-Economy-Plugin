package leehj050211.mceconomy.global.exception;

import leehj050211.mceconomy.global.player.PlayerManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MCExceptionHandler {

    private static MCExceptionHandler instance;
    public static MCExceptionHandler getInstance() {
        if (instance == null) {
            instance = new MCExceptionHandler();
        }
        return instance;
    }

    private final PlayerManager playerManager = PlayerManager.getInstance();

    public GeneralMCPlayerException handle(GeneralMCPlayerException exception) {
        Player player = playerManager.getPlayer(exception.getUuid());
        String errorMsg = exception.getMessage();
        player.sendMessage(ChatColor.DARK_RED + "에러: " + errorMsg);
        return exception;
    }
}
