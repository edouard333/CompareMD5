package com.phenix.comparemd5.dal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public final class DB {

    /**
     * La seule instance.
     */
    private static DB instance;

    /**
     * Où se trouve la base de données sur l'ordinateur.
     */
    @NotNull
    @NotBlank
    private final String url;

    /**
     *
     */
    private DB() {
        this.url = "jdbc:sqlite:c:\\TMP\\file_md5.db";
    }

    /**
     *
     * @param url
     */
    private DB(String url) {
        this.url = url;
    }

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
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(this.url);
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

            Connection conn = DriverManager.getConnection(this.url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

            // Créer les tables.
            Statement stmt = DriverManager.getConnection(this.url).createStatement();

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
     * Retourne l'instance de {@link DB}.<br>
     *
     * @return L'instance.
     */
    @NotNull
    public static synchronized DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }

        return instance;
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
