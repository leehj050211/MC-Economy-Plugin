package leehj050211.mceconomy.global.util;

import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CountDownTimer implements Runnable {

    private JavaPlugin plugin;

    private Integer assignedTaskId;

    private int seconds;
    private int secondsLeft;

    private Consumer<CountDownTimer> everySecond;
    private Runnable beforeTimer;
    private Runnable afterTimer;

    public CountDownTimer(JavaPlugin plugin, int seconds,
        Runnable beforeTimer, Runnable afterTimer,
        Consumer<CountDownTimer> everySecond) {

        this.plugin = plugin;

        this.seconds = seconds;
        this.secondsLeft = seconds;

        this.beforeTimer = beforeTimer;
        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    @Override
    public void run() {
        if (secondsLeft < 1) {
            afterTimer.run();

            if (assignedTaskId != null) {
                Bukkit.getScheduler().cancelTask(assignedTaskId);
            }
            return;
        }

        if (secondsLeft == seconds) {
            beforeTimer.run();
        }

        everySecond.accept(this);

        secondsLeft--;
    }

    public int getTotalSeconds() {
        return seconds;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler()
            .scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }

}
