package com.phenix.comparemd5.ui;

import com.phenix.comparemd5.util.Utils;
import com.phenix.swing.FileDrop;
import java.awt.Color;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Fenêtre principale du programme.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class FenetreNew extends JFrame {

    /**
     * Crée la fenêtre.
     */
    public FenetreNew() {
        initComponents();

        // On centre la fenêtre.
        super.setLocationRelativeTo(null);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(FenetreNew.class.getClassLoader().getResource("images/download.png")));
            final Taskbar taskbar = Taskbar.getTaskbar();

            try {
                //set icon for mac os (and other systems which do support this method)
                taskbar.setIconImage(Toolkit.getDefaultToolkit().getImage(FenetreNew.class.getClassLoader().getResource("images/download.png")));
            } catch (final UnsupportedOperationException e) {
                System.out.println("The os does not support: 'taskbar.setIconImage'");
            } catch (final SecurityException e) {
                System.out.println("There was a security exception for: 'taskbar.setIconImage'");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // Initialise le tableau source :
        {
            String[] columns = {"Fichier"};

            DefaultTableModel model = new DefaultTableModel() {
                /**
                 * Pour ne pas pouvoir modifier les colonnes.
                 *
                 * @param row
                 * @param column
                 * @return
                 */
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columns);
            this.T_fichiers_sources.setModel(model);
        }

        // Initialise le tableau destination :
        {
            String[] columns = {"Fichier", "Hash", "Statut"};

            DefaultTableModel model = new DefaultTableModel() {
                /**
                 * Pour ne pas pouvoir modifier les colonnes.
                 *
                 * @param row
                 * @param column
                 * @return
                 */
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnIdentifiers(columns);
            this.T_fichiers_destinations.setModel(model);
        }

        new FileDrop(this.SP_fichiers_sources, files -> {
            try {
                DefaultTableModel model = (DefaultTableModel) this.T_fichiers_sources.getModel();
                addListeFichier(model, files);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Définit le dossier de destination :
        new FileDrop(this.TF_dossier_destination, files -> {
            try {
                if (files.length != 1) {
                    throw new Exception("On ne peut déposer qu'un élement.");
                }

                if (!files[0].isDirectory()) {
                    throw new Exception("Ca doit être un dossier.");
                }

                this.TF_dossier_destination.setText(files[0].getAbsolutePath());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Ajout une liste de fichiers à un tableau.
     *
     * @param model Le modèle du tableau.
     * @param liste_fichier La liste des fichiers.
     */
    private void addListeFichier(DefaultTableModel model, File[] liste_fichier) {
        this.addListeFichier(model, liste_fichier, null);
    }

    /**
     * Ajout une liste de fichiers à un tableau.
     *
     * @param model Le modèle du tableau.
     * @param liste_fichier La liste des fichiers.
     * @param row Variable en mémoire pour ajouter un fichier au tableau.
     */
    private void addListeFichier(DefaultTableModel model, File[] liste_fichier, String[] row) {
        for (int i = 0; i < liste_fichier.length; i++) {
            if (liste_fichier[i].isDirectory()) {
                this.addListeFichier(model, liste_fichier[i].listFiles());
            } else {
                row = new String[1];
                row[0] = liste_fichier[i].getAbsolutePath();
                System.out.println((i + 1) + "/" + liste_fichier.length);
                model.addRow(row);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        L_source = new javax.swing.JLabel();
        SP_fichiers_sources = new javax.swing.JScrollPane();
        T_fichiers_sources = new javax.swing.JTable();
        L_destination = new javax.swing.JLabel();
        SP_fichiers_destination = new javax.swing.JScrollPane();
        T_fichiers_destinations = new javax.swing.JTable();
        B_tout_est_ok = new javax.swing.JButton();
        B_copîe = new javax.swing.JButton();
        PB_progression = new javax.swing.JProgressBar();
        TF_dossier_destination = new javax.swing.JTextField();
        B_vider_liste = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MD5 de fichier 1.0.0");

        L_source.setText("Source");

        T_fichiers_sources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiers_sources.setViewportView(T_fichiers_sources);

        L_destination.setText("Destination");

        T_fichiers_destinations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiers_destination.setViewportView(T_fichiers_destinations);

        B_tout_est_ok.setText("Tout est ok");
        B_tout_est_ok.setEnabled(false);

        B_copîe.setText("Lancer copie");
        B_copîe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_copîeActionPerformed(evt);
            }
        });

        PB_progression.setToolTipText("Avancement");

        B_vider_liste.setText("Vider liste");
        B_vider_liste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_vider_listeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(B_vider_liste)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(B_copîe))
                            .addComponent(SP_fichiers_sources, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(L_source)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SP_fichiers_destination, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(L_destination)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TF_dossier_destination))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PB_progression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(B_tout_est_ok)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(L_source))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(L_destination)
                            .addComponent(TF_dossier_destination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SP_fichiers_destination, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(SP_fichiers_sources))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(B_copîe)
                        .addComponent(B_vider_liste))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(PB_progression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(B_tout_est_ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Quand on clique sur le bouton "<em>Lancer copie</em>".
     *
     * @param evt L'évènement.
     */
    private void B_copîeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_copîeActionPerformed
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    String dossier_destination = TF_dossier_destination.getText();

                    if (dossier_destination.isEmpty()) {
                        throw new Exception("Il faut spécifier un dossier de destination.");
                    }

                    File destination = new File(dossier_destination);

                    if (!destination.exists()) {
                        throw new Exception("Le dossier de destination n'existe pas.");
                    }

                    if (!destination.isDirectory()) {
                        throw new Exception("La destination n'est pas un dossier.");
                    }

                    boolean tout_est_ok = true;

                    File fichier_source;
                    String fichier_source_md5;
                    File fichier_destination;
                    String fichier_destination_md5;

                    String[] colonne_destination;

                    DefaultTableModel model_destination = (DefaultTableModel) T_fichiers_destinations.getModel();

                    PB_progression.setMaximum(T_fichiers_sources.getRowCount());

                    // Fait la copie des fichiers + vérifie via MD5 que la copie est conforme :
                    for (int i = 0; i < T_fichiers_sources.getRowCount(); i++) {
                        fichier_source = new File((String) T_fichiers_sources.getValueAt(i, 0));
                        colonne_destination = new String[3];

                        try {
                            fichier_source_md5 = Utils.MD5(fichier_source);

                            fichier_destination = new File(dossier_destination + File.separator + fichier_source.getName());

                            colonne_destination[0] = fichier_destination.getAbsolutePath();

                            Files.copy(fichier_source.toPath(), fichier_destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            fichier_destination_md5 = Utils.MD5(fichier_destination);

                            colonne_destination[1] = fichier_destination_md5;

                            System.out.println(fichier_source_md5 + " == " + fichier_destination_md5);

                            if (fichier_source_md5.equals(fichier_destination_md5)) {
                                colonne_destination[2] = "OK";
                            } else {
                                colonne_destination[2] = "BAD";
                                tout_est_ok = false;
                            }

                        } catch (IOException | NoSuchAlgorithmException exception) {
                            tout_est_ok = false;
                            colonne_destination[2] = "BAD";
                            System.out.println("Erreur : " + exception.getMessage());
                        }
                        model_destination.addRow(colonne_destination);
                        PB_progression.setValue(i + 1);
                    }

                    if (tout_est_ok) {
                        B_tout_est_ok.setBackground(Color.GREEN);
                    } else {
                        B_tout_est_ok.setBackground(Color.RED);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        th.start();
    }//GEN-LAST:event_B_copîeActionPerformed

    /**
     * Quand on clique sur le bouton "<em>Vider liste</em>".<br>
     * On vide les tableaux de leur contenu.
     *
     * @param evt L'évènement.
     */
    private void B_vider_listeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_vider_listeActionPerformed
        DefaultTableModel model_source = (DefaultTableModel) this.T_fichiers_sources.getModel();
        DefaultTableModel model_destination = (DefaultTableModel) this.T_fichiers_destinations.getModel();

        while (model_source.getRowCount() > 0) {
            model_source.removeRow(0);
        }

        while (model_destination.getRowCount() > 0) {
            model_destination.removeRow(0);
        }
    }//GEN-LAST:event_B_vider_listeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_copîe;
    private javax.swing.JButton B_tout_est_ok;
    private javax.swing.JButton B_vider_liste;
    private javax.swing.JLabel L_destination;
    private javax.swing.JLabel L_source;
    private javax.swing.JProgressBar PB_progression;
    private javax.swing.JScrollPane SP_fichiers_destination;
    private javax.swing.JScrollPane SP_fichiers_sources;
    private javax.swing.JTextField TF_dossier_destination;
    private javax.swing.JTable T_fichiers_destinations;
    private javax.swing.JTable T_fichiers_sources;
    // End of variables declaration//GEN-END:variables
}
