/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Vista.Estado;

import Juego.Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Lewis
 */
public class Pausa extends javax.swing.JPanel {

    private String panelAnterior;
    /**
     * Creates new form Pausa
     */
    public Pausa(final Ventana ventana) {
        initComponents();
        panelAnterior = "Juego";
        
        jButtonRenaudar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setContentPane(panelAnterior);
                
            }
        });
        jButtonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setContentPane("Menu");
            }
        });
        jButtonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.salir();
            }
        });
    }
    
    public void setPanelAnterior(String panelAnterior){
        this.panelAnterior = panelAnterior;
    }

    public void renaudar() {
        jButtonRenaudar.doClick();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonRenaudar = new javax.swing.JButton();
        jButtonMenu = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonRenaudar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Play.png"))); // NOI18N
        jButtonRenaudar.setBorderPainted(false);
        jButtonRenaudar.setContentAreaFilled(false);
        jButtonRenaudar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRenaudar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Play-Press.png"))); // NOI18N
        jButtonRenaudar.setRequestFocusEnabled(false);
        jButtonRenaudar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Play-Rollover.png"))); // NOI18N
        add(jButtonRenaudar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 400, 180, -1));

        jButtonMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Options.png"))); // NOI18N
        jButtonMenu.setBorderPainted(false);
        jButtonMenu.setContentAreaFilled(false);
        jButtonMenu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMenu.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Options-Press.png"))); // NOI18N
        jButtonMenu.setRequestFocusEnabled(false);
        jButtonMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Options-Rollover.png"))); // NOI18N
        add(jButtonMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 490, 180, -1));

        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel.png"))); // NOI18N
        jButtonSalir.setBorderPainted(false);
        jButtonSalir.setContentAreaFilled(false);
        jButtonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel-Press.png"))); // NOI18N
        jButtonSalir.setRequestFocusEnabled(false);
        jButtonSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel-Rollover.png"))); // NOI18N
        add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, -1, -1));

        jLabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Paused.png"))); // NOI18N
        jLabelTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Fondo-Opaco.png"))); // NOI18N
        jLabelFondo.setText("jLabel5");
        add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonMenu;
    private javax.swing.JButton jButtonRenaudar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelTitulo;
    // End of variables declaration//GEN-END:variables
}
