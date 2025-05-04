package org.example.gestion_salle_de_classe.dao;

import org.example.gestion_salle_de_classe.model.Occuper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Date;

public class OccuperDAO {
    private static SessionFactory sessionFactory;

    public OccuperDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Occuper occuper) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(occuper);
        transaction.commit();
        session.close();
    }

    public Occuper read(int id) {
        Session session = sessionFactory.openSession();
        Occuper occuper = session.get(Occuper.class, id);
        session.close();
        return occuper;
    }
    public static List<Occuper> findBySalleAndDate(int salleCode, Date date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Occuper> query = session.createQuery(
                    "from Occuper o where o.salle.codesal = :salleCode and o.date = :date", Occuper.class);
            query.setParameter("salleCode", salleCode);
            query.setParameter("date", date);
            System.out.println("findBySalleAndDate: salleCode=" + salleCode + ", date=" + date);
            List<Occuper> result = query.list();
            System.out.println("findBySalleAndDate: result size=" + result.size());
            return result;
        }
    }

    public static List<Occuper> findByProfAndDate(int profCode, Date date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Occuper> query = session.createQuery(
                    "from Occuper o where o.prof.codeprof = :profCode and o.date = :date", Occuper.class);
            query.setParameter("profCode", profCode);
            query.setParameter("date", date);
            System.out.println("findByProfAndDate: profCode=" + profCode + ", date=" + date);
            List<Occuper> result = query.list();
            System.out.println("findByProfAndDate: result size=" + result.size());
            return result;
        }
    }

    public void update(Occuper occuper) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(occuper);
        transaction.commit();
        session.close();
    }

    public void delete(Occuper occuper) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(occuper);
        transaction.commit();
        session.close();
    }

    public List<Occuper> getAll() {
        Session session = sessionFactory.openSession();
        Query<Occuper> query = session.createQuery("FROM Occuper", Occuper.class);
        List<Occuper> occupations = query.list();
        System.out.println("OccuperDAO.getAll(): occupations size=" + occupations.size());
        for (Occuper occupation : occupations) {
            System.out.println("OccuperDAO.getAll(): occupation=" + occupation);
        }
        return occupations;
    }

}
