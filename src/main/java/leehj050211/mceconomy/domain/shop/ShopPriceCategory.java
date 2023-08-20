package leehj050211.mceconomy.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopPriceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long basePrice;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Long supply;

    @Column(nullable = false)
    private Long lastSupply;

    @Column(nullable = false)
    private Long demand;

    @Column(nullable = false)
    private Long lastDemand;

    public void increaseSupply() {
        this.supply++;
        this.amount++;
    }

    public void increaseDemand() {
        this.demand++;
        this.amount--;
    }
}
