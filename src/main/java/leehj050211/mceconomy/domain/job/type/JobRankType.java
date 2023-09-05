package leehj050211.mceconomy.domain.job.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobRankType {
    TTEALGAM(0, "땔감", 0L),
    GUKBIE(1, "국비", 1000L),
    MIDAS(2, "마이다스 개발자", 10000L),
    GOOGLE(3, "구글 개발자", 100000L);

    private final int value;
    private final String name;
    private final Long exp;

}