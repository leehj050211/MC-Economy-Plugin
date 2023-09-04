package leehj050211.mceconomy.command.estate;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.global.estate.EstateManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetEstatePointCommand extends CustomCommandExecutor {

    @Override
    public String getName() {
        return "부동산위치";
    }

    private final EstateManager estateManager = EstateManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        Location location = player.getLocation();
        int step = Integer.parseInt(args[0]);
        switch (step) {
            case 1 -> {
                estateManager.setPoint(player, location, null);
                return true;
            }
            case 2 -> {
                estateManager.setPoint(player, null, location);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

}
