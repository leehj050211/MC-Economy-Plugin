package leehj050211.mceconomy.global.exp;

import java.util.UUID;
import javax.annotation.Nonnull;
import leehj050211.mceconomy.domain.job.JobExpData;
import org.bukkit.entity.Player;

public record JobExpWrapper(@Nonnull UUID uuid, @Nonnull Player player, @Nonnull JobExpData jobExpData) {}
