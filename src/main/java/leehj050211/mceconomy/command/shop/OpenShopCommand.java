package leehj050211.mceconomy.command.shop;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.event.shop.OpenShopEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenShopCommand extends CustomCommandExecutor {

    @Override
    public String getName() {
        return "상점";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        Bukkit.getPluginManager().callEvent(new OpenShopEvent(player));
        return true;
    }
}
