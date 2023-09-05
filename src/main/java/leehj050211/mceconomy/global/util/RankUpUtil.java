package leehj050211.mceconomy.global.util;

import leehj050211.mceconomy.domain.job.type.JobRankType;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

@UtilityClass
public final class RankUpUtil {

    public void rankUpDefaultEvent(Player player, JobRankType jobRankType) {

        Bukkit.broadcastMessage(
            ChatColor.AQUA + player.getName() + "님이 \"" + jobRankType.getName()
                + "\" 등급으로 승급하였습니다.");

        // 폭죽 이벤트
        Firework firework = player.getWorld().spawn(
            player.getLocation(), Firework.class);

        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder().flicker(false)
            .withColor(Color.RED).withFade(Color.LIME).with(Type.BURST)
            .trail(true).build();

        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(1);

        firework.setFireworkMeta(fireworkMeta);

    }

}