package leehj050211.mceconomy.domain.player;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.job.type.JobTypeConverter;
import leehj050211.mceconomy.exception.money.InvalidMoneyException;
import leehj050211.mceconomy.exception.money.NotEnoughMoneyException;
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

    @Column(nullable = false, name = "health_level")
    private Short healthLevel;

    @Column(nullable = false, name = "armor_level")
    private Short armorLevel;

    @Column(nullable = false, name = "attack_level")
    private Short attackLevel;

    @Column(nullable = false, name = "speedLevel")
    private Short speedLevel;

    public static PlayerData create(UUID uuid, String nickname) {
        PlayerData playerData = new PlayerData();
        playerData.uuid = uuid;
        playerData.nickname = nickname;
        playerData.money = 1000L;
        playerData.job = JobType.JOBLESS;
        playerData.healthLevel = 1;
        playerData.armorLevel = 1;
        playerData.attackLevel = 1;
        playerData.speedLevel = 1;
        return playerData;
    }

    public void updateJob(JobType job) {
        this.job = job;
    }

    public void increaseMoney(long money) {
        if (money < 0) {
            throw new InvalidMoneyException(this.uuid);
        }
        this.money += money;
    }

    public void increaseMoney(double money) {
        increaseMoney((long) money);
    }

    public void decreaseMoney(long money) {
        if (money < 0) {
            throw new InvalidMoneyException(this.uuid);
        }
        if (this.money < money) {
            throw new NotEnoughMoneyException(this.uuid, money - this.money);
        }
        this.money -= money;
    }

    public void decreaseMoney(double money) {
        decreaseMoney((long) money);
    }

    public void levelUpHealth() {
        this.healthLevel++;
    }

    public void levelUpArmor() {
        this.armorLevel++;
    }

    public void levelUpAttack() {
        this.attackLevel++;
    }

    public void levelUpSpeed() {
        this.speedLevel++;
    }

}
