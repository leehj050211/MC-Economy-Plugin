package leehj050211.mceconomy.exception.exp;

import java.util.UUID;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;

public class InvalidExpException extends GeneralMCPlayerException {
    public InvalidExpException(UUID uuid) {
        super(uuid, "유효하지 않은 경험치입니다.");
    }

}