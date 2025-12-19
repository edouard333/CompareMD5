package com.phenix.comparemd5.ui;

import com.phenix.comparemd5.util.Utils;
import com.phenix.swing.FileDrop;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Fenêtre principale du programme.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Fenetre extends JFrame {

    /**
     * Crée la fenêtre.
     */
    public Fenetre() {
        initComponents();

        // On centre la fenêtre.
        super.setLocationRelativeTo(null);

        //<editor-fold defaultstate="collapsed" desc="Evènement drag and drop pour SP_fichiersSources.">
        new FileDrop(this.SP_fichiersSources, listeFichier -> {
            initTableau(this.T_fichiersSources, listeFichier);
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Evènement drag and drop pour SP_fichiersDestination.">
        new FileDrop(this.SP_fichiersDestination, listeFichier -> {
            initTableau(this.T_fichierDestination, listeFichier);

            boolean toutEstOk = true;

            // Vérifie que les MD5 source et destination sont les mêmes...
            for (int i = 0; i < this.T_fichierDestination.getRowCount(); i++) {
                String nomFichier = (String) this.T_fichierDestination.getValueAt(i, 0);
                String fichierSourceMd5 = getMD5Source(nomFichier);

                System.out.println(fichierSourceMd5 + " == " + this.T_fichierDestination.getValueAt(i, 1));

                if (fichierSourceMd5.equals(this.T_fichierDestination.getValueAt(i, 1))) {
                    this.T_fichierDestination.setValueAt("OK", i, 2);
                } else {
                    this.T_fichierDestination.setValueAt("BAD", i, 2);
                    toutEstOk = false;
                }
            }

            if (toutEstOk) {
                this.B_toutEstOk.setBackground(Color.GREEN);
            } else {
                this.B_toutEstOk.setBackground(Color.RED);
            }
        });
        //</editor-fold>
    }

    /**
     *
     * @param fichier
     * @return
     */
    private String getMD5Source(String fichier) {
        for (int i = 0; i < this.T_fichiersSources.getRowCount(); i++) {
            String fichierSource = (String) this.T_fichiersSources.getValueAt(i, 0);

            if (fichierSource.equals(fichier)) {
                return (String) this.T_fichiersSources.getValueAt(i, 1);
            }
        }

        return "-1";
    }

    /**
     * Initialise un tableau.
     *
     * @param tableau Le tableau.
     * @param files Liste de fichier.
     */
    private void initTableau(@NotNull JTable tableau, @NotNull File[] files) {
        String[] columns = {"Fichier", "Hash", "OK"};

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
        tableau.setModel(model);

        System.out.println("Calcule hash :");

        Object[] row = new Object[columns.length];

        // Fait la liste de fichier :
        listFichier(files, row, model);
    }

    /**
     * Analyse d'un ensemble de fichier.
     *
     * @param files Liste de fichier.
     * @param row La ligne.
     * @param model Le modèle du tableau.
     */
    private void listFichier(@NotNull File[] files, @NotNull @NotEmpty Object[] row, @NotNull DefaultTableModel model) {
        for (int i = 0; i < files.length; i++) {
            File file = files[i];

            if (file.isDirectory()) {
                listFichier(file.listFiles(), row, model);
            } else {
                addFile(file, row, model);
                System.out.println((i + 1) + "/" + files.length);
            }
        }
    }

    /**
     * Ajoute un fichier à la liste.
     *
     * @param file Le fichier.
     * @param row La ligne.
     * @param model Le modèle du tableau.
     */
    private void addFile(@NotNull File file, @NotNull @NotEmpty Object[] row, @NotNull DefaultTableModel model) {
        row[0] = file.getName();

        try {
            row[1] = Utils.MD5(file);
        } catch (IOException | NoSuchAlgorithmException exception) {
            // Ajoute à la cellule l'erreur.
            row[1] = exception.getMessage();
            exception.printStackTrace();
        }

        row[2] = "";

        model.addRow(row);
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
        T_fichiersSources = new javax.swing.JTable();
        L_destination = new javax.swing.JLabel();
        SP_fichiersDestination = new javax.swing.JScrollPane();
        T_fichierDestination = new javax.swing.JTable();
        B_toutEstOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MD5 de fichier 1.0.0");

        L_source.setText("Source");

        T_fichiersSources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiersSources.setViewportView(T_fichiersSources);

        L_destination.setText("Destination");

        T_fichierDestination.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SP_fichiersDestination.setViewportView(T_fichierDestination);

        B_toutEstOk.setText("Tout est ok");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(L_source)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(L_destination)
                .addGap(206, 206, 206))
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 625, Short.MAX_VALUE)
                .addComponent(B_toutEstOk)
                .addGap(195, 195, 195))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(SP_fichiersSources, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SP_fichiersDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(L_source)
                    .addComponent(L_destination))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SP_fichiersDestination, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(SP_fichiersSources))
                .addGap(30, 30, 30)
                .addComponent(B_toutEstOk)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_toutEstOk;
    private javax.swing.JLabel L_destination;
    private javax.swing.JLabel L_source;
    private javax.swing.JScrollPane SP_fichiersDestination;
    private javax.swing.JScrollPane SP_fichiersSources;
    private javax.swing.JTable T_fichierDestination;
    private javax.swing.JTable T_fichiersSources;
    // End of variables declaration//GEN-END:variables
}
