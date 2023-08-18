package leehj050211.mceconomy.config;

import leehj050211.mceconomy.domain.PlayerData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        String url = "jdbc:mysql://localhost:3306/minecraft_economy?autoReconnect=true";
        String user = "mine";
        String password = "mine";

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", user)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(PlayerData.class);

        sessionFactory = configuration.buildSessionFactory();;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
