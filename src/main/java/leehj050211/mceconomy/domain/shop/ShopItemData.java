package leehj050211.mceconomy.domain.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategory;
import leehj050211.mceconomy.domain.shop.type.ShopItemCategoryConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopItemData {

    @Id
    private String id;

    @Convert(converter = ShopItemCategoryConverter.class)
    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private ShopItemCategory itemCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id")
    private ShopPriceCategory priceCategory;

    @Column(nullable = false)
    private Long price;

    public Material getMaterial() {
        return Material.valueOf(this.id);
    }

    public long getPrice() {
        return this.price + this.priceCategory.getBasePrice();
    }

    public long getCurrentPrice() {
        long demand = this.priceCategory.getDemand();
        long supply = this.priceCategory.getSupply();
        double demandFactor = Math.pow(1.2, demand);
        double supplyFactor = 1 - (supply * 0.05);

        return Math.round(getPrice() * demandFactor * supplyFactor);
    }
}
