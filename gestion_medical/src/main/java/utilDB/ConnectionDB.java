package src.main.java.utilDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL      = "jdbc:postgresql://localhost:5432/gestion_medicale";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection instance = null;

    private ConnectionDB() {}

    /**
     * Retourne la connexion active (crée une nouvelle si nécessaire).
     */
    public static Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                Class.forName("org.postgresql.Driver");
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver PostgreSQL introuvable.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de se connecter à la base.", e);
        }
        return instance;
    }

    /**
     * Ferme proprement la connexion.
     */
    public static void closeConnection() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                instance = null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur fermeture connexion : " + e.getMessage());
        }
    }
}