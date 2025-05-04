package org.example.gestion_salle_de_classe.service;
import org.example.gestion_salle_de_classe.dao.ProfDAO;
import org.example.gestion_salle_de_classe.model.Prof;

import java.util.List;

public class ProfService {
    private ProfDAO ProfDAO;

    public ProfService(ProfDAO profDao) {
        this.ProfDAO = profDao;
    }

    public void createProf(Prof prof) {
        ProfDAO.create(prof);
    }
    public List<Prof> getAllProfs() {
        return ProfDAO.findAll();
    }
    public Prof getProf(int codeprof) {
        return ProfDAO.read(codeprof);
    }

    public void updateProf(Prof prof) {
        ProfDAO.update(prof);
    }

    public void deleteProf(Prof prof) {
        ProfDAO.delete(prof);
    }

    public List<Prof> findProfs(String codeOrName) {
        return ProfDAO.findByCodeOrName(codeOrName);
    }
}