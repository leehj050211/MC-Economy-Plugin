package leehj050211.mceconomy.global.exception;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class CommandExceptionHandler implements CommandExecutor {

    private final CommandExecutor wrappedExecutor;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            return wrappedExecutor.onCommand(sender, command, label, args);
        } catch (Exception exception) {
            if (exception instanceof GeneralMCPlayerException mcPlayerException) {
                MCExceptionHandler.getInstance().handle(mcPlayerException);
                return true;
            } else {
                exception.printStackTrace();
                return false;
            }
        }
    }
}
