package com.phenix.comparemd5;

/**
 * Compare un dossier via son MD5.
 */
import com.phenix.comparemd5.ui.FenetreNew;
import java.awt.EventQueue;
import javax.swing.UIManager;

/**
 * Lance le programme.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Main {

    /**
     * Où commence le programme.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc="Design de l'application selon l'OS.">
        UIManager.LookAndFeelInfo[] list = UIManager.getInstalledLookAndFeels();

        try {
            for (int i = 0; i < list.length; i++) {
                // Si on trouve un thème "Windows", on le prend !
                if (list[i].getName().equals("Windows")) {
                    UIManager.setLookAndFeel(list[i].getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException exception) {
            System.out.println("Erreur : " + exception.getMessage());
        }
        //</editor-fold>

        // Crée et affiche la fenêtre principale.
        EventQueue.invokeLater(() -> {
            new FenetreNew().setVisible(true);
        });
    }
}
