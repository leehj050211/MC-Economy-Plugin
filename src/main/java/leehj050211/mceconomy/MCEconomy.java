package leehj050211.mceconomy;

import com.samjakob.spigui.SpiGUI;
import leehj050211.mceconomy.command.CommandManager;
import leehj050211.mceconomy.event.EventManager;
import leehj050211.mceconomy.global.shop.ShopManager;
import leehj050211.mceconomy.global.task.SaveDataTask;
import leehj050211.mceconomy.global.task.StatusWindowTask;
import leehj050211.mceconomy.gui.MenuToolbarProvider;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class MCEconomy extends JavaPlugin {

    private static MCEconomy instance;

    public static MCEconomy getInstance() {
        return instance;
    }

    public static SpiGUI spiGUI;

    @Override
    public void onEnable() {
        instance = this;
        spiGUI = new SpiGUI(this);
        spiGUI.setDefaultToolbarBuilder(new MenuToolbarProvider());
        ShopManager.getInstance();

        EventManager.registerEvents();
        CommandManager.registerCommands();

        SaveDataTask.getInstance().start();
        StatusWindowTask.getInstance().start();

    }

    @Override
    public void onDisable() {
        SaveDataTask.getInstance().stop();
        SaveDataTask.getInstance().execute();
        StatusWindowTask.getInstance().stop();
    }
}
