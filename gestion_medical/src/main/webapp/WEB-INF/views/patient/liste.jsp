<%@ page import="java.util.Vector" %>
<%@ page import="src.main.java.modele.PatientModele" %>

<%
    Vector<PatientModele> patients =
            (Vector<PatientModele>) request.getAttribute("patients");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des patients</title>
</head>
<body>

    <h1>Liste des patients</h1>

    <a href="<%= request.getContextPath() %>/patient/saisie">
        Ajouter un patient
    </a>

    <br><br>

    <table border="1" cellpadding="8" cellspacing="0">

        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>Date de naissance</th>
                <th>Sexe</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>

        <% if (patients != null && !patients.isEmpty()) { %>

            <% for (PatientModele p : patients) { %>

                <tr>
                    <td><%= p.getId_patient() %></td>
                    <td><%= p.getNom() %></td>
                    <td><%= p.getPrenom() %></td>
                    <td><%= p.getTelephone() != null ? p.getTelephone() : "" %></td>
                    <td><%= p.getDate_naissance() %></td>
                    <td><%= p.getSexe() %></td>

                    <td>
                        <a href="<%= request.getContextPath() %>/patient/fiche?id=<%= p.getId_patient() %>">
                            Voir
                        </a>

                        |

                        <a href="<%= request.getContextPath() %>/patient/modifier?id=<%= p.getId_patient() %>">
                            Modifier
                        </a>

                        |

                        <form action="<%= request.getContextPath() %>/patient/delete"
                              method="post"
                              style="display:inline;">

                            <input type="hidden"
                                   name="id"
                                   value="<%= p.getId_patient() %>">

                            <button type="submit"
                                    onclick="return confirm('Voulez-vous vraiment supprimer ce patient ?')">
                                Supprimer
                            </button>

                        </form>
                    </td>
                </tr>

            <% } %>

        <% } else { %>

            <tr>
                <td colspan="7">Aucun patient enregistré.</td>
            </tr>

        <% } %>

        </tbody>

    </table>

</body>
</html>