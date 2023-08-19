package leehj050211.mceconomy.command.money;

import leehj050211.mceconomy.command.CustomCommandExecutor;
import leehj050211.mceconomy.constant.ErrorMsgConstant;
import leehj050211.mceconomy.domain.PlayerData;
import leehj050211.mceconomy.exception.money.InvalidSendMoneyTargetException;
import leehj050211.mceconomy.global.exception.OfflineTargetPlayerException;
import leehj050211.mceconomy.global.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SendMoneyCommand extends CustomCommandExecutor {

    private final PlayerManager playerManager = PlayerManager.getInstance();

    @Override
    public String getName() {
        return "송금";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMsgConstant.InvalidCommandExecutor);
            return true;
        }
        if (args.length != 2) {
            return false;
        }
        Long sendAmount = Long.valueOf(args[1]);
        String targetNickname = args[0];
        Player player = ((Player) sender).getPlayer();
        if (Objects.equals(player.getName(), targetNickname)) {
            throw new InvalidSendMoneyTargetException(player.getUniqueId());
        }
        Player target = Bukkit.getPlayer(targetNickname);
        if (target == null) {
            throw new OfflineTargetPlayerException(player.getUniqueId());
        }

        PlayerData playerData = playerManager.getData(player.getUniqueId());
        PlayerData targetPlayerData = playerManager.getData(targetNickname);
        playerData.decreaseMoney(sendAmount);
        targetPlayerData.increaseMoney(sendAmount);
        player.sendMessage(String.format("%d원 송금 나 -> %s\n잔액: %d원", sendAmount, targetNickname, playerData.getMoney()));
        target.sendMessage(String.format("%d원 입금 %s -> 나\n잔액: %d원", sendAmount, playerData.getNickname(), targetPlayerData.getMoney()));
        return true;
    }
}
