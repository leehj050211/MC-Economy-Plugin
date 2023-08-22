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
    @Column(nullable = false, name = "item_category", columnDefinition = "INT UNSIGNED")
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

    public long getPrice(int amount) {
        return getPrice() * amount;
    }

    public long getCurrentPrice(int amount) {
        long remainingAmount = priceCategory.getAmount() - amount + 1;
        if (remainingAmount > 0) {
            return (long) (getPrice() * (priceCategory.getAvgAmount() / (remainingAmount * 1.0)));
        }
        return (long) ((getPrice() * priceCategory.getAvgAmount()) * Math.pow(1.5, Math.max(-remainingAmount + 1, 1)));
    }
}
