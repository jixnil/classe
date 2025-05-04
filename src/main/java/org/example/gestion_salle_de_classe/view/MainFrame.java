package org.example.gestion_salle_de_classe.view;

import org.example.gestion_salle_de_classe.service.OccuperService;
import org.example.gestion_salle_de_classe.service.ProfService;
import org.example.gestion_salle_de_classe.service.SalleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    public MainFrame(ProfService profService, SalleService salleService, OccuperService occuperService) {
        setTitle("Gestion des salles de classe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Style de la fenêtre
        getContentPane().setBackground(new Color(240, 240, 240));
        getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Création des panels
        ProfPanel profPanel = new ProfPanel(profService);
        SallePanel sallePanel = new SallePanel(salleService);
        OccuperPanel occuperPanel = new OccuperPanel(occuperService, profService, salleService);

        EmploiDuTempsPanel emploiDuTempsPanel = new EmploiDuTempsPanel(salleService, occuperService);


        // Ajout des onglets avec rafraîchissement
        tabbedPane.addTab("Profs", profPanel);
        tabbedPane.addTab("Salles", sallePanel);
        tabbedPane.addTab("Occupations", occuperPanel);
        tabbedPane.addTab("EDT", emploiDuTempsPanel);

        // Rafraîchissement des données à chaque clic sur un onglet
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    profPanel.chargerProfs();
                } else if (selectedIndex == 1) {
                    sallePanel.chargerSalles();
                } else if (selectedIndex == 2) {
                    occuperPanel.chargerOccuper();
                    occuperPanel.chargerComboBoxes();
                }else if (selectedIndex == 3) {
                    emploiDuTempsPanel.chargerEmploiDuTemps();
                }
            }
        });

        add(tabbedPane, BorderLayout.CENTER);

        // Style des onglets
        UIManager.put("TabbedPane.selected", new Color(200, 220, 255)); // Couleur de l'onglet sélectionné
        UIManager.put("TabbedPane.contentAreaColor", new Color(255, 255, 255)); // Couleur de la zone de contenu

        setSize(800, 600);
        setLocationRelativeTo(null);
    }
}