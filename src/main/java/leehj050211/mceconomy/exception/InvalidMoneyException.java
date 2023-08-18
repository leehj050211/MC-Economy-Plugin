package leehj050211.mceconomy.exception;

import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;

import java.util.UUID;

public class InvalidMoneyException extends GeneralMCPlayerException {
    public InvalidMoneyException(UUID uuid) {
        super(uuid, "유효하지 않은 돈입니다.");
    }
}
