package com.phenix.comparemd5;

/**
 * Compare un dossier via son MD5.
 */
import com.phenix.comparemd5.ui.FenetreNew;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Lance le programme.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Main {

    /**
     * Où commence le programme.
     *
     * @param args Les valeurs reçues par le programme.
     */
    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc="Design de l'application selon l'OS.">
        LookAndFeelInfo[] list = UIManager.getInstalledLookAndFeels();

        try {
            for (LookAndFeelInfo item : list) {
                // Si on trouve un thème "Windows", on le prend !
                if (item.getName().equals("Windows")) {
                    UIManager.setLookAndFeel(item.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exception) {
            System.out.println("Erreur : " + exception.getMessage());
        }
        //</editor-fold>

        // Crée et affiche la fenêtre.
        EventQueue.invokeLater(() -> {
            new FenetreNew().setVisible(true);
        });
    }
}
