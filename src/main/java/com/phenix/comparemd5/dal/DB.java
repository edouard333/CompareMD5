package com.phenix.comparemd5.dal;

import com.phenix.comparemd5.exception.CompareMD5Exception;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connexion à une DB SQLite.<br>
 * Design pattern : Singleton (optionnel).
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
    private final File fichier;

    /**
     *
     */
    public DB() {
        this(new File("c:\\TMP\\file_md5.db"));
    }

    /**
     * Construit la base de données avec le fichier.
     *
     * @param fichier Le fichier.
     */
    public DB(@NotNull @NotBlank File fichier) {
        this.fichier = fichier;
    }

    /**
     * Ajouter un fichier.
     *
     * @param name
     * @param md5
     */
    public void addFile(@NotNull String name, @NotNull String md5) throws CompareMD5Exception {
        String requete_sql = "INSERT INTO file (name, md5) VALUES(?, ?);";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(requete_sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, md5);
            pstmt.executeUpdate();
        } catch (SQLException exception) {
            throw new CompareMD5Exception(exception.getMessage(), exception);
        }
    }

    /**
     * Donne l'objet de connexion.
     *
     * @return
     *
     * @throws CompareMD5Exception
     */
    @NotNull
    private Connection connect() throws CompareMD5Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + this.fichier.getAbsolutePath());
        } catch (ClassNotFoundException | SQLException exception) {
            throw new CompareMD5Exception(exception.getMessage(), exception);
        }
    }

    /**
     * Tente de créer la base de données.
     *
     * @throws CompareMD5Exception
     */
    public void creer() throws CompareMD5Exception {
        // Créer la DB:
        try (Connection conn = this.connect()) {
            // Créer les tables.
            Statement stmt = this.connect().createStatement();

            // Créer table:
            String requete_sql = "CREATE TABLE IF NOT EXISTS file (\n"
                    + "	id INTEGER PRIMARY KEY,\n" // ID
                    + "	name TEXT NOT NULL,\n"
                    + " md5 TEXT NOT NULL);";

            stmt.execute(requete_sql);

            // Vider la table.
            requete_sql = "DELETE FROM file;";

            stmt.execute(requete_sql);
        } catch (SQLException exception) {
            throw new CompareMD5Exception(exception.getMessage(), exception);
        }
    }

    /**
     * Retourne l'instance de {@link DB}.
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
     * Retourne le MD5 d'un fichier en {@link String}.
     *
     * @param name
     * @return Le MD5 sinon {@code null}.
     *
     * @throws CompareMD5Exception
     */
    @Null
    public String getMD5File(@NotNull String name) throws CompareMD5Exception {
        String requete_sql = "SELECT md5 FROM file WHERE name = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(requete_sql)) {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("md5");
                } else {
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new CompareMD5Exception(exception.getMessage(), exception);
        }
    }
}
