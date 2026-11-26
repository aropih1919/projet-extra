<%@ page import="src.main.java.modele.PatientModele" %>

<%
    PatientModele patient = (PatientModele) request.getAttribute("patient");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fiche du patient</title>
</head>
<body>

    <h1>Fiche du patient</h1>

    <% if (patient != null) { %>

        <p><strong>Identifiant :</strong> <%= patient.getId_patient() %></p>

        <p><strong>Nom :</strong> <%= patient.getNom() %></p>

        <p><strong>Prénom :</strong> <%= patient.getPrenom() %></p>

        <p><strong>Téléphone :</strong>
            <%= patient.getTelephone() != null ? patient.getTelephone() : "" %>
        </p>

        <p><strong>Date de naissance :</strong> <%= patient.getDate_naissance() %></p>

        <p><strong>Sexe :</strong> <%= patient.getSexe() %></p>

        <p><strong>Adresse :</strong>
            <%= patient.getAdresse() != null ? patient.getAdresse() : "" %>
        </p>

        <p><strong>Email :</strong>
            <%= patient.getEmail() != null ? patient.getEmail() : "" %>
        </p>

        <p><strong>Date de création :</strong> <%= patient.getDate_creation() %></p>


        <br>

        <a href="<%= request.getContextPath() %>/patient/modifier?id=<%= patient.getId_patient() %>">
            Modifier ce patient
        </a>

    <% } else { %>

        <p>Patient introuvable.</p>

    <% } %>

    <br><br>

    <a href="<%= request.getContextPath() %>/patient/liste">
        Retour à la liste
    </a>

</body>
</html>