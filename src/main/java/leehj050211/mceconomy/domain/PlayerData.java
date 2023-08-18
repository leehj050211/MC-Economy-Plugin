package leehj050211.mceconomy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import leehj050211.mceconomy.domain.type.JobType;
import leehj050211.mceconomy.domain.type.JobTypeConverter;
import leehj050211.mceconomy.exception.money.InvalidMoneyException;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerData {

    @Id
    private UUID uuid;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long money;

    @Convert(converter = JobTypeConverter.class)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private JobType job;

    public static PlayerData create(UUID uuid, String nickname) {
        PlayerData playerData = new PlayerData();
        playerData.uuid = uuid;
        playerData.nickname = nickname;
        playerData.money = 1000L;
        playerData.job = JobType.JOBLESS;
        return playerData;
    }

    public void updateJob(JobType job) {
        this.job = job;
    }

    public void increaseMoney(Long money) throws GeneralMCPlayerException {
        if (money >= 0) {
            this.money += money;
        } else {
            throw new InvalidMoneyException(this.uuid);
        }
    }

    public void decreaseMoney(Long money) throws GeneralMCPlayerException {
        if (money >= 0 && this.money >= money) {
            this.money -= money;
        } else {
            throw new InvalidMoneyException(this.uuid);
        }
    }
}
