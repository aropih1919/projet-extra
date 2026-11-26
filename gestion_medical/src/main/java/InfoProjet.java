package src.main.java;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// Step 2: Définir la cible (ici, applicable sur les classes et les méthodes)
@Target({ElementType.TYPE, ElementType.METHOD})
// Step 3: Définir la rétention (accessible à l'exécution)
@Retention(RetentionPolicy.RUNTIME)
// Step 1: Déclaration avec @interface
public @interface InfoProjet {
// Step 4: Ajout d'éléments (paramètres)
String auteur();
String version() default "1.0"; // Une valeur par défaut
}