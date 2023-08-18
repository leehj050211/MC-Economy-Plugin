package leehj050211.mceconomy.global.exception;

import java.util.UUID;

public class OfflineTargetPlayerException extends GeneralMCPlayerException {
    public OfflineTargetPlayerException(UUID uuid) {
        super(uuid, "상대방이 오프라인 상태입니다.");
    }
}
