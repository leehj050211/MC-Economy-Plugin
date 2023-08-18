package leehj050211.mceconomy.global.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GeneralMCPlayerException extends Exception {

    private final UUID uuid;

    public GeneralMCPlayerException(UUID uuid, String message) {
        super(message);
        this.uuid = uuid;
    }
}