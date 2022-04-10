package com.gitlab.marnow.stretchambitionlab.springpostgresbase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManagerFactory;

public class CleanUpPostgresDataBase implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        ApplicationContext context = SpringContext.getInstance();
        EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);

        final SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        final Session session = sessionFactory.openSession();
        final Transaction tx = session.beginTransaction();
        final int sqlQuery = session.createSQLQuery("""
                        TRUNCATE table
                            users
                            CASCADE;
                        """)
                .executeUpdate();
        tx.commit();
        session.close();
    }
}
