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
public class ShopCategoryDao {

    private static ShopCategoryDao instance;
    public static ShopCategoryDao getInstance() {
        if (instance == null) {
            instance = new ShopCategoryDao();
        }
        return instance;
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void updateAll(List<ShopPriceCategory> categoryDataList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (ShopPriceCategory categoryData : categoryDataList) {
            session.merge(categoryData);
        }
        transaction.commit();
        session.close();
    }

    public List<ShopPriceCategory> findAll() {
        Session session = sessionFactory.openSession();
        List<ShopPriceCategory> categoryDataList = session
                .createQuery("FROM ShopCategoryData", ShopPriceCategory.class)
                .list();
        session.close();
        return categoryDataList;
    }
}
