package com.exemple.qrcode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
@Service
public class QrCodeService {

    /**
     * Génère un QR code PNG à partir d'un texte et le retourne encodé en Base64.
     */
    public String genererBase64(String texte, int taille) {
        try {
            Map<EncodeHintType, Object> options = Map.of(
                    EncodeHintType.CHARACTER_SET, "UTF-8",
                    EncodeHintType.MARGIN, 1
            );

            BitMatrix matrice = new QRCodeWriter()
                    .encode(texte, BarcodeFormat.QR_CODE, taille, taille, options);

            ByteArrayOutputStream sortie = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrice, "PNG", sortie);

            return Base64.getEncoder().encodeToString(sortie.toByteArray());
        } catch (WriterException | IOException e) {
            throw new IllegalStateException("Impossible de générer le QR code", e);
        }
    }

    public Path genererEtEnregistrer(String texte, String dossierDestination,
                                 String nomFichier, int taille)
        throws WriterException, IOException {

        // 1. Préparer le dossier de destination (le crée s'il n'existe pas)
        Path dossier = Paths.get(dossierDestination);
        Files.createDirectories(dossier);

        // 2. Générer la matrice du QR code à partir de la phrase
        BitMatrix matrice = new QRCodeWriter().encode(
                texte, BarcodeFormat.QR_CODE, taille, taille,
                Map.of(EncodeHintType.CHARACTER_SET, "UTF-8",
                    EncodeHintType.MARGIN, 1));

        // 3. Enregistrer l'image en JPEG dans le dossier
        Path fichier = dossier.resolve(nomFichier + ".jpg");
        MatrixToImageWriter.writeToPath(matrice, "JPG", fichier);

        return fichier; // chemin complet du fichier créé
    }
}
