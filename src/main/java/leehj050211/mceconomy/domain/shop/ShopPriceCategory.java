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

    @Column(nullable = false, name = "base_price")
    private Long basePrice;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, name = "avg_amount")
    private Long avgAmount;

    @Column(nullable = false)
    private Long supply;

    @Column(nullable = false, name = "last_supply")
    private Long lastSupply;

    @Column(nullable = false)
    private Long demand;

    @Column(nullable = false, name = "last_demand")
    private Long lastDemand;

    public void increaseSupply(int amount) {
        this.supply += amount;
        this.amount += amount;
    }

    public void increaseDemand(int amount) {
        this.demand += amount;
        this.amount -= amount;
    }
}
