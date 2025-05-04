package org.example.gestion_salle_de_classe.view;

import com.toedter.calendar.JDateChooser;
import org.example.gestion_salle_de_classe.model.Occuper;
import org.example.gestion_salle_de_classe.model.Prof;
import org.example.gestion_salle_de_classe.model.Salle;
import org.example.gestion_salle_de_classe.service.OccuperService;
import org.example.gestion_salle_de_classe.service.ProfService;
import org.example.gestion_salle_de_classe.service.SalleService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class OccuperPanel extends JPanel {
    private OccuperService occuperService;
    private ProfService profService;
    private SalleService salleService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Integer> profComboBox, salleComboBox;
    private JDateChooser dateChooser;

    public OccuperPanel(OccuperService occuperService, ProfService profService, SalleService salleService) {
        this.occuperService = occuperService;
        this.profService = profService;
        this.salleService = salleService;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Prof");
        tableModel.addColumn("Salle");
        tableModel.addColumn("Date");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations d'occupation"));
        formPanel.add(new JLabel("Prof (Code) :"));
        profComboBox = new JComboBox<>();
        chargerProfComboBox();
        formPanel.add(profComboBox);
        formPanel.add(new JLabel("Salle (Code) :"));
        salleComboBox = new JComboBox<>();
        chargerSalleComboBox();
        formPanel.add(salleComboBox);
        formPanel.add(new JLabel("Date :"));
        dateChooser = new JDateChooser();
        dateChooser.setMinSelectableDate(new Date());
        formPanel.add(dateChooser);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(formPanel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(220, 220, 220));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterOccuper();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(220, 220, 220));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierOccuper();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(220, 220, 220));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerOccuper();
            }
        });
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Ajout de l'écouteur de sélection de ligne
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        remplirFormulaire(selectedRow);
                    }
                }
            }
        });

        chargerOccuper();
    }

    void chargerOccuper() {
        tableModel.setRowCount(0);
        List<Occuper> occupations = occuperService.getAllOccuper();
        for (Occuper occupation : occupations) {
            tableModel.addRow(new Object[]{occupation.getProf().getCodeprof(), occupation.getSalle().getCodesal(), occupation.getDate()});
        }
    }
    public void chargerComboBoxes() {
        chargerProfComboBox();
        chargerSalleComboBox();
    }
    private void chargerProfComboBox() {
            profComboBox.removeAllItems();
        List<Prof> profs = profService.getAllProfs();
        for (Prof prof : profs) {
            profComboBox.addItem(prof.getCodeprof());
        }
    }

    private void chargerSalleComboBox() {
        salleComboBox.removeAllItems();
        List<Salle> salles = salleService.getAllSalles();
        for (Salle salle : salles) {
            salleComboBox.addItem(salle.getCodesal());
        }
    }

    private void remplirFormulaire(int row) {
        Integer profCodeInt = (Integer) tableModel.getValueAt(row, 0);
        Integer salleCodeInt = (Integer) tableModel.getValueAt(row, 1);
        Date date = (Date) tableModel.getValueAt(row, 2);

        profComboBox.setSelectedItem(profCodeInt);
        salleComboBox.setSelectedItem(salleCodeInt);
        dateChooser.setDate(date);
    }

    private void ajouterOccuper() {
        try {
            int profCode = (int) profComboBox.getSelectedItem();
            int salleCode = (int) salleComboBox.getSelectedItem();
            Date date = dateChooser.getDate();

            Prof prof = profService.getProf(profCode);
            Salle salle = salleService.getSalle(salleCode);

            if (prof != null && salle != null) {
                // Vérifier si la salle est déjà occupée à cette date
                if (occuperService.isSalleOccupee(salleCode, date)) {
                    JOptionPane.showMessageDialog(this, "La salle est déjà occupée à cette date.");
                    return;
                }

                // Vérifier si le professeur occupe déjà une salle à cette date
                if (occuperService.isProfOccupe(profCode, date)) {
                    JOptionPane.showMessageDialog(this, "Le professeur occupe déjà une salle à cette date.");
                    return;
                }

                Occuper occupation = new Occuper();
                occupation.setProf(prof);
                occupation.setSalle(salle);
                occupation.setDate(date);
                occuperService.createOccuper(occupation);
                chargerOccuper();

                dateChooser.setDate(null);
            } else {
                JOptionPane.showMessageDialog(this, "Professeur ou Salle introuvable.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sélection de la date.");
        }
    }



    private void modifierOccuper() {
        int row = table.getSelectedRow();
        if (row != -1) {
            try {
                Occuper occupation = occuperService.getAllOccuper().get(row);

                int profCode = (int) profComboBox.getSelectedItem();
                int salleCode = (int) salleComboBox.getSelectedItem();
                Date date = dateChooser.getDate();

                Prof prof = profService.getProf(profCode);
                Salle salle = salleService.getSalle(salleCode);

                if (prof != null && salle != null) {
                    occupation.setProf(prof);
                    occupation.setSalle(salle);
                    occupation.setDate(date);
                    occuperService.updateOccuper(occupation);
                    chargerOccuper();
                } else {
                    JOptionPane.showMessageDialog(this, "Professeur ou Salle introuvable.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la sélection de la date.");
            }
        }
        dateChooser.setDate(null);
    }

    private void supprimerOccuper() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            Occuper occupation = occuperService.getOccuper(id);
            if (occupation != null) {
                occuperService.deleteOccuper(occupation);
                chargerOccuper();
            } else {
                JOptionPane.showMessageDialog(this, "L'occupation avec l'ID " + id + " n'existe pas.");
            }
        }
        dateChooser.setDate(null);
    }
}