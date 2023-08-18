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
        } catch (GeneralMCPlayerException exception) {
            MCExceptionHandler.getInstance().handle(exception);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
