package leehj050211.mceconomy.global.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class Formatter {

    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0원");
    private final DecimalFormat amountFormat = new DecimalFormat("#,##0개");

    public String formatMoney(long money) {
        return currencyFormat.format(money);
    }

    public String formatAmount(long amount) {
        return amountFormat.format(amount);
    }

    public String formatInGameTime(long time) {
        long hours = (time / 1000 + 6) % 24;
        long minutes = (time % 1000) * 60 / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }
}
