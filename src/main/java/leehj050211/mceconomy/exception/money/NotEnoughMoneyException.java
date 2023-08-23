package leehj050211.mceconomy.exception.money;

import java.util.UUID;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;

public class NotEnoughMoneyException extends GeneralMCPlayerException {
    public NotEnoughMoneyException(UUID uuid, Long lackMoney) {
        super(uuid, "돈이 " + lackMoney  + "원 부족합니다.");
    }

}
