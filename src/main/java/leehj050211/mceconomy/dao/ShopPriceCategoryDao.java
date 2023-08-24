package leehj050211.mceconomy.dao;

import leehj050211.mceconomy.config.HibernateUtil;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopPriceCategoryDao {

    private static ShopPriceCategoryDao instance;
    public static ShopPriceCategoryDao getInstance() {
        if (instance == null) {
            instance = new ShopPriceCategoryDao();
        }
        return instance;
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void updateAll(List<ShopPriceCategory> priceCategoryList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (ShopPriceCategory priceCategory : priceCategoryList) {
            session.merge(priceCategory);
        }
        transaction.commit();
        session.close();
    }

    public List<ShopPriceCategory> findAll() {
        Session session = sessionFactory.openSession();
        List<ShopPriceCategory> priceCategoryList = session
                .createQuery("FROM ShopPriceCategory", ShopPriceCategory.class)
                .list();
        session.close();
        return priceCategoryList;
    }
}
