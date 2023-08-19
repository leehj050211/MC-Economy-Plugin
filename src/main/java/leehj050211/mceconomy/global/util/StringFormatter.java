package leehj050211.mceconomy.global.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class StringFormatter {

    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0원");

    public String getMoneyString(long money) {
        return currencyFormat.format(money);
    }

    public String formatInGameTime(long time) {
        long hours = (time / 1000 + 6) % 24;
        long minutes = (time % 1000) * 60 / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }
}
