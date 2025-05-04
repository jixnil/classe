package org.example.gestion_salle_de_classe.service;

import org.example.gestion_salle_de_classe.dao.OccuperDAO;
import org.example.gestion_salle_de_classe.model.Occuper;

import java.util.List;
import java.util.Date;

public class OccuperService {
    private OccuperDAO occuperDao;

    public OccuperService(OccuperDAO occuperDao) {
        this.occuperDao = occuperDao;
    }

    public void createOccuper(Occuper occuper) {
        occuperDao.create(occuper);
    }

    public Occuper getOccuper(int id) {
        return occuperDao.read(id);
    }
    public boolean isSalleOccupee(int salleCode, Date date) {
        List<Occuper> occupations = OccuperDAO.findBySalleAndDate(salleCode, date);
        return !occupations.isEmpty();
    }

    public boolean isProfOccupe(int profCode, Date date) {
        List<Occuper> occupations = OccuperDAO.findByProfAndDate(profCode, date);
        return !occupations.isEmpty();
    }

    public void updateOccuper(Occuper occuper) {
        occuperDao.update(occuper);
    }

    public void deleteOccuper(Occuper occuper) {
        occuperDao.delete(occuper);
    }

    public List<Occuper> getAllOccuper() {
        return occuperDao.getAll();
    }
}
