package src.main.java.service;

import java.util.Vector;

import src.main.java.dao.PatientDAO;
import src.main.java.modele.PatientModele;

public class PatientService {

    private PatientDAO patientDAO = new PatientDAO();

    public void insert(PatientModele p) throws Exception {
        patientDAO.insert(p);
    }

    public Vector<PatientModele> findAll() throws Exception {
        return patientDAO.findAll();
    }

    public PatientModele findById(int id) throws Exception {
        return patientDAO.findById(id);
    }

    public void update(PatientModele p) throws Exception {
        patientDAO.update(p);
    }

    public void delete(PatientModele p) throws Exception {
        patientDAO.delete(p);
    }
}