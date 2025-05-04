package org.example.gestion_salle_de_classe.service;

import org.example.gestion_salle_de_classe.dao.SalleDAO;
import org.example.gestion_salle_de_classe.model.Salle;

import java.util.List;

public class SalleService {
    private SalleDAO salleDao;

    public SalleService(SalleDAO salleDao) {
        this.salleDao = salleDao;
    }

    public void createSalle(Salle salle) {
        salleDao.create(salle);
    }

    public Salle getSalle(int codesal) {
        return salleDao.read(codesal);
    }

    public void updateSalle(Salle salle) {
        salleDao.update(salle);
    }

    public void deleteSalle(Salle salle) {
        salleDao.delete(salle);
    }

    public List<Salle> getAllSalles() {
        return salleDao.getAll();
    }
}
