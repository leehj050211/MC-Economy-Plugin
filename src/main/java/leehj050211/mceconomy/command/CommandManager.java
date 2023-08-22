package leehj050211.mceconomy.command;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.command.job.TpWorkspaceCommand;
import leehj050211.mceconomy.command.menu.OpenMenuCommand;
import leehj050211.mceconomy.command.money.SendMoneyCommand;
import leehj050211.mceconomy.command.shop.OpenShopCommand;
import leehj050211.mceconomy.global.exception.CommandExceptionHandler;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class CommandManager {

    public static void registerCommands() {
        Plugin plugin = MCEconomy.getInstance();
        if (plugin == null) {
            return;
        }
        Server server = plugin.getServer();
        CustomCommandExecutor[] executors = {
                new OpenMenuCommand(),
                new SendMoneyCommand(),
                new OpenShopCommand(),
                new TpWorkspaceCommand(),
        };

        for (CustomCommandExecutor executor : executors) {
            server.getPluginCommand(executor.getName())
                    .setExecutor(new CommandExceptionHandler(executor));
        }
    }
}
