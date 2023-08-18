package leehj050211.mceconomy.exception.money;

import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;

import java.util.UUID;

public class InvalidSendMoneyTargetException extends GeneralMCPlayerException {
    public InvalidSendMoneyTargetException(UUID uuid) {
        super(uuid, "같은 사람에게는 송금할 수 없습니다.");
    }
}
