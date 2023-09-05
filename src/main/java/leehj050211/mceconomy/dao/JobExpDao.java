package leehj050211.mceconomy.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import leehj050211.mceconomy.config.HibernateUtil;
import leehj050211.mceconomy.domain.job.JobExpData;
import leehj050211.mceconomy.domain.job.type.JobType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobExpDao {

    private static JobExpDao instance;
    public static JobExpDao getInstance() {
        if (instance == null) {
            instance = new JobExpDao();
        }
        return instance;
    }

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(JobExpData jobExpData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(jobExpData);
        transaction.commit();
        session.close();
    }

    public void update(JobExpData jobExpData) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(jobExpData);
        transaction.commit();
        session.close();
    }

    public Optional<JobExpData> findByPlayerAndJobType(UUID playerId, JobType job) {
        Session session = sessionFactory.openSession();
        Optional<JobExpData> jobExpData = session
            .createQuery("FROM JobExpData WHERE playerId = :playerId AND job = :job", JobExpData.class)
            .setParameter("playerId", playerId)
            .setParameter("job", job)
            .uniqueResultOptional();
        session.close();

        return jobExpData;
    }

    public List<JobExpData> findAllByPlayer(UUID playerId) {
        Session session = sessionFactory.openSession();
        List<JobExpData> jobExpData = session
            .createQuery("FROM JobExpData WHERE playerId = :playerId", JobExpData.class)
            .setParameter("playerId", playerId)
            .list();
        session.close();

        return jobExpData;
    }

}

