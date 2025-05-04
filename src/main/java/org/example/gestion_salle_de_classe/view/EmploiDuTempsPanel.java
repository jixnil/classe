package org.example.gestion_salle_de_classe.view;

import org.example.gestion_salle_de_classe.model.Occuper;
import org.example.gestion_salle_de_classe.model.Salle;
import org.example.gestion_salle_de_classe.service.OccuperService;
import org.example.gestion_salle_de_classe.service.SalleService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class EmploiDuTempsPanel extends JPanel {

    private JTable emploiDuTempsTable;
    private DefaultTableModel tableModel;
    private SalleService salleService;
    private OccuperService occuperService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Map<Salle, Map<String, Boolean>> occupationsMap = new HashMap<>();
    private Map<Integer, Salle> sallesMap = new HashMap<>();

    public EmploiDuTempsPanel(SalleService salleService, OccuperService occuperService) {
        this.salleService = salleService;
        this.occuperService = occuperService;
        initUI();
        chargerEmploiDuTemps();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        emploiDuTempsTable = new JTable(tableModel);
        emploiDuTempsTable.setDefaultRenderer(Object.class, new OccupiedCellRenderer(occupationsMap));
        add(new JScrollPane(emploiDuTempsTable), BorderLayout.CENTER);
    }

    void chargerEmploiDuTemps() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        List<Salle> salles = salleService.getAllSalles();
        List<String> dates = getDatesOfWeek();

        for (Salle salle : salles) {
            sallesMap.put(salle.getCodesal(), salle);
        }

        tableModel.addColumn("Salle");
        for (String date : dates) {
            tableModel.addColumn(date);
        }

        for (Salle salle : salles) {
            Object[] row = new Object[dates.size() + 1];
            row[0] = salle.getCodesal();
            tableModel.addRow(row);
        }

        List<Occuper> occupations = occuperService.getAllOccuper();

        for (Salle salle : salles) {
            Map<String, Boolean> salleOccupations = new HashMap<>();
            for (String date : dates) {
                salleOccupations.put(date, false);
            }
            occupationsMap.put(salle, salleOccupations);
        }

        for (Occuper occupation : occupations) {
            Salle salle = sallesMap.get(occupation.getSalle().getCodesal());
            if (salle != null) {
                occupationsMap.get(salle).put(dateFormat.format(occupation.getDate()), true);
            }
        }
    }

    private List<String> getDatesOfWeek() {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        for (int i = 0; i < 7; i++) {
            dates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private class OccupiedCellRenderer extends DefaultTableCellRenderer {
        private Map<Salle, Map<String, Boolean>> occupationsMap;

        public OccupiedCellRenderer(Map<Salle, Map<String, Boolean>> occupationsMap) {
            this.occupationsMap = occupationsMap;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column > 0) {
                int salleCode = Integer.parseInt(table.getValueAt(row, 0).toString());
                Salle salle = sallesMap.get(salleCode);
                String dateString = table.getColumnName(column);

                if (salle != null && dateString != null) {
                    Map<String, Boolean> salleOccupations = occupationsMap.get(salle);
                    if (salleOccupations != null) {
                        boolean isOccupied = salleOccupations.get(dateString);
                        c.setBackground(isOccupied ? Color.RED : table.getBackground());
                    } else {
                        c.setBackground(table.getBackground());
                    }
                } else {
                    c.setBackground(table.getBackground());
                }
            } else {
                c.setBackground(table.getBackground());
            }
            return c;
        }
    }
}