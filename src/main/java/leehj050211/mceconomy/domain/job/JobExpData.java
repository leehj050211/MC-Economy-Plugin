package leehj050211.mceconomy.domain.job;

import static leehj050211.mceconomy.domain.job.type.JobRankType.GOOGLE;
import static leehj050211.mceconomy.domain.job.type.JobRankType.GUKBIE;
import static leehj050211.mceconomy.domain.job.type.JobRankType.MIDAS;
import static leehj050211.mceconomy.domain.job.type.JobRankType.TTEALGAM;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import leehj050211.mceconomy.domain.job.type.JobRankType;
import leehj050211.mceconomy.domain.job.type.JobRankTypeConverter;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.job.type.JobTypeConverter;
import leehj050211.mceconomy.exception.exp.InvalidExpException;
import leehj050211.mceconomy.global.exception.GeneralMCPlayerException;
import leehj050211.mceconomy.global.util.RankUpUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import org.bukkit.entity.Player;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JobExpData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = JobRankTypeConverter.class)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private JobRankType jobRank;

    @Column(nullable = false)
    private UUID playerId;

    @Column(nullable = false)
    private Long totalExp;

    @Column(nullable = false)
    private Long exp;

    @Convert(converter = JobTypeConverter.class)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private JobType job;

    public static JobExpData create(UUID playerId, JobType job) {
        JobExpData jobExpData = new JobExpData();
        jobExpData.jobRank = TTEALGAM;
        jobExpData.playerId = playerId;
        jobExpData.exp = 0L;
        jobExpData.totalExp = 0L;
        jobExpData.job = job;
        return jobExpData;
    }

    public void increaseExp(Player player, Long exp) throws GeneralMCPlayerException {
        if (exp < 0) {
            throw new InvalidExpException(this.playerId);
        }

        this.totalExp += exp;
        this.exp += exp;

        // 1차 승급 이벤트
        if (jobRank == TTEALGAM && this.exp > GUKBIE.getExp()) {
            increaseRank(GUKBIE);
            RankUpUtil.rankUpDefaultEvent(player, GUKBIE);
        }

        // 2차 승급 이벤트
        else if (jobRank == GUKBIE && this.exp > MIDAS.getExp()) {
            increaseRank(MIDAS);
            RankUpUtil.rankUpDefaultEvent(player, MIDAS);
        }

        // 3차 승급 이벤트
        else if (jobRank == MIDAS && this.exp > GOOGLE.getExp()) {
            increaseRank(GOOGLE);
            RankUpUtil.rankUpDefaultEvent(player, GOOGLE);
        }

    }

    public void increaseRank(JobRankType jobRank) throws GeneralMCPlayerException {
        this.exp = 0L;
        this.jobRank = jobRank;
    }

}
