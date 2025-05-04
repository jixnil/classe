package org.example.gestion_salle_de_classe;

import org.example.gestion_salle_de_classe.dao.OccuperDAO;
import org.example.gestion_salle_de_classe.dao.ProfDAO;
import org.example.gestion_salle_de_classe.dao.SalleDAO;
import org.example.gestion_salle_de_classe.service.OccuperService;
import org.example.gestion_salle_de_classe.service.ProfService;
import org.example.gestion_salle_de_classe.service.SalleService;
import org.example.gestion_salle_de_classe.utils.HibernateUtil;
import org.example.gestion_salle_de_classe.view.MainFrame;
import org.hibernate.SessionFactory;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            if (sessionFactory != null) {
                ProfDAO profDao = new ProfDAO(sessionFactory);
                SalleDAO salleDao = new SalleDAO(sessionFactory);
                OccuperDAO occuperDao = new OccuperDAO(sessionFactory);

                ProfService profService = new ProfService(profDao);
                SalleService salleService = new SalleService(salleDao);
                OccuperService occuperService = new OccuperService(occuperDao);

                new MainFrame(profService, salleService, occuperService).setVisible(true);
            } else {
                System.err.println("Erreur d'initialisation de Hibernate. L'application ne peut pas démarrer.");
            }
        });
    }
}