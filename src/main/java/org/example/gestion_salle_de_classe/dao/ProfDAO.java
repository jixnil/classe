package org.example.gestion_salle_de_classe.dao;

import org.example.gestion_salle_de_classe.model.Prof;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProfDAO {
    private SessionFactory sessionFactory;

    public ProfDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Prof prof) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(prof);
        transaction.commit();
        session.close();
    }
    public List<Prof> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Prof> query = session.createQuery("from Prof", Prof.class);
            return query.list();
        }
    }
    public Prof read(int codeprof) {
        Session session = sessionFactory.openSession();
        Prof prof = session.get(Prof.class, codeprof);
        session.close();
        return prof;
    }

    public void update(Prof prof) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(prof);
        transaction.commit();
        session.close();
    }

    public void delete(Prof prof) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(prof);
        transaction.commit();
        session.close();
    }

    public List<Prof> findByCodeOrName(String codeOrName) {
        Session session = sessionFactory.openSession();
        List<Prof> profs;

        try {
            int code = Integer.parseInt(codeOrName);
            Query<Prof> query = session.createQuery("FROM Prof WHERE codeprof = :code", Prof.class);
            query.setParameter("code", code);
            profs = query.list();
        } catch (NumberFormatException e) {
            // Recherche par nom ou code contenant la chaîne
            Query<Prof> query = session.createQuery("FROM Prof WHERE nom LIKE :search OR CAST(codeprof AS string) LIKE :search", Prof.class);
            query.setParameter("search", "%" + codeOrName + "%");
            profs = query.list();
        } finally {
            session.close();
        }
        return profs;
    }
}