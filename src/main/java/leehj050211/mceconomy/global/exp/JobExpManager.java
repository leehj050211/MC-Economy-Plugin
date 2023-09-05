package leehj050211.mceconomy.global.exp;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import leehj050211.mceconomy.dao.JobExpDao;
import leehj050211.mceconomy.domain.job.JobExpData;
import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.player.PlayerData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JobExpManager {

    private static JobExpManager instance;

    public static JobExpManager getInstance() {
        if (instance == null) {
            instance = new JobExpManager();
        }
        return instance;
    }

    private final JobExpDao jobExpDao = JobExpDao.getInstance();

    private static final HashMap<String, JobExpWrapper> onlineJobExp = new HashMap<>();

    public void addPlayer(Player player) {
        List<JobExpData> jobExpDataList = jobExpDao.findAllByPlayer(player.getUniqueId());
        for (JobExpData jobExpData : jobExpDataList) {
            JobExpWrapper jobExpWrapper = new JobExpWrapper(player.getUniqueId(), player,
                jobExpData);

            String key = generateKey(jobExpData.getPlayerId(), jobExpData.getJob());
            onlineJobExp.put(key, jobExpWrapper);
        }
    }

    public void deletePlayer(PlayerData playerData) {
        jobExpDao.update(getData(playerData));

        List<JobExpData> jobExpDataList = jobExpDao.findAllByPlayer(playerData.getUuid());
        for (JobExpData jobExpData : jobExpDataList) {
            String key = generateKey(jobExpData.getPlayerId(), jobExpData.getJob());
            onlineJobExp.remove(key);
        }
    }

    public void addJobExp(Player player, JobType jobType, Long exp) {
        JobExpData jobExpData = jobExpDao.findByPlayerAndJobType(player.getUniqueId(), jobType)
            .orElseGet(() -> createJobExp(player, jobType));

        jobExpData.increaseExp(player, exp);
        jobExpDao.update(jobExpData);

        JobExpWrapper jobExpWrapper = new JobExpWrapper(player.getUniqueId(), player, jobExpData);

        String key = generateKey(jobExpData.getPlayerId(), jobExpData.getJob());
        onlineJobExp.put(key, jobExpWrapper);
    }

    private JobExpData createJobExp(Player player, JobType jobType) {
        JobExpData jobExpData = JobExpData.create(player.getUniqueId(), jobType);
        jobExpDao.save(jobExpData);
        return jobExpData;
    }

    public JobExpData getData(PlayerData playerData) {
        String key = generateKey(playerData.getUuid(), playerData.getJob());
        JobExpWrapper jobExpWrapper = onlineJobExp.get(key);
        if (jobExpWrapper != null) {
            return jobExpWrapper.jobExpData();
        }
        return null;
    }

    private String generateKey(UUID uuid, JobType jobType) {
        return uuid.toString() + "_" + jobType.toString();
    }

}