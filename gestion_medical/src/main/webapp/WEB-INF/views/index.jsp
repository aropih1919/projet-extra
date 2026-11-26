<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion Médicale</title>
</head>
<body>

    <h1>Gestion Médicale</h1>

    <p>Bienvenue dans votre application de gestion médicale.</p>

    <hr>

    <h2>Menu principal</h2>

    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/patient/liste">
                Gestion des patients
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/patient/saisie">
                Ajouter un patient
            </a>
        </li>
    </ul>

</body>
</html>