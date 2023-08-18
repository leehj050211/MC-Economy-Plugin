package leehj050211.mceconomy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class PlayerData {

    @Id
    private UUID uuid;

    @Column
    private String nickname;

    public static PlayerData create(UUID uuid, String nickname) {
        PlayerData playerData = new PlayerData();
        playerData.uuid = uuid;
        playerData.nickname = nickname;
        return playerData;
    }
}
