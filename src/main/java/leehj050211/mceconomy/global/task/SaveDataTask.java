package leehj050211.mceconomy.global.task;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.dao.PlayerDao;
import leehj050211.mceconomy.global.player.PlayerManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveDataTask {

    private static SaveDataTask instance;
    public static SaveDataTask getInstance() {
        if (instance == null) {
            instance = new SaveDataTask();
        }
        return instance;
    }

    private final Plugin plugin = MCEconomy.getInstance();
    private BukkitRunnable runnable;
    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();

    public void execute() {
        playerDao.updateAll(playerManager.getAllData());
    }

    public void start() {
        int delay = 20 * 60 * 10;
        int period = delay;

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                execute();
            }
        };
        runnable.runTaskTimer(plugin, delay, period);
    }

    public void stop() {
        if (runnable == null) {
            return;
        }
        runnable.cancel();
        runnable = null;
    }

}
