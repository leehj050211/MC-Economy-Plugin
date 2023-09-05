package leehj050211.mceconomy.event.player.listener;

import leehj050211.mceconomy.domain.job.type.JobType;
import leehj050211.mceconomy.domain.job.type.farmer.FarmerExpType;
import leehj050211.mceconomy.domain.job.type.miner.MinerExpType;
import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.global.exp.JobExpManager;
import leehj050211.mceconomy.global.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockBreakListener implements Listener {

    private final PlayerManager playerManager = PlayerManager.getInstance();
    private final JobExpManager jobExpManager = JobExpManager.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = playerManager.getData(player.getUniqueId());

        Material blockType = event.getBlock().getType();
        JobType job = playerData.getJob();

        switch (job) {
            case FARMER:
                for (FarmerExpType farmerExptype : FarmerExpType.values()) {
                    if (farmerExptype.getIcon() == blockType) {
                        jobExpManager.addJobExp(player, job, farmerExptype.getExp());
                        break;
                    }
                }
                break;
            case MINER:
                for (MinerExpType minerExpType : MinerExpType.values()) {
                    if (minerExpType.getIcon() == blockType) {
                        jobExpManager.addJobExp(player, job, minerExpType.getExp());
                        break;
                    }
                }
                break;
        }

    }
}
