package com.phenix.comparemd5.ui;

import com.phenix.comparemd5.exception.CompareMD5Exception;
import com.phenix.comparemd5.util.Utils;
import com.phenix.swing.FileDrop;
import com.phenix.swing.JChooser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
 * Fenêtre principale.
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
            } catch (UnsupportedOperationException exception) {
                System.out.println("The os does not support: 'taskbar.setIconImage'");
            } catch (SecurityException exception) {
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
            this.T_listeFichierSource.setModel(model);
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
            this.T_listeFichierDestination.setModel(model);
        }

        //<editor-fold defaultstate="collapsed" desc="Evènement quand on dépose des fichiers dans le tableau des fichiers sources.">
        new FileDrop(this.SP_fichiersSources, listeFichier -> {
            try {
                DefaultTableModel model = (DefaultTableModel) this.T_listeFichierSource.getModel();
                addListeFichier(model, listeFichier);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Evènement quand on dépose un dossier dans le champ 'TF_dossierDestination'.">
        new FileDrop(this.TF_dossierDestination, listeFichier -> {
            try {
                if (listeFichier.length != 1) {
                    throw new CompareMD5Exception("On ne peut déposer qu'un élement.");
                }

                if (!listeFichier[0].isDirectory()) {
                    throw new CompareMD5Exception("Ca doit être un dossier.");
                }

                this.TF_dossierDestination.setText(listeFichier[0].getAbsolutePath());
            } catch (CompareMD5Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        //</editor-fold>
    }

    /**
     * Ajout une liste de fichiers à un tableau.
     *
     * @param model Le modèle du tableau.
     * @param listeFichier La liste des fichiers.
     */
    private void addListeFichier(@NotNull DefaultTableModel model, @NotNull File[] listeFichier) {
        this.addListeFichier(model, listeFichier, null);
    }

    /**
     * Ajout une liste de fichiers à un tableau.
     *
     * @param model Le modèle du tableau.
     * @param listeFichier La liste des fichiers.
     * @param row Variable en mémoire pour ajouter un fichier au tableau.
     */
    private void addListeFichier(@NotNull DefaultTableModel model, @NotNull File[] listeFichier, @Null String[] row) {
        for (int i = 0; i < listeFichier.length; i++) {
            File fichier = listeFichier[i];

            // Si c'est un dossier, on ajoute tous ses fichiers.
            if (fichier.isDirectory()) {
                this.addListeFichier(model, fichier.listFiles());
            } // Sinon, on ajoute le fichier.
            else {
                row = new String[1];
                row[0] = fichier.getAbsolutePath();
                System.out.println((i + 1) + "/" + listeFichier.length);
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
        SP_fichiersSources = new javax.swing.JScrollPane();
        T_listeFichierSource = new javax.swing.JTable();
        L_destination = new javax.swing.JLabel();
        SP_fichiersDestination = new javax.swing.JScrollPane();
        T_listeFichierDestination = new javax.swing.JTable();
        B_toutEstOk = new javax.swing.JButton();
        B_copie = new javax.swing.JButton();
        PB_progression = new javax.swing.JProgressBar();
        TF_dossierDestination = new javax.swing.JTextField();
        B_viderListe = new javax.swing.JButton();
        B_rechercherDossierDestination = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MD5 de fichier 1.0.0");

        L_source.setLabelFor(T_listeFichierSource);
        L_source.setText("Source");

        T_listeFichierSource.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiersSources.setViewportView(T_listeFichierSource);

        L_destination.setText("Destination");

        T_listeFichierDestination.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiersDestination.setViewportView(T_listeFichierDestination);

        B_toutEstOk.setText("Tout est ok");
        B_toutEstOk.setEnabled(false);

        B_copie.setText("Lancer copie");
        B_copie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_copieActionPerformed(evt);
            }
        });

        PB_progression.setToolTipText("Avancement");

        B_viderListe.setText("Vider liste");
        B_viderListe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_viderListeActionPerformed(evt);
            }
        });

        B_rechercherDossierDestination.setText("...");
        B_rechercherDossierDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_rechercherDossierDestinationActionPerformed(evt);
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
                                .addComponent(B_viderListe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(B_copie))
                            .addComponent(SP_fichiersSources, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(L_source)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(L_destination)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TF_dossierDestination)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_rechercherDossierDestination))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PB_progression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(B_toutEstOk))
                    .addComponent(SP_fichiersDestination))
                .addContainerGap())
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
                            .addComponent(TF_dossierDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B_rechercherDossierDestination))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SP_fichiersDestination, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(SP_fichiersSources))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(B_copie)
                        .addComponent(B_viderListe))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(PB_progression, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(B_toutEstOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Quand on clique sur le bouton "<em>Lancer copie</em>".
     *
     * @param evt L'évènement.
     */
    private void B_copieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_copieActionPerformed
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String dossierDestination = TF_dossierDestination.getText();

                    if (dossierDestination.isBlank()) {
                        throw new CompareMD5Exception("Il faut spécifier un dossier de destination.");
                    }

                    File destination = new File(dossierDestination);

                    if (!destination.exists()) {
                        throw new CompareMD5Exception("Le dossier de destination n'existe pas.");
                    }

                    if (!destination.isDirectory()) {
                        throw new CompareMD5Exception("La destination n'est pas un dossier.");
                    }

                    boolean toutEstOk = true;

                    DefaultTableModel modelDestination = (DefaultTableModel) T_listeFichierDestination.getModel();

                    PB_progression.setMaximum(T_listeFichierSource.getRowCount());

                    // Fait la copie des fichiers + vérifie via MD5 que la copie est conforme :
                    for (int i = 0; i < T_listeFichierSource.getRowCount(); i++) {
                        File fichierSource = new File((String) T_listeFichierSource.getValueAt(i, 0));
                        String[] colonneDestination = new String[3];

                        try {
                            String fichierSourceMd5 = Utils.MD5(fichierSource);

                            File fichierDestination = new File(dossierDestination + File.separator + fichierSource.getName());

                            colonneDestination[0] = fichierDestination.getAbsolutePath();

                            Files.copy(fichierSource.toPath(), fichierDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            String fichierDestinationMd5 = Utils.MD5(fichierDestination);

                            colonneDestination[1] = fichierDestinationMd5;

                            System.out.println(fichierSourceMd5 + " == " + fichierDestinationMd5);

                            if (fichierSourceMd5.equals(fichierDestinationMd5)) {
                                colonneDestination[2] = "OK";
                            } else {
                                colonneDestination[2] = "BAD";
                                toutEstOk = false;
                            }
                        } catch (IOException | NoSuchAlgorithmException exception) {
                            toutEstOk = false;
                            colonneDestination[2] = "BAD";
                            System.out.println("Erreur : " + exception.getMessage());
                        }

                        modelDestination.addRow(colonneDestination);
                        PB_progression.setValue(i + 1);
                    }

                    if (toutEstOk) {
                        B_toutEstOk.setBackground(Color.GREEN);
                    } else {
                        B_toutEstOk.setBackground(Color.RED);
                    }
                } catch (CompareMD5Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        thread.start();
    }//GEN-LAST:event_B_copieActionPerformed

    /**
     * Quand on clique sur le bouton "<em>Vider liste</em>".<br>
     * On vide les tableaux de leur contenu.
     *
     * @param evt L'évènement.
     */
    private void B_viderListeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_viderListeActionPerformed
        DefaultTableModel modelSource = (DefaultTableModel) this.T_listeFichierSource.getModel();
        DefaultTableModel modelDestination = (DefaultTableModel) this.T_listeFichierDestination.getModel();

        modelSource.setRowCount(0);
        modelDestination.setRowCount(0);
    }//GEN-LAST:event_B_viderListeActionPerformed

    /**
     * Quand on clique sur le bouton "<em>...</em>".<br>
     * On ouvre l'explorateur de fichier pour choisir le dossier où on veut
     * sauver les fichiers.
     *
     * @param evt L'évènement.
     */
    private void B_rechercherDossierDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_rechercherDossierDestinationActionPerformed
        JChooser.directory(
                this,
                dossier -> {
                    this.TF_dossierDestination.setText(dossier.getAbsolutePath());
                }
        );
    }//GEN-LAST:event_B_rechercherDossierDestinationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_copie;
    private javax.swing.JButton B_rechercherDossierDestination;
    private javax.swing.JButton B_toutEstOk;
    private javax.swing.JButton B_viderListe;
    private javax.swing.JLabel L_destination;
    private javax.swing.JLabel L_source;
    private javax.swing.JProgressBar PB_progression;
    private javax.swing.JScrollPane SP_fichiersDestination;
    private javax.swing.JScrollPane SP_fichiersSources;
    private javax.swing.JTextField TF_dossierDestination;
    private javax.swing.JTable T_listeFichierDestination;
    private javax.swing.JTable T_listeFichierSource;
    // End of variables declaration//GEN-END:variables
}
