package com.phenix.comparemd5.dal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Test de connexion à une DB SQLite.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class DB {

    /**
     * Où se trouve la base de données sur l'ordinateur.
     */
    private static final String URL = "jdbc:sqlite:c:\\TMP\\file_md5.db";

    /**
     * Ajouter un fichier.
     *
     * @param name
     * @param md5
     */
    public void addFile(String name, String md5) {
        String sql = "INSERT INTO file (name, md5) VALUES(?, ?);";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, md5);

            pstmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Donne l'objet de connexion.
     *
     * @return
     */
    private static Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return conn;
    }

    /**
     * Tente de créer la base de données.
     */
    public void creer() {
        // Créer la DB:
        try {

            Connection conn = DriverManager.getConnection(URL);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

            // Créer les tables.
            Statement stmt = DriverManager.getConnection(URL).createStatement();

            // Créer table:
            String sql = "CREATE TABLE IF NOT EXISTS file (\n"
                    + "	id INTEGER PRIMARY KEY,\n" // ID
                    + "	name TEXT NOT NULL,\n"
                    + " md5 TEXT NOT NULL);";

            stmt.execute(sql);

            // Vider la table.
            sql = "DELETE FROM file;";

            stmt.execute(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Retourne le MD5 d'un fichier en <code>String</code>.
     *
     * @param name
     * @return
     */
    public String getMD5File(String name) {
        String sql = "SELECT md5 FROM file WHERE name = \"" + name + "\"";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            if (rs.next()) {
                return rs.getString("md5");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        // En cas d'erreur, retourne null.
        return null;
    }

}
