package leehj050211.mceconomy.dao;

import leehj050211.mceconomy.config.HibernateUtil;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopItemDao {

    private static ShopItemDao instance;
    public static ShopItemDao getInstance() {
        if (instance == null) {
            instance = new ShopItemDao();
        }
        return instance;
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void update(ShopItemData itemData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(itemData);
        transaction.commit();
        session.close();
    }

    public void updateAll(List<ShopItemData> itemDataList) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (ShopItemData itemData : itemDataList) {
            session.merge(itemData);
        }
        transaction.commit();
        session.close();
    }

    public List<ShopItemData> findAll() {
        Session session = sessionFactory.openSession();
        List<ShopItemData> itemDataList = session
                .createQuery("FROM ShopItemData i JOIN FETCH i.priceCategory", ShopItemData.class)
                .list();
        session.close();
        return itemDataList;
    }
}
