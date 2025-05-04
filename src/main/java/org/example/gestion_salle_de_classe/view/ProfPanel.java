package org.example.gestion_salle_de_classe.view;

import org.example.gestion_salle_de_classe.model.Prof;
import org.example.gestion_salle_de_classe.service.ProfService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfPanel extends JPanel {
    private ProfService profService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField codeField, nomField, prenomField, searchField,gradeField;
    private JComboBox<String> gradeComboBox; // JComboBox pour les grades
    private String[] grades = {"Docteur", "HDR", "Professeur", "Maître de conférences", "Assistant", "Chargé de cours"};

    public ProfPanel(ProfService profService) {
        this.profService = profService;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherProfs();
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(searchPanel, gbc);

        // Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Code");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Grade");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du professeur"));
        formPanel.add(new JLabel("Code :"));
        codeField = new JTextField();
        formPanel.add(codeField);
        formPanel.add(new JLabel("Nom :"));
        nomField = new JTextField();
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom :"));
        prenomField = new JTextField();
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Grade :"));
        gradeComboBox = new JComboBox<>(grades); // Initialisation du JComboBox
        formPanel.add(gradeComboBox);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(formPanel, gbc);

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

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(220, 220, 220));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterProf();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(220, 220, 220));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierProf();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(220, 220, 220));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerProf();
            }
        });
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        chargerProfs();
    }

    void chargerProfs() {
        tableModel.setRowCount(0);
        List<Prof> profs = profService.findProfs("");
        for (Prof prof : profs) {
            tableModel.addRow(new Object[]{prof.getCodeprof(), prof.getNom(), prof.getPrenom(), prof.getGrade()});
        }
    }

    private void ajouterProf() {
        try {
            int codeprof = Integer.parseInt(codeField.getText());

            if (profService.getProf(codeprof) != null) {
                JOptionPane.showMessageDialog(this, "Un professeur avec ce code existe déjà.");
                return;
            }

            Prof prof = new Prof();
            prof.setCodeprof(codeprof);
            prof.setNom(nomField.getText());
            prof.setPrenom(prenomField.getText());
            prof.setGrade((String) gradeComboBox.getSelectedItem()); // Récupération du grade depuis le JComboBox
            profService.createProf(prof);
            chargerProfs();

            codeField.setText("");
            nomField.setText("");
            prenomField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le code doit être un nombre.");
        }
    }

    private void modifierProf() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codeprof = (int) tableModel.getValueAt(row, 0);
            Prof prof = profService.getProf(codeprof);
            prof.setNom(nomField.getText());
            prof.setPrenom(prenomField.getText());
            prof.setGrade((String) gradeComboBox.getSelectedItem()); // Récupération du grade depuis le JComboBox
            profService.updateProf(prof);
            chargerProfs();
        }

        codeField.setText("");
        nomField.setText("");
        prenomField.setText("");
    }
    private void remplirFormulaire(int row) {
        int codeprof = (int) tableModel.getValueAt(row, 0);
        String nom = (String) tableModel.getValueAt(row, 1);
        String prenom = (String) tableModel.getValueAt(row, 2);
        String grade = (String) tableModel.getValueAt(row, 3);

        codeField.setText(String.valueOf(codeprof));
        nomField.setText(nom);
        prenomField.setText(prenom);
        gradeField.setText(grade);
    }

    private void supprimerProf() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codeprof = (int) tableModel.getValueAt(row, 0);
            Prof prof = profService.getProf(codeprof);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer ce professeur ?",
                    "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                profService.deleteProf(prof);
                chargerProfs();
            }
        }
        codeField.setText("");
        nomField.setText("");
        prenomField.setText("");
        gradeField.setText("");
    }

    private void rechercherProfs() {
        String codeOrName = searchField.getText();
        tableModel.setRowCount(0);
        List<Prof> profs = profService.findProfs(codeOrName);
        for (Prof prof : profs) {
            tableModel.addRow(new Object[]{prof.getCodeprof(), prof.getNom(), prof.getPrenom(), prof.getGrade()});
        }
    }
}