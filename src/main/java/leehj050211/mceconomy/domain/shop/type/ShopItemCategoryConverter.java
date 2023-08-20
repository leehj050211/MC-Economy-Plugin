package leehj050211.mceconomy.domain.shop.type;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ShopItemCategoryConverter implements AttributeConverter<ShopItemCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ShopItemCategory category) {
        return category.getValue();
    }

    @Override
    public ShopItemCategory convertToEntityAttribute(Integer dbData) {
        for (ShopItemCategory category : ShopItemCategory.values()) {
            if (category.getValue() == dbData) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown database value:" + dbData);
    }
}