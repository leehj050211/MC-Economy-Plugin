package leehj050211.mceconomy.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopPriceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Enumerated(EnumType.STRING)
    private Material icon;

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

    public long getNormalPrice(int amount) {
        long remainingAmount = this.avgAmount - amount + 1;
        return calcPrice(remainingAmount);
    }

    public long getCurrentPrice(int amount) {
        long remainingAmount = this.amount - amount + 1;
        return calcPrice(remainingAmount);
    }

    private long calcPrice(long remainingAmount) {
        if (remainingAmount > 0) {
            return (long) (this.basePrice * (avgAmount / (remainingAmount * 1.0)));
        }
        return (long) ((this.basePrice * avgAmount) * Math.pow(1.5, Math.max(-remainingAmount + 1, 1)));
    }
}
