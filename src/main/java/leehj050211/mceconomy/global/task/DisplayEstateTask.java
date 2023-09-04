package leehj050211.mceconomy.global.task;

import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.estate.EstateManager;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.EstateUtil;
import leehj050211.mceconomy.global.util.Formatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayEstateTask {

    private static DisplayEstateTask instance;
    public static DisplayEstateTask getInstance() {
        if (instance == null) {
            instance = new DisplayEstateTask();
        }
        return instance;
    }

    private final Plugin plugin = MCEconomy.getInstance();
    private BukkitRunnable runnable;

    private final EstateManager estateManager = EstateManager.getInstance();

    public void start() {
        int delay = 0;
        int period = 40;
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                updateDisplayEstate();
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

    private void updateDisplayEstate() {
        for (ProtectedRegion region : estateManager.getRegions()) {
            if (!(region instanceof ProtectedCuboidRegion)) return;

            EstateUtil.displayEstate(region);
        }
    }

}
