package leehj050211.mceconomy.dao;

import leehj050211.mceconomy.config.HibernateUtil;
import leehj050211.mceconomy.domain.PlayerData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PlayerDao {
    private static PlayerDao instance;

    private final SessionFactory sessionFactory;

    private PlayerDao() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static PlayerDao getInstance() {
        if (instance == null) {
            instance = new PlayerDao();
        }
        return instance;
    }

    public void save(PlayerData playerData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(playerData);
        transaction.commit();
        session.close();
    }
}
