package src.main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.Vector;

import src.main.java.modele.PatientModele;
import src.main.java.utilDB.ConnectionDB;

public class PatientDAO {

    private ConnectionDB connectionDB;

    public ConnectionDB getConnectionDB() {
        return connectionDB;
    }

    public void setConnectionDB(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    public PatientDAO() {
    }

    // ================= INSERT =================

    public void insert(PatientModele p) throws Exception {
        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);

            insert(p, conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionDB.closeConnection();
            }
        }
    }

    public void insert(PatientModele p, Connection conn) throws Exception {

        String sql = "INSERT INTO patient (nom, prenom, telephone, date_naissance, sexe, adresse, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getTelephone());
            ps.setDate(4, p.getDate_naissance());
            ps.setString(5, p.getSexe());
            ps.setString(6, p.getAdresse());
            ps.setString(7, p.getEmail());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new Exception("Insertion du patient échouée.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId_patient(rs.getInt(1));
                }
            }
        }
    }

    // ================= FIND ALL =================

    public Vector<PatientModele> findAll() throws Exception {

        Vector<PatientModele> liste = new Vector<>();

        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            findAll(conn, liste);

        } finally {
            ConnectionDB.closeConnection();
        }

        return liste;
    }

    public void findAll(Connection conn, Vector<PatientModele> liste) throws Exception {

        String sql = "SELECT * FROM patient ORDER BY id_patient";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                PatientModele p = new PatientModele();

                p.setId_patient(rs.getInt("id_patient"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setTelephone(rs.getString("telephone"));
                p.setDate_naissance(rs.getDate("date_naissance"));
                p.setSexe(rs.getString("sexe"));
                p.setAdresse(rs.getString("adresse"));
                p.setEmail(rs.getString("email"));
                p.setDate_creation(rs.getTimestamp("date_creation"));

                liste.add(p);
            }
        }
    }

    // ================= FIND BY ID =================

    public PatientModele findById(int id) throws Exception {

        Connection conn = null;
        PatientModele p = null;

        try {
            conn = ConnectionDB.getConnection();
            p = findById(id, conn);

        } finally {
            ConnectionDB.closeConnection();
        }

        return p;
    }

    public PatientModele findById(int id, Connection conn) throws Exception {

        String sql = "SELECT * FROM patient WHERE id_patient = ?";

        PatientModele p = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    p = new PatientModele();

                    p.setId_patient(rs.getInt("id_patient"));
                    p.setNom(rs.getString("nom"));
                    p.setPrenom(rs.getString("prenom"));
                    p.setTelephone(rs.getString("telephone"));
                    p.setDate_naissance(rs.getDate("date_naissance"));
                    p.setSexe(rs.getString("sexe"));
                    p.setAdresse(rs.getString("adresse"));
                    p.setEmail(rs.getString("email"));
                    p.setDate_creation(rs.getTimestamp("date_creation"));
                }
            }
        }

        return p;
    }

    // ================= UPDATE =================

    public void update(PatientModele p) throws Exception {

        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);

            update(p, conn);

            conn.commit();

        } catch (Exception e) {

            if (conn != null) {
                conn.rollback();
            }

            throw e;

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionDB.closeConnection();
            }
        }
    }

    public void update(PatientModele p, Connection conn) throws Exception {

        String sql = "UPDATE patient SET nom = ?, prenom = ?, telephone = ?, "
                   + "date_naissance = ?, sexe = ?, adresse = ?, email = ? "
                   + "WHERE id_patient = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getTelephone());
            ps.setDate(4, p.getDate_naissance());
            ps.setString(5, p.getSexe());
            ps.setString(6, p.getAdresse());
            ps.setString(7, p.getEmail());
            ps.setInt(8, p.getId_patient());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new Exception("Patient introuvable : id = " + p.getId_patient());
            }
        }
    }

    // ================= DELETE =================

    public void delete(PatientModele p) throws Exception {

        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);

            delete(p, conn);

            conn.commit();

        } catch (Exception e) {

            if (conn != null) {
                conn.rollback();
            }

            throw e;

        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionDB.closeConnection();
            }
        }
    }

    public void delete(PatientModele p, Connection conn) throws Exception {

        String sql = "DELETE FROM patient WHERE id_patient = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getId_patient());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new Exception("Patient introuvable : id = " + p.getId_patient());
            }
        }
    }
}