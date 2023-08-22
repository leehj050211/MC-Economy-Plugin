package leehj050211.mceconomy.command.menu;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.event.menu.OpenMenuEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenMenuCommand extends CustomCommandExecutor {

    @Override
    public String getName() {
        return "메뉴";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        Bukkit.getPluginManager().callEvent(new OpenMenuEvent(player));
        return true;
    }
}
