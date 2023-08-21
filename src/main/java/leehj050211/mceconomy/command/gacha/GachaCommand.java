package leehj050211.mceconomy.command.gacha;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.event.gacha.OpenGachaEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GachaCommand extends CustomCommandExecutor {

    @Override
    public String getName() {
        return "가챠";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        Bukkit.getPluginManager().callEvent(new OpenGachaEvent(player));
        return true;
    }

}
