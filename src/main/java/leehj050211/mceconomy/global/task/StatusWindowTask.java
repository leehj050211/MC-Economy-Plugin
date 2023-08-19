package leehj050211.mceconomy.global.task;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.PlayerData;
import leehj050211.mceconomy.global.player.PlayerManager;
import leehj050211.mceconomy.global.util.StringFormatter;
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
public class StatusWindowTask {

    private static StatusWindowTask instance;
    public static StatusWindowTask getInstance() {
        if (instance == null) {
            instance = new StatusWindowTask();
        }
        return instance;
    }

    private final Plugin plugin = MCEconomy.getInstance();
    private BukkitRunnable runnable;

    private ScoreboardManager scoreboardManager;
    private final PlayerManager playerManager = PlayerManager.getInstance();

    public void start() {
        scoreboardManager = Bukkit.getScoreboardManager();
        int delay = 0;
        int period = 20;
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                updatePlayerStatusWindows();
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

    private void updatePlayerStatusWindows() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            Objective objective = scoreboard.getObjective("status");
            if (objective == null) {
                objective = scoreboard.registerNewObjective("status", "dummy", "정보");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            updatePlayerStatusWindow(player, objective);
            player.setScoreboard(scoreboard);
        }
    }

    private void updatePlayerStatusWindow(Player player, Objective objective) {
        PlayerData playerData = playerManager.getData(player.getUniqueId());
        long time = player.getWorld().getTime();
        long money = playerData.getMoney();

        objective.setDisplayName(ChatColor.BOLD + player.getName() + "의 상태창");
        objective.getScore(ChatColor.YELLOW + "시간:").setScore(5);
        objective.getScore(StringFormatter.formatInGameTime(time)).setScore(4);
        objective.getScore(ChatColor.YELLOW + "직업:").setScore(3);
        objective.getScore(playerData.getJob().getName()).setScore(2);
        objective.getScore(ChatColor.YELLOW + "돈:").setScore(1);
        objective.getScore(StringFormatter.getMoneyString(money)).setScore(0);
    }
}
