package com.exemple.qrcode.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.exemple.qrcode.service.QrCodeService;

import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.web.client.RestClient;

import com.exemple.qrcode.model.PatientModele;
@Controller
public class HomeController {

    private final QrCodeService qrCodeService;

    public HomeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(Model model) throws Exception {
        Path dossier = null;
        Path fichier = null;
        String apiRest = "http://localhost:8080/gestion_medical/api/patient/findAll";
        List<PatientModele> patients = listePatients(apiRest);
        for(PatientModele patient : patients) {
            String path = "/usr/local/cdn/patients/" + patient.getId_patient() + "/qrcode/";
            dossier = Paths.get(path);
            if (Files.isDirectory(dossier)) {
                System.out.println("Le dossier existe.");
            } else {
                try {
                    String lien = "http://localhost:7777/patient/" + patient.getId_patient();
                    String nomFichier = "qrcode_" + patient.getId_patient();
                    fichier = qrCodeService.genererEtEnregistrer(lien, path, nomFichier, 300);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la géneration du fichier : " + e.getMessage());
                }
            }
        }
        
        return "index";
    }

    public List<PatientModele> listePatients(String apiRestt) throws Exception {

        RestClient client = RestClient.create();

        String json = client.get()
                .uri("http://localhost:8080/gestion_medical/api/patient/findAll")
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();

        List<PatientModele> patients = mapper.readValue(
                json,
                new TypeReference<List<PatientModele>>() {}
        );

        return patients;
    }
}
