# QR Code App

Projet Spring Boot minimal (Java 17, Maven, Thymeleaf), prévu pour accueillir un générateur de QR code.

## Prérequis

- Java JDK 17 ou plus récent
- Maven 3.9+ (ou un IDE qui l'intègre : IntelliJ IDEA, Eclipse, VS Code)

Vérification :

```bash
java -version
mvn -version
```

## Lancer l'application

### Option 1 : en ligne de commande

Depuis la racine du projet (là où se trouve `pom.xml`) :

```bash
mvn spring-boot:run
```

### Option 2 : depuis un IDE

1. Ouvrir le dossier du projet (il sera reconnu comme projet Maven).
2. Attendre le téléchargement des dépendances.
3. Exécuter la classe `QrcodeApplication` (clic droit > Run).

### Option 3 : générer un JAR

```bash
mvn clean package
java -jar target/qrcode-app-0.0.1-SNAPSHOT.jar
```

## Accéder à l'application

Ouvrir http://localhost:8080 dans le navigateur.
La page « Générateur de QR Code » doit s'afficher.

## Arrêter l'application

`Ctrl + C` dans le terminal (ou le bouton Stop de l'IDE).

## Changer le port

Dans `src/main/resources/application.properties` :

```properties
server.port=9090
```

## Utilisation

1. Saisir une phrase dans le champ de texte.
2. Cliquer sur « Générer » : le QR code s'affiche et peut être téléchargé en PNG.

## Structure du projet

```
src/main/java/com/exemple/qrcode/
├── QrcodeApplication.java              # Point d'entrée
├── controller/HomeController.java      # Gère la page et le formulaire
└── service/QrCodeService.java          # Génération du QR code (ZXing)
src/main/resources/
├── templates/index.html                # Vue Thymeleaf (formulaire + résultat)
└── application.properties              # Configuration
```

## Problèmes courants

- **Port 8080 déjà utilisé** : changer `server.port` (voir ci-dessus).
- **`release version 17 not supported`** : votre JDK est trop ancien, installez Java 17+.
- **`mvn` introuvable** : installez Maven ou utilisez votre IDE.
