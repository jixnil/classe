package org.example.gestion_salle_de_classe.dao;

import org.example.gestion_salle_de_classe.model.Salle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SalleDAO {
    private SessionFactory sessionFactory;

    public SalleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Salle salle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(salle);
        transaction.commit();
        session.close();
    }

    public Salle read(int codesal) {
        Session session = sessionFactory.openSession();
        Salle salle = session.get(Salle.class, codesal);
        session.close();
        return salle;
    }

    public void update(Salle salle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(salle);
        transaction.commit();
        session.close();
    }

    public void delete(Salle salle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(salle);
        transaction.commit();
        session.close();
    }

    public List<Salle> getAll() {
        Session session = sessionFactory.openSession();
        Query<Salle> query = session.createQuery("FROM Salle", Salle.class);
        return query.list();
    }
}
