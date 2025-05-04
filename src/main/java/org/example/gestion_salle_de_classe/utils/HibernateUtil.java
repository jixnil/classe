package org.example.gestion_salle_de_classe.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            System.out.println("Initialisation de Hibernate...");
            Configuration configuration = new Configuration().configure();
            System.out.println("Configuration chargée depuis hibernate.cfg.xml.");

            System.out.println("Construction de SessionFactory...");
            SessionFactory factory = configuration.buildSessionFactory();
            System.out.println("SessionFactory créée avec succès.");

            return factory;
        } catch (Throwable ex) {
            System.err.println("Échec de la création de SessionFactory : " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}