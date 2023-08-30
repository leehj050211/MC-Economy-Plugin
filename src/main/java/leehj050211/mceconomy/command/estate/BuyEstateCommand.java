package leehj050211.mceconomy.command.estate;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.global.estate.EstateManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyEstateCommand extends CustomCommandExecutor {

    @Override
    public String getName() {
        return "부동산구매";
    }

    private final EstateManager estateManager = EstateManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        estateManager.buyEstate(player);
        return true;
    }

}
