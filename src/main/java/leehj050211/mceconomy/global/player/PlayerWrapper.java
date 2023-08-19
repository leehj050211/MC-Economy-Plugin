package leehj050211.mceconomy.global.player;

import leehj050211.mceconomy.domain.player.PlayerData;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.UUID;


public record PlayerWrapper(@Nonnull UUID uuid, @Nonnull Player player, @Nonnull PlayerData playerData) {}
