package leehj050211.mceconomy.global.util;

import leehj050211.mceconomy.domain.player.PlayerData;
import lombok.experimental.UtilityClass;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

@UtilityClass
public class StatUtil {

    private static final int BASE_PRICE = 5000;
    private static final int INITIAL_MAX_HEALTH = 2;
    private static final int INITIAL_ARMOR = 0;
    private static final double INITIAL_ATTACK_DAMAGE = 0.5;
    private static final double INITIAL_SPEED = 0.09;

    public long calcPrice(int level) {
        return (long) (BASE_PRICE * Math.pow(1.5, level));
    }

    public void levelUpHealth(Player player, PlayerData data) {
        data.decreaseMoney(calcPrice(data.getHealthLevel()));
        data.levelUpHealth();
        applyHealth(player, data);
    }

    public void levelUpArmor(Player player, PlayerData data) {
        data.decreaseMoney(calcPrice(data.getArmorLevel()));
        data.levelUpArmor();
        applyArmor(player, data);
    }

    public void levelUpAttack(Player player, PlayerData data) {
        data.decreaseMoney(calcPrice(data.getAttackLevel()));
        data.levelUpAttack();
        applyAttack(player, data);
    }

    public void levelUpSpeed(Player player, PlayerData data) {
        data.decreaseMoney(calcPrice(data.getSpeedLevel()));
        data.levelUpSpeed();
        applySpeed(player, data);
    }

    private void applyHealth(Player player, PlayerData data) {
        int value = INITIAL_MAX_HEALTH + (2 * (data.getHealthLevel() - 1));
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
    }

    private void applyArmor(Player player, PlayerData data) {
        int value = INITIAL_ARMOR + (data.getArmorLevel() - 1);
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(value);
    }

    private void applyAttack(Player player, PlayerData data) {
        double value = INITIAL_ATTACK_DAMAGE + (0.05 * (data.getArmorLevel() - 1));
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(value);
    }

    private void applySpeed(Player player, PlayerData data) {
        double value = INITIAL_SPEED + (0.0025 * (data.getSpeedLevel() - 1));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(value);
    }

    public void applyStat(Player player, PlayerData data) {
        applyHealth(player, data);
        applyArmor(player, data);
        applyAttack(player, data);
        applySpeed(player, data);
    }
}
