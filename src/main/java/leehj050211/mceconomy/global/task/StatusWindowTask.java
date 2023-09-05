package leehj050211.mceconomy.global.task;

import leehj050211.mceconomy.MCEconomy;
import leehj050211.mceconomy.domain.job.JobExpData;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exp.JobExpManager;
import leehj050211.mceconomy.global.player.PlayerManager;
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
    private final JobExpManager jobExpManager = JobExpManager.getInstance();

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
        JobExpData jobExpData = jobExpManager.getData(playerData);
        long time = player.getWorld().getTime();
        long money = playerData.getMoney();

        objective.setDisplayName(ChatColor.BOLD + player.getName() + "의 상태창");
        objective.getScore(ChatColor.YELLOW + "시간:").setScore(9);
        objective.getScore(Formatter.formatInGameTime(time)).setScore(8);
        objective.getScore(ChatColor.YELLOW + "계급:").setScore(7);
        objective.getScore(jobExpData.getJobRank().getName()).setScore(6);
        objective.getScore(ChatColor.YELLOW + "직업:").setScore(5);
        objective.getScore(playerData.getJob().getName()).setScore(4);
        objective.getScore(ChatColor.YELLOW + "경험치:").setScore(3);
        objective.getScore(jobExpData.getExp() + "exp").setScore(2);
        objective.getScore(ChatColor.YELLOW + "돈:").setScore(1);
        objective.getScore(Formatter.formatMoney(money)).setScore(0);
    }
}