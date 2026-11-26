package src.main.java.controller;

import mg.itu.aropy.annotation.AropyController;
import mg.itu.aropy.annotation.URLMapping;
import mg.itu.aropy.web.ModelAndView;
import mg.itu.aropy.annotation.ResponseBody;

import src.main.java.service.PatientService;
import src.main.java.modele.PatientModele;

import java.sql.Date;
import java.util.Vector;

@AropyController
public class PatientController {

    private PatientService patientService = new PatientService();

    public PatientController() {
    }

    // ================= INDEX =================

    @URLMapping(urlPath="/", method="GET")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    // ================= LISTE =================

    @URLMapping(urlPath="/patient/liste", method="GET")
    public ModelAndView liste() throws Exception {

        ModelAndView mav = new ModelAndView("/patient/liste");

        Vector<PatientModele> patients = patientService.findAll();

        mav.setAttribute("patients", patients);

        return mav;
    }

    // ================= FORMULAIRE AJOUT =================

    @URLMapping(urlPath="/patient/saisie", method="GET")
    public ModelAndView saisie() {

        ModelAndView mav = new ModelAndView("/patient/saisie");

        mav.setAttribute("patient", new PatientModele());

        return mav;
    }

    // ================= INSERT =================

    @URLMapping(urlPath="/patient/insert", method="POST")
    public ModelAndView insert(
            String nom,
            String prenom,
            String telephone,
            Date date_naissance,
            String sexe,
            String adresse,
            String email
    ) throws Exception {

        PatientModele p = new PatientModele();

        p.setNom(nom);
        p.setPrenom(prenom);
        p.setTelephone(telephone);
        p.setDate_naissance(date_naissance);
        p.setSexe(sexe);
        p.setAdresse(adresse);
        p.setEmail(email);

        patientService.insert(p);

        return liste();
    }

    // ================= FORMULAIRE MODIFICATION =================

    @URLMapping(urlPath="/patient/modifier", method="GET")
    public ModelAndView modifier(Integer id) throws Exception {

        ModelAndView mav = new ModelAndView("/patient/saisie");

        PatientModele patient = patientService.findById(id);

        mav.setAttribute("patient", patient);

        return mav;
    }

    // ================= UPDATE =================

    @URLMapping(urlPath="/patient/update", method="POST")
    public ModelAndView update(
            Integer id_patient,
            String nom,
            String prenom,
            String telephone,
            Date date_naissance,
            String sexe,
            String adresse,
            String email
    ) throws Exception {

        PatientModele p = new PatientModele();

        p.setId_patient(id_patient);
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setTelephone(telephone);
        p.setDate_naissance(date_naissance);
        p.setSexe(sexe);
        p.setAdresse(adresse);
        p.setEmail(email);

        patientService.update(p);

        return liste();
    }

    // ================= FICHE =================

    @URLMapping(urlPath="/patient/fiche", method="GET")
    public ModelAndView fiche(Integer id) throws Exception {

        ModelAndView mav = new ModelAndView("/patient/fiche");

        PatientModele patient = patientService.findById(id);

        mav.setAttribute("patient", patient);
        // résolution du chemin vers le QR code
        String QRCodeURL = "http://localhost:8004/patients/" + id + "/qrcode/qrcode_" + id + ".jpg";
        mav.setAttribute("QRCodeURL", QRCodeURL);
        return mav;
    }

    // ================= DELETE =================

    @URLMapping(urlPath="/patient/delete", method="POST")
    public ModelAndView delete(Integer id) throws Exception {

        PatientModele p = new PatientModele();
        p.setId_patient(id);

        patientService.delete(p);

        return liste();
    }

    // API pour FindAll
    @ResponseBody
    @URLMapping(urlPath="/api/patient/findAll", method="GET")
    public Vector<PatientModele> apiFindAll() throws Exception {
        return patientService.findAll();
    }
}