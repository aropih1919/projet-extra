<%@ page import="src.main.java.modele.PatientModele" %>

<%
    PatientModele patient = (PatientModele) request.getAttribute("patient");

    if (patient == null) {
        patient = new PatientModele();
    }

    boolean modification = patient.getId_patient() != 0;

    String action = modification
            ? request.getContextPath() + "/patient/update"
            : request.getContextPath() + "/patient/insert";

    String nom = patient.getNom() != null ? patient.getNom() : "Rakoto";
    String prenom = patient.getPrenom() != null ? patient.getPrenom() : "Jean";
    String telephone = patient.getTelephone() != null ? patient.getTelephone() : "0340000000";
    String sexe = patient.getSexe() != null ? patient.getSexe() : "Masculin";
    String adresse = patient.getAdresse() != null ? patient.getAdresse() : "Antananarivo";
    String email = patient.getEmail() != null ? patient.getEmail() : "test.patient@example.com";

    String dateNaissance = patient.getDate_naissance() != null
            ? patient.getDate_naissance().toString()
            : "2000-01-01";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= modification ? "Modifier un patient" : "Ajouter un patient" %></title>
</head>
<body>

    <h1><%= modification ? "Modifier un patient" : "Ajouter un patient" %></h1>

    <form action="<%= action %>" method="post">

        <% if (modification) { %>
            <input type="hidden" name="id_patient" value="<%= patient.getId_patient() %>">
        <% } %>

        <div>
            <label>Nom :</label>
            <input type="text" name="nom" value="<%= nom %>" required>
        </div>

        <div>
            <label>Prénom :</label>
            <input type="text" name="prenom" value="<%= prenom %>" required>
        </div>

        <div>
            <label>Téléphone :</label>
            <input type="text" name="telephone" value="<%= telephone %>">
        </div>

        <div>
            <label>Date de naissance :</label>
            <input type="date" name="date_naissance" value="<%= dateNaissance %>" required>
        </div>

        <div>
            <label>Sexe :</label>
            <select name="sexe" required>
                <option value="Masculin" <%= "Masculin".equals(sexe) ? "selected" : "" %>>
                    Masculin
                </option>
                <option value="Feminin" <%= "Feminin".equals(sexe) ? "selected" : "" %>>
                    Féminin
                </option>
            </select>
        </div>

        <div>
            <label>Adresse :</label>
            <textarea name="adresse"><%= adresse %></textarea>
        </div>

        <div>
            <label>Email :</label>
            <input type="email" name="email" value="<%= email %>">
        </div>

        <br>

        <button type="submit">
            <%= modification ? "Enregistrer les modifications" : "Ajouter le patient" %>
        </button>

        <a href="<%= request.getContextPath() %>/patient/liste">Annuler</a>

    </form>

</body>
</html>