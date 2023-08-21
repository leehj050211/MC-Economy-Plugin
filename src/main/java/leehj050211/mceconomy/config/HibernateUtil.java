package leehj050211.mceconomy.config;

import leehj050211.mceconomy.domain.player.PlayerData;
import leehj050211.mceconomy.domain.shop.ShopPriceCategory;
import leehj050211.mceconomy.domain.shop.ShopItemData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        String url = "jdbc:mysql://localhost:4306/minecraft_economy?autoReconnect=true";
        String user = "root";
        String password = "root";

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", url)
                .setProperty("hibernate.connection.username", user)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(PlayerData.class)
                .addAnnotatedClass(ShopPriceCategory.class)
                .addAnnotatedClass(ShopItemData.class);

        sessionFactory = configuration.buildSessionFactory();
    }
}
