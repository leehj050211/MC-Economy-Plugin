package leehj050211.mceconomy.global.util;

import leehj050211.mceconomy.domain.shop.ShopItemData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ShopUtil {

    public long getNormalPrice(ShopItemData itemData, int amount) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        long remainingAmount = priceCategory.getAvgAmount() - amount + 1;
        return calcPrice(itemData, remainingAmount, amount);
    }

    public long getCurrentPrice(ShopItemData itemData, int amount) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        long remainingAmount = priceCategory.getAmount() - amount + 1;
        return calcPrice(itemData, remainingAmount, amount);
    }

    public long getNormalSellPrice(ShopItemData itemData, int amount) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        long remainingAmount = priceCategory.getAvgAmount() + amount - 1;
        return calcPrice(itemData, remainingAmount, amount);
    }

    public long getCurrentSellPrice(ShopItemData itemData, int amount) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        long remainingAmount = priceCategory.getAmount() + amount - 1;
        return calcPrice(itemData, remainingAmount, amount);
    }

    private long calcPrice(ShopItemData itemData, long remainingAmount, int amount) {
        ShopPriceCategory priceCategory = itemData.getPriceCategory();
        if (remainingAmount > 0) {
            return (long) (itemData.getTotalPrice() * (priceCategory.getAvgAmount() / (remainingAmount * 1.0)) * amount);
        }
        return (long) ((itemData.getTotalPrice() * priceCategory.getAvgAmount()) * Math.pow(1.5, Math.max(-remainingAmount + 1, 1)) * amount);
    }
}
