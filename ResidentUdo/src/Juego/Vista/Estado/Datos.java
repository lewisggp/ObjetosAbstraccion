/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Vista.Estado;

import Juego.Controlador.Cargador;
import Juego.Modelo.Estructura.ListaCircular;
import Juego.Vista.Ventana;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Lewis
 */
public class Datos extends javax.swing.JPanel {

    private ListaCircular personajes;
    private ListaCircular disponibles;

    /**
     * Creates new form Presentacion
     */
    public Datos(final Ventana ventana) {
        initComponents();

        disponibles = new ListaCircular();
        disponibles.add(Cargador.ENTE_DEFAULT);
        
        personajes = Cargador.personajes("/entes");
        setSeleccionado(Cargador.ENTE_DEFAULT);
        actualizarPersonajes();

        jButtonJugador1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jugadorSeleccionado = personajes.getFirstNodo().get().toString();
                if(disponibles.contains(jugadorSeleccionado)) {
                    ventana.setJugadorSeleccionado(jugadorSeleccionado);
                    ventana.setContentPane("Menu");                    
                } else {
                    Opcion.showMessage(ventana, true, "No disponible");
                }
            }
        });
        jButtonJugador2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jugadorSeleccionado = personajes.getFirstNodo().getNext().get().toString();
                if(disponibles.contains(jugadorSeleccionado)) {
                    ventana.setJugadorSeleccionado(jugadorSeleccionado);
                    ventana.setContentPane("Menu");                    
                } else {
                    Opcion.showMessage(ventana, true, "No disponible");
                }
            }
        });
        jButtonJugador3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jugadorSeleccionado = personajes.getFirstNodo().getNext().getNext().get().toString();
                if(disponibles.contains(jugadorSeleccionado)) {
                    ventana.setJugadorSeleccionado(jugadorSeleccionado);
                    ventana.setContentPane("Menu");                    
                } else {
                    Opcion.showMessage(ventana, true, "No disponible");
                }
            }
        });
        jButtonJugador4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String jugadorSeleccionado = personajes.getFirstNodo().getNext().getNext().getNext().get().toString();
                if(disponibles.contains(jugadorSeleccionado)) {
                    ventana.setJugadorSeleccionado(jugadorSeleccionado);
                    ventana.setContentPane("Menu");                    
                } else {
                    Opcion.showMessage(ventana, true, "No disponible");
                }
            }
        });
        jButtonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.salir();
            }
        });
    }
    
    public ListaCircular getDisponibles() {
        return disponibles;
    }
    
    public void setDisponibles(ListaCircular disponibles) {
        this.disponibles = disponibles;
    }
    
    public void setSeleccionado(String seleccionado) {
        if(personajes.contains(seleccionado)) {
            boolean girar = true;
            while (girar) {
                if(seleccionado.equals(personajes.getFirstNodo().get().toString())) {
                    girar = false;
                } else {
                    personajes.girarNext();
                }
            }
        }
        actualizarPersonajes();
    }
    
    private void actualizarPersonajes() {
        try {
            String x = personajes.getFirstNodo().get().toString();
            if(disponibles.contains(x)) {
                jLabelJugador1.setIcon(new ImageIcon(Cargador.imagen("/entes/" + x + "/" + x.split("_")[0] + ".png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
            }
            else jLabelJugador1.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        } catch (Exception e) {
            jLabelJugador1.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        }
        
        try {
            String x = personajes.getFirstNodo().getNext().get().toString();
            if(disponibles.contains(x)) {
                Icon icon = new ImageIcon(Cargador.imagen("/entes/" + x + "/" + x.split("_")[0] + ".png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT));
                jLabelJugador2.setIcon(icon);
            }
            else
                jLabelJugador2.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        } catch (Exception e) {
            jLabelJugador2.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        }
        
        try {
            String x = personajes.getFirstNodo().getNext().getNext().get().toString();
            if(disponibles.contains(x)) {
                Icon icon = new ImageIcon(Cargador.imagen("/entes/" + x + "/" + x.split("_")[0] + ".png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT));
                jLabelJugador3.setIcon(icon);
            }
            else
                jLabelJugador3.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        } catch (Exception e) {
            jLabelJugador3.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        }
        
        try {
            String x = personajes.getFirstNodo().getNext().getNext().getNext().get().toString();
            if(disponibles.contains(x)) {
                Icon icon = new ImageIcon(Cargador.imagen("/entes/" + x + "/" + x.split("_")[0] + ".png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT));
                jLabelJugador4.setIcon(icon);
            }
            else
                jLabelJugador4.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        } catch (Exception e) {
            jLabelJugador4.setIcon(new ImageIcon(Cargador.imagen("/entes/" + "unknown.png", true).getScaledInstance(160, 160, Image.SCALE_DEFAULT)));
        }
    }

    public void enter() {
        jButtonJugador1.doClick();
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelJugador1 = new javax.swing.JLabel();
        jLabelJugador4 = new javax.swing.JLabel();
        jLabelJugador3 = new javax.swing.JLabel();
        jLabelJugador2 = new javax.swing.JLabel();
        jButtonAnterior = new javax.swing.JButton();
        jButtonSiguiente = new javax.swing.JButton();
        jButtonJugador1 = new javax.swing.JButton();
        jButtonJugador2 = new javax.swing.JButton();
        jButtonJugador3 = new javax.swing.JButton();
        jButtonJugador4 = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelJugador = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelJugador1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/entes/Blue_32x32/Blue.png"))); // NOI18N
        jLabelJugador1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabelJugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 440, 160, 160));

        jLabelJugador4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/entes/Blue_32x32/Blue.png"))); // NOI18N
        jLabelJugador4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabelJugador4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 440, 160, 160));

        jLabelJugador3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/entes/Blue_32x32/Blue.png"))); // NOI18N
        jLabelJugador3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabelJugador3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 440, 160, 160));

        jLabelJugador2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/entes/Blue_32x32/Blue.png"))); // NOI18N
        jLabelJugador2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(jLabelJugador2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 440, 160, 160));

        jButtonAnterior.setFont(Ventana.font);
        jButtonAnterior.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Prev.png"))); // NOI18N
        jButtonAnterior.setBorderPainted(false);
        jButtonAnterior.setContentAreaFilled(false);
        jButtonAnterior.setFocusable(false);
        jButtonAnterior.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAnterior.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Prev-Press.png"))); // NOI18N
        jButtonAnterior.setRequestFocusEnabled(false);
        jButtonAnterior.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Prev-Rollover.png"))); // NOI18N
        jButtonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnteriorActionPerformed(evt);
            }
        });
        add(jButtonAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 540, -1, -1));

        jButtonSiguiente.setFont(Ventana.font);
        jButtonSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Next.png"))); // NOI18N
        jButtonSiguiente.setBorderPainted(false);
        jButtonSiguiente.setContentAreaFilled(false);
        jButtonSiguiente.setFocusable(false);
        jButtonSiguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSiguiente.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Next-Press.png"))); // NOI18N
        jButtonSiguiente.setRequestFocusEnabled(false);
        jButtonSiguiente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Next-Rollover.png"))); // NOI18N
        jButtonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSiguienteActionPerformed(evt);
            }
        });
        add(jButtonSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 540, -1, -1));

        jButtonJugador1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2.png"))); // NOI18N
        jButtonJugador1.setBorderPainted(false);
        jButtonJugador1.setContentAreaFilled(false);
        jButtonJugador1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonJugador1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButtonJugador1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Press.png"))); // NOI18N
        jButtonJugador1.setRequestFocusEnabled(false);
        jButtonJugador1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Rollover.png"))); // NOI18N
        add(jButtonJugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 530, -1, -1));

        jButtonJugador2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2.png"))); // NOI18N
        jButtonJugador2.setBorderPainted(false);
        jButtonJugador2.setContentAreaFilled(false);
        jButtonJugador2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonJugador2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButtonJugador2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Press.png"))); // NOI18N
        jButtonJugador2.setRequestFocusEnabled(false);
        jButtonJugador2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Rollover.png"))); // NOI18N
        add(jButtonJugador2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 530, -1, -1));

        jButtonJugador3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2.png"))); // NOI18N
        jButtonJugador3.setBorderPainted(false);
        jButtonJugador3.setContentAreaFilled(false);
        jButtonJugador3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButtonJugador3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Press.png"))); // NOI18N
        jButtonJugador3.setRequestFocusEnabled(false);
        jButtonJugador3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Rollover.png"))); // NOI18N
        add(jButtonJugador3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 530, -1, 80));

        jButtonJugador4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2.png"))); // NOI18N
        jButtonJugador4.setBorderPainted(false);
        jButtonJugador4.setContentAreaFilled(false);
        jButtonJugador4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButtonJugador4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Press.png"))); // NOI18N
        jButtonJugador4.setRequestFocusEnabled(false);
        jButtonJugador4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-2-Rollover.png"))); // NOI18N
        add(jButtonJugador4, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 530, -1, -1));

        jButtonSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel.png"))); // NOI18N
        jButtonSalir.setBorderPainted(false);
        jButtonSalir.setContentAreaFilled(false);
        jButtonSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSalir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel-Press.png"))); // NOI18N
        jButtonSalir.setRequestFocusEnabled(false);
        jButtonSalir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-Cancel-Rollover.png"))); // NOI18N
        add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, -1, -1));

        jLabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-ResidentUDO.png"))); // NOI18N
        jLabelTitulo.setToolTipText("");
        add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, -1));

        jLabelJugador.setFont(Ventana.font);
        jLabelJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelJugador.setText("Seleccionar Jugador");
        add(jLabelJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, 650, -1));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Fondo-Opaco.png"))); // NOI18N
        jLabelFondo.setText("jLabel5");
        add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSiguienteActionPerformed
        // TODO add your handling code here:
        personajes.girarNext();
        actualizarPersonajes();
    }//GEN-LAST:event_jButtonSiguienteActionPerformed

    private void jButtonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnteriorActionPerformed
        // TODO add your handling code here:
        personajes.girarPrevious();
        actualizarPersonajes();
    }//GEN-LAST:event_jButtonAnteriorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAnterior;
    private javax.swing.JButton jButtonJugador1;
    private javax.swing.JButton jButtonJugador2;
    private javax.swing.JButton jButtonJugador3;
    private javax.swing.JButton jButtonJugador4;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSiguiente;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelJugador;
    private javax.swing.JLabel jLabelJugador1;
    private javax.swing.JLabel jLabelJugador2;
    private javax.swing.JLabel jLabelJugador3;
    private javax.swing.JLabel jLabelJugador4;
    private javax.swing.JLabel jLabelTitulo;
    // End of variables declaration//GEN-END:variables

}
