package leehj050211.mceconomy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import leehj050211.mceconomy.domain.type.JobType;
import leehj050211.mceconomy.domain.type.JobTypeConverter;
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

    @Column
    private String nickname;

    @Convert(converter = JobTypeConverter.class)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private JobType job;

    public static PlayerData create(UUID uuid, String nickname) {
        PlayerData playerData = new PlayerData();
        playerData.uuid = uuid;
        playerData.nickname = nickname;
        playerData.job = JobType.JOBLESS;
        return playerData;
    }

    public void updateJob(JobType job) {
        this.job = job;
    }
}
