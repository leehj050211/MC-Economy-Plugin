package leehj050211.mceconomy.domain.job.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum JobType {
    JOBLESS(0, "무직", "직업이 없음", Material.BARRIER, false),
    FARMER(1, "농부", "그냥 농부", Material.HAY_BLOCK, true),
    MINER(2, "광부", "그냥 광부", Material.STONE, true),
    PROGRAMMER(3, "프로그래머", "JSON 상하차", Material.LEVER, false);

    private final int value;
    private final String name;
    private final String description;
    private final Material icon;
    private final boolean worldGen;
}
