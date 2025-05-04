package org.example.gestion_salle_de_classe.view;

import org.example.gestion_salle_de_classe.model.Salle;
import org.example.gestion_salle_de_classe.service.SalleService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SallePanel extends JPanel {
    private SalleService salleService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JComboBox<String> designationComboBox;

    public SallePanel(SalleService salleService) {
        this.salleService = salleService;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Code");
        tableModel.addColumn("Désignation");
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
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations de la salle"));
        formPanel.add(new JLabel("Code :"));
        codeField = new JTextField();
        formPanel.add(codeField);
        formPanel.add(new JLabel("Désignation :"));
        designationComboBox = new JComboBox<>(new String[]{"Libre", "Occupé"});
        formPanel.add(designationComboBox);

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
                ajouterSalle();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(220, 220, 220));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierSalle();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(220, 220, 220));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerSalle();
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

        chargerSalles();
    }

    private void remplirFormulaire(int row) {
        int codesal = (int) tableModel.getValueAt(row, 0);
        String designation = (String) tableModel.getValueAt(row, 1);

        codeField.setText(String.valueOf(codesal));
        designationComboBox.setSelectedItem(designation);
    }

    void chargerSalles() {
        tableModel.setRowCount(0);
        List<Salle> salles = salleService.getAllSalles();
        for (Salle salle : salles) {
            tableModel.addRow(new Object[]{salle.getCodesal(), salle.getDesignation()});
        }
    }

    private void ajouterSalle() {
        try {
            int codesal = Integer.parseInt(codeField.getText());

            if (salleService.getSalle(codesal) != null) {
                JOptionPane.showMessageDialog(this, "Une salle avec ce code existe déjà.");
                return;
            }

            Salle salle = new Salle();
            salle.setCodesal(codesal);
            salle.setDesignation((String) designationComboBox.getSelectedItem());
            salleService.createSalle(salle);
            chargerSalles();

            codeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le code doit être un nombre.");
        }
    }

    private void modifierSalle() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codesal = (int) tableModel.getValueAt(row, 0);
            Salle salle = salleService.getSalle(codesal);
            salle.setDesignation((String) designationComboBox.getSelectedItem());
            salleService.updateSalle(salle);
            chargerSalles();
        }
    }

    private void supprimerSalle() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int codesal = (int) tableModel.getValueAt(row, 0);
            Salle salle = salleService.getSalle(codesal);
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer cette salle ?",
                    "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                salleService.deleteSalle(salle);
                chargerSalles();
            }
        }
    }
}