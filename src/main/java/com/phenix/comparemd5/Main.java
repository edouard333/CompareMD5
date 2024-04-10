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
public class Main {

    /**
     * Où commence le programme.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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

        /* Créé et affiche la fenêtre principale. */
        EventQueue.invokeLater(() -> {
            new FenetreNew().setVisible(true);
        });
    }
}
