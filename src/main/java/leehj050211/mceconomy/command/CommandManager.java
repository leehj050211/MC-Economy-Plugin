package leehj050211.mceconomy.command;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.command.estate.BuyEstateCommand;
import leehj050211.mceconomy.command.estate.SetEstatePointCommand;
import leehj050211.mceconomy.command.gacha.GachaCommand;
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
                new GachaCommand(),
                new OpenShopCommand(),
                new TpWorkspaceCommand(),
                new SetEstatePointCommand(),
                new BuyEstateCommand()
        };

        for (CustomCommandExecutor executor : executors) {
            server.getPluginCommand(executor.getName())
                    .setExecutor(new CommandExceptionHandler(executor));
        }
    }
}
