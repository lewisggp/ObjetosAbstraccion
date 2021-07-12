/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Vista.Estado;

import Juego.Controlador.Controlador;
import Juego.Modelo.Ente.Vendedor;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoDiccionario;
import Juego.Modelo.Modificador.Arma;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Municion;
import Juego.Vista.Ventana;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author Lewis
 */
public class Tienda extends javax.swing.JPanel {

    private Ventana ventana;

    private Vendedor vendedor;
    private Consumible objetoSeleccionado;
    private Arma armaSeleccionada;
    private Municion municionSeleccionado;

    private JButton pausa;

    private boolean actualizarTienda;

    /**
     * Creates new form Inventario
     */
    public Tienda(final Ventana ventana) {
        initComponents();
        setBounds(0, 0, ventana.getWidth(), ventana.getHeight());
        this.ventana = ventana;

        pausa = new JButton("Pausa");
        pausa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setContentPane("Pausa");
            }
        });

        actualizarTienda = true;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setActualizarTienda(boolean b) {
        actualizarTienda = b;
    }

    public void actualizarTienda() {
        vendedor = ventana.getControlador().getVendedor();

        // CONSUMIBLES
        
        jPanelObjetosLista.removeAll();
        ListaSimple consumiblesTienda = new ListaSimple();
        NodoDiccionario nodoConsumible = vendedor.getConsumibles().getFirstNodo();
        while (nodoConsumible != null) {
            final Consumible consumibleTemp = (Consumible) nodoConsumible.getClave();
            JButton btnTemp = new JButton();
            
            btnTemp.setFocusable(false);
            btnTemp.setIcon(new ImageIcon(consumibleTemp.getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            btnTemp.setPreferredSize(new Dimension(64, 64));
            btnTemp.setRolloverEnabled(false);

            btnTemp.setFocusable(false);
            btnTemp.setBorder(null);
            btnTemp.setBorderPainted(false);
            btnTemp.setContentAreaFilled(false);
            btnTemp.setRequestFocusEnabled(false);

            btnTemp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    objetoSeleccionado = consumibleTemp;
                    actualizarTienda = true;
                    jLabelObjetoImagen.setText(null);
                }
            });

            consumiblesTienda.add(btnTemp);
            jPanelObjetosLista.add(btnTemp);
            nodoConsumible = nodoConsumible.getNext();
        }

        if (objetoSeleccionado != null) {
            jLabelObjetoImagen.setIcon(new ImageIcon(objetoSeleccionado.getSprite().getImagen().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
            jLabelObjetoNombre.setText(objetoSeleccionado.getNombre());
            jTextAreaDescripcionObjeto.setText(objetoSeleccionado.getDescripcion());
            jLabelObjetoVida.setText("+" + objetoSeleccionado.getAumentaVida() + " <3");
            jLabelObjetoCantidad.setText("Cantidad: " + objetoSeleccionado.getCantidad());
            jButtonObjetoComprar.setText("Costo: " + vendedor.getPrecioConsumible(objetoSeleccionado));
        }
        
        // ARMAS

        jPanelArmasLista.removeAll();
        ListaSimple armasTienda = new ListaSimple();
        NodoDiccionario nodoArma = vendedor.getArmas().getFirstNodo();
        while (nodoArma != null) {
            final Arma armaTemp = (Arma) nodoArma.getClave();
            JButton btnTemp = new JButton();

            btnTemp.setFocusable(false);
            btnTemp.setIcon(new ImageIcon(armaTemp.getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            btnTemp.setPreferredSize(new Dimension(64, 64));
            btnTemp.setRolloverEnabled(false);

            btnTemp.setFocusable(false);
            btnTemp.setBorder(null);
            btnTemp.setBorderPainted(false);
            btnTemp.setContentAreaFilled(false);
            btnTemp.setRequestFocusEnabled(false);

            btnTemp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    armaSeleccionada = armaTemp;
                    actualizarTienda = true;
                    jLabelArmaImagen.setText(null);
                }
            });
            armasTienda.add(btnTemp);
            jPanelArmasLista.add(btnTemp);
            nodoArma = nodoArma.getNext();
        }

        if (armaSeleccionada != null) {
            jLabelArmaImagen.setIcon(new ImageIcon(armaSeleccionada.getSprite().getImagen().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
            jLabelArmaNombre.setText(armaSeleccionada.getNombre());
            jTextAreaDescripcionArma.setText(armaSeleccionada.getDescripcion());
            jLabelArmaAtaque.setText("Daño: " + armaSeleccionada.getAtaqueMinimo()
                    + "-" + armaSeleccionada.getAtaqueMaximo() + " <3");
            jLabelArmaAlcance.setText("Alcance: " + armaSeleccionada.getAlcance());
            if (armaSeleccionada.isPenetrante()) {
                jLabelArmaPenetrante.setText("Es penetrante");
            } else {
                jLabelArmaPenetrante.setText("No es penetrante");
            }
            jButtonArmaComprar.setText("Costo: " + vendedor.getPrecioArma(armaSeleccionada));
        }

        // MUNICIONES
        
        ListaSimple municionesTienda = new ListaSimple();
        jPanelMunicionLista.removeAll();
        NodoDiccionario nodoMuniciones = vendedor.getMuniciones().getFirstNodo();
        while (nodoMuniciones != null) {
            final Municion municionTemp = (Municion) nodoMuniciones.getClave();
            final JButton btnTemp = new JButton();
            btnTemp.setFocusable(true);
            btnTemp.setIcon(new ImageIcon(municionTemp.getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            btnTemp.setPreferredSize(new Dimension(64, 64));
            btnTemp.setRolloverEnabled(false);
            
            btnTemp.setFocusable(false);
            btnTemp.setBorder(null);
            btnTemp.setBorderPainted(false);
            btnTemp.setContentAreaFilled(false);
            btnTemp.setRequestFocusEnabled(false);
            
            btnTemp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    municionSeleccionado = municionTemp;
                    actualizarTienda = true;
                    jLabelMunicionImagen.setText(null);
                }
            });
            municionesTienda.add(btnTemp);
            jPanelMunicionLista.add(btnTemp);
            nodoMuniciones = nodoMuniciones.getNext();
        }

        
        if (municionSeleccionado != null) {
            if (municionSeleccionado.getBalas() == 0) {                
                municionSeleccionado = null;
                jLabelMunicionImagen.setIcon(null);
                jLabelMunicionImagen.setText("Imagen");
                jLabelMunicionNombre.setText("Municion");
                jLabelMunicionTipo.setText("Arma");
                jLabelMunicionBalas.setText("Balas");
            } else {
                jLabelMunicionImagen.setIcon(new ImageIcon(municionSeleccionado.getSprite().getImagen().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
                jLabelMunicionNombre.setText(municionSeleccionado.getNombre());
                jLabelMunicionTipo.setText("Arma: " + municionSeleccionado.getTipo());
                jLabelMunicionBalas.setText("Balas: " + municionSeleccionado.getBalas());
            }
        }
        
        actualizarTienda = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (actualizarTienda) {
            actualizarTienda();
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

        UIManager.put("TabbedPane.selected", new java.awt.Color(255, 102, 0));
        UIManager.put("TabbedPane.contentAreaColor", new java.awt.Color(255, 102, 0));
        UIManager.put("TabbedPane.borderHightlightColor", new Color(240,135,6)); // tab
        UIManager.put("TabbedPane.tabAreaBackground", java.awt.Color.magenta);
        UIManager.put("TabbedPane.light", new Color(255, 153, 51)); // tab
        UIManager.put("TabbedPane.selectHighlight", java.awt.Color.black);
        UIManager.put("TabbedPane.highlight", new Color(224,83,12));
        UIManager.put("TabbedPane.darkShadow", new java.awt.Color(255, 102, 0));// tab
        UIManager.put("TabbedPane.selectHighlight", new Color(249,209,174));
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelObjetos = new javax.swing.JPanel();
        jScrollPaneObjetos = new javax.swing.JScrollPane();
        jPanelObjetosLista = new javax.swing.JPanel();
        jPanelObjeto = new javax.swing.JPanel();
        jLabelObjetoImagen = new javax.swing.JLabel();
        jLabelObjetoNombre = new javax.swing.JLabel();
        jLabelObjetoVida = new javax.swing.JLabel();
        jLabelObjetoCantidad = new javax.swing.JLabel();
        jButtonObjetoComprar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescripcionObjeto = new javax.swing.JTextArea();
        jPanelArmas = new javax.swing.JPanel();
        jScrollPaneArmas = new javax.swing.JScrollPane();
        jPanelArmasLista = new javax.swing.JPanel();
        jPanelArma = new javax.swing.JPanel();
        jLabelArmaImagen = new javax.swing.JLabel();
        jLabelArmaNombre = new javax.swing.JLabel();
        jLabelArmaAtaque = new javax.swing.JLabel();
        jLabelArmaAlcance = new javax.swing.JLabel();
        jLabelArmaPenetrante = new javax.swing.JLabel();
        jButtonArmaComprar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcionArma = new javax.swing.JTextArea();
        jPanelMuniciones = new javax.swing.JPanel();
        jScrollPaneMunicion = new javax.swing.JScrollPane();
        jPanelMunicionLista = new javax.swing.JPanel();
        jPanelMunicion = new javax.swing.JPanel();
        jLabelMunicionImagen = new javax.swing.JLabel();
        jLabelMunicionNombre = new javax.swing.JLabel();
        jLabelMunicionTipo = new javax.swing.JLabel();
        jLabelMunicionBalas = new javax.swing.JLabel();
        jButtonMunicionComprar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jTabbedPane.setBackground(new java.awt.Color(255, 153, 51));
        jTabbedPane.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setFont(Ventana.font);

        jPanelObjetos.setBackground(new java.awt.Color(255, 102, 0));

        jScrollPaneObjetos.setBorder(null);
        jScrollPaneObjetos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelObjetosLista.setBackground(new java.awt.Color(255, 247, 231));
        jPanelObjetosLista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPaneObjetos.setViewportView(jPanelObjetosLista);

        jPanelObjeto.setBackground(new java.awt.Color(255, 255, 255));
        jPanelObjeto.setForeground(new java.awt.Color(255, 255, 255));
        jPanelObjeto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelObjetoImagen.setFont(Ventana.font);
        jLabelObjetoImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelObjetoImagen.setText("Imagen");
        jPanelObjeto.add(jLabelObjetoImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 150, 150));

        jLabelObjetoNombre.setBackground(new java.awt.Color(255, 102, 0));
        jLabelObjetoNombre.setFont(Ventana.font);
        jLabelObjetoNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelObjetoNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelObjetoNombre.setText("Nombre");
        jLabelObjetoNombre.setOpaque(true);
        jPanelObjeto.add(jLabelObjetoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 380, 50));

        jLabelObjetoVida.setFont(Ventana.font);
        jLabelObjetoVida.setText("Vida");
        jPanelObjeto.add(jLabelObjetoVida, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 320, 40));

        jLabelObjetoCantidad.setFont(Ventana.font);
        jLabelObjetoCantidad.setText("Cantidad");
        jPanelObjeto.add(jLabelObjetoCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 320, 40));

        jButtonObjetoComprar.setFont(Ventana.font);
        jButtonObjetoComprar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonObjetoComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonObjetoComprar.setText("Consumir");
        jButtonObjetoComprar.setBorderPainted(false);
        jButtonObjetoComprar.setContentAreaFilled(false);
        jButtonObjetoComprar.setFocusable(false);
        jButtonObjetoComprar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonObjetoComprar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonObjetoComprar.setRequestFocusEnabled(false);
        jButtonObjetoComprar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonObjetoComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObjetoComprarActionPerformed(evt);
            }
        });
        jPanelObjeto.add(jButtonObjetoComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextAreaDescripcionObjeto.setEditable(false);
        jTextAreaDescripcionObjeto.setBackground(new java.awt.Color(255, 255, 255));
        jTextAreaDescripcionObjeto.setColumns(20);
        jTextAreaDescripcionObjeto.setFont(Ventana.font);
        jTextAreaDescripcionObjeto.setLineWrap(true);
        jTextAreaDescripcionObjeto.setRows(5);
        jTextAreaDescripcionObjeto.setText("Descripcion");
        jTextAreaDescripcionObjeto.setWrapStyleWord(true);
        jTextAreaDescripcionObjeto.setBorder(null);
        jTextAreaDescripcionObjeto.setFocusable(false);
        jTextAreaDescripcionObjeto.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(jTextAreaDescripcionObjeto);

        jPanelObjeto.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 320, 120));

        javax.swing.GroupLayout jPanelObjetosLayout = new javax.swing.GroupLayout(jPanelObjetos);
        jPanelObjetos.setLayout(jPanelObjetosLayout);
        jPanelObjetosLayout.setHorizontalGroup(
            jPanelObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelObjetosLayout.createSequentialGroup()
                .addComponent(jScrollPaneObjetos, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelObjetosLayout.setVerticalGroup(
            jPanelObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelObjetosLayout.createSequentialGroup()
                .addGroup(jPanelObjetosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addComponent(jScrollPaneObjetos))
                .addGap(0, 0, 0))
        );

        jTabbedPane.addTab("Objetos", jPanelObjetos);

        jScrollPaneArmas.setBorder(null);
        jScrollPaneArmas.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelArmasLista.setBackground(new java.awt.Color(255, 247, 231));
        jScrollPaneArmas.setViewportView(jPanelArmasLista);

        jPanelArma.setBackground(new java.awt.Color(255, 255, 255));
        jPanelArma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelArmaImagen.setFont(Ventana.font);
        jLabelArmaImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelArmaImagen.setText("Imagen");
        jPanelArma.add(jLabelArmaImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 150, 150));

        jLabelArmaNombre.setBackground(new java.awt.Color(255, 102, 0));
        jLabelArmaNombre.setFont(Ventana.font);
        jLabelArmaNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelArmaNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelArmaNombre.setText("Nombre");
        jLabelArmaNombre.setOpaque(true);
        jPanelArma.add(jLabelArmaNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 380, 50));

        jLabelArmaAtaque.setFont(Ventana.font);
        jLabelArmaAtaque.setText("Ataque");
        jPanelArma.add(jLabelArmaAtaque, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 320, 40));

        jLabelArmaAlcance.setFont(Ventana.font);
        jLabelArmaAlcance.setText("Alcance");
        jPanelArma.add(jLabelArmaAlcance, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 320, 40));

        jLabelArmaPenetrante.setFont(Ventana.font);
        jLabelArmaPenetrante.setText("Penetrante");
        jPanelArma.add(jLabelArmaPenetrante, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 320, 40));

        jButtonArmaComprar.setFont(Ventana.font);
        jButtonArmaComprar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonArmaComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonArmaComprar.setText("Portar");
        jButtonArmaComprar.setBorderPainted(false);
        jButtonArmaComprar.setContentAreaFilled(false);
        jButtonArmaComprar.setFocusable(false);
        jButtonArmaComprar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonArmaComprar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonArmaComprar.setRequestFocusEnabled(false);
        jButtonArmaComprar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonArmaComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArmaComprarActionPerformed(evt);
            }
        });
        jPanelArma.add(jButtonArmaComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextAreaDescripcionArma.setEditable(false);
        jTextAreaDescripcionArma.setBackground(new java.awt.Color(255, 255, 255));
        jTextAreaDescripcionArma.setColumns(20);
        jTextAreaDescripcionArma.setFont(Ventana.font);
        jTextAreaDescripcionArma.setLineWrap(true);
        jTextAreaDescripcionArma.setRows(5);
        jTextAreaDescripcionArma.setText("Descripcion");
        jTextAreaDescripcionArma.setWrapStyleWord(true);
        jTextAreaDescripcionArma.setBorder(null);
        jTextAreaDescripcionArma.setFocusable(false);
        jScrollPane2.setViewportView(jTextAreaDescripcionArma);

        jPanelArma.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 320, 120));

        javax.swing.GroupLayout jPanelArmasLayout = new javax.swing.GroupLayout(jPanelArmas);
        jPanelArmas.setLayout(jPanelArmasLayout);
        jPanelArmasLayout.setHorizontalGroup(
            jPanelArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelArmasLayout.createSequentialGroup()
                .addComponent(jScrollPaneArmas, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanelArmasLayout.setVerticalGroup(
            jPanelArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneArmas)
            .addComponent(jPanelArma, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Armas", jPanelArmas);

        jScrollPaneMunicion.setBorder(null);
        jScrollPaneMunicion.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelMunicionLista.setBackground(new java.awt.Color(255, 247, 231));
        jScrollPaneMunicion.setViewportView(jPanelMunicionLista);

        jPanelMunicion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMunicion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMunicionImagen.setFont(Ventana.font);
        jLabelMunicionImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMunicionImagen.setText("Imagen");
        jPanelMunicion.add(jLabelMunicionImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 150, 150));

        jLabelMunicionNombre.setBackground(new java.awt.Color(255, 102, 0));
        jLabelMunicionNombre.setFont(Ventana.font);
        jLabelMunicionNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMunicionNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMunicionNombre.setText("Municion");
        jLabelMunicionNombre.setOpaque(true);
        jPanelMunicion.add(jLabelMunicionNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 380, 50));

        jLabelMunicionTipo.setFont(Ventana.font);
        jLabelMunicionTipo.setText("Arma");
        jPanelMunicion.add(jLabelMunicionTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 310, 40));

        jLabelMunicionBalas.setFont(Ventana.font);
        jLabelMunicionBalas.setText("Balas");
        jPanelMunicion.add(jLabelMunicionBalas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 310, 40));

        jButtonMunicionComprar.setFont(Ventana.font);
        jButtonMunicionComprar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMunicionComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonMunicionComprar.setText("Recargar");
        jButtonMunicionComprar.setBorderPainted(false);
        jButtonMunicionComprar.setContentAreaFilled(false);
        jButtonMunicionComprar.setFocusable(false);
        jButtonMunicionComprar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMunicionComprar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonMunicionComprar.setRequestFocusEnabled(false);
        jButtonMunicionComprar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonMunicionComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMunicionComprarActionPerformed(evt);
            }
        });
        jPanelMunicion.add(jButtonMunicionComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

        javax.swing.GroupLayout jPanelMunicionesLayout = new javax.swing.GroupLayout(jPanelMuniciones);
        jPanelMuniciones.setLayout(jPanelMunicionesLayout);
        jPanelMunicionesLayout.setHorizontalGroup(
            jPanelMunicionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMunicionesLayout.createSequentialGroup()
                .addComponent(jScrollPaneMunicion, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelMunicion, javax.swing.GroupLayout.PREFERRED_SIZE, 379, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanelMunicionesLayout.setVerticalGroup(
            jPanelMunicionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneMunicion)
            .addComponent(jPanelMunicion, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Municiones", jPanelMuniciones);

        add(jTabbedPane);
        jTabbedPane.setBounds(6, 22, 1268, 692);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonObjetoComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObjetoComprarActionPerformed
        // TODO add your handling code here:
        if (objetoSeleccionado == null) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona un objeto");
            return;
        }        
        if (vendedor.getPrecioConsumible(objetoSeleccionado) > ventana.getControlador().getJugador().getPuntos()) {        
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Puntos insuficientes");
            return;
        }
        
        int resultado = ventana.getControlador().comprarConsumible(vendedor, objetoSeleccionado);
        switch(resultado) {
            case Controlador.COMPRA_EXITOSA_ULTIMA:
                objetoSeleccionado = null;
                jLabelObjetoImagen.setIcon(null);
                jLabelObjetoImagen.setText("Imagen");
                jLabelObjetoNombre.setText("Nombre");
                jTextAreaDescripcionObjeto.setText("Descripcion");
                jLabelObjetoVida.setText("Vida");
                jLabelObjetoCantidad.setText("Cantidad");
                jButtonObjetoComprar.setText("Comprar");
            case Controlador.COMPRA_EXITOSA:
                //Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Compra exitosa");
                break;
                
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al comprar");
                break;
        }
        
        actualizarTienda = true;
    }//GEN-LAST:event_jButtonObjetoComprarActionPerformed

    private void jButtonArmaComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArmaComprarActionPerformed
        // TODO add your handling code here:
        if (armaSeleccionada == null) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona un arma");
            return;
        }
        if (vendedor.getPrecioArma(armaSeleccionada) > ventana.getControlador().getJugador().getPuntos()) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Puntos insuficientes");
            return;
        }
        
        int resultado = ventana.getControlador().comprarArma(vendedor, armaSeleccionada);
        switch(resultado) {
            case Controlador.COMPRA_EXITOSA:
                //Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Compra exitosa");
                break;
                
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al comprar");
                break;
        }
        
        /* // armaSeleccionada = null;
        jLabelArmaImagen.setIcon(null);
        jLabelArmaImagen.setText("Imagen");
        jLabelArmaNombre.setText("Nombre");
        jTextAreaDescripcionArma.setText("Descripcion");
        jLabelArmaAtaque.setText("Daño: " + armaSeleccionada.getAtaqueMinimo()
                + "-" + armaSeleccionada.getAtaqueMaximo() + " <3");
        jLabelArmaAlcance.setText("Alcance: " + armaSeleccionada.getAlcance());*/
        
        jButtonArmaComprar.setText("Comprar");
        actualizarTienda = true;
    }//GEN-LAST:event_jButtonArmaComprarActionPerformed

    private void jButtonMunicionComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMunicionComprarActionPerformed
        // TODO add your handling code here:
        if (municionSeleccionado == null) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona una municion");
            return;
        }
        if (vendedor.getPrecioMunicion(municionSeleccionado) > ventana.getControlador().getJugador().getPuntos()) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Puntos insuficientes");
            return;
        }
                
        int resultado = ventana.getControlador().comprarMunicion(vendedor, municionSeleccionado);
        switch(resultado) {
            case Controlador.COMPRA_EXITOSA:
                //Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Compra exitosa");
                break;
                
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al comprar");
                break;
        }
        
        municionSeleccionado = null;
        jLabelMunicionImagen.setIcon(null);
        jLabelMunicionImagen.setText("Imagen");
        jLabelMunicionNombre.setText("Municion");
        jLabelMunicionTipo.setText("Arma");
        jLabelMunicionBalas.setText("Balas");        
        
        jButtonMunicionComprar.setText("Comprar");
        actualizarTienda = true;
    }//GEN-LAST:event_jButtonMunicionComprarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonArmaComprar;
    private javax.swing.JButton jButtonMunicionComprar;
    private javax.swing.JButton jButtonObjetoComprar;
    private javax.swing.JLabel jLabelArmaAlcance;
    private javax.swing.JLabel jLabelArmaAtaque;
    private javax.swing.JLabel jLabelArmaImagen;
    private javax.swing.JLabel jLabelArmaNombre;
    private javax.swing.JLabel jLabelArmaPenetrante;
    private javax.swing.JLabel jLabelMunicionBalas;
    private javax.swing.JLabel jLabelMunicionImagen;
    private javax.swing.JLabel jLabelMunicionNombre;
    private javax.swing.JLabel jLabelMunicionTipo;
    private javax.swing.JLabel jLabelObjetoCantidad;
    private javax.swing.JLabel jLabelObjetoImagen;
    private javax.swing.JLabel jLabelObjetoNombre;
    private javax.swing.JLabel jLabelObjetoVida;
    private javax.swing.JPanel jPanelArma;
    private javax.swing.JPanel jPanelArmas;
    private javax.swing.JPanel jPanelArmasLista;
    private javax.swing.JPanel jPanelMunicion;
    private javax.swing.JPanel jPanelMunicionLista;
    private javax.swing.JPanel jPanelMuniciones;
    private javax.swing.JPanel jPanelObjeto;
    private javax.swing.JPanel jPanelObjetos;
    private javax.swing.JPanel jPanelObjetosLista;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneArmas;
    private javax.swing.JScrollPane jScrollPaneMunicion;
    private javax.swing.JScrollPane jScrollPaneObjetos;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextArea jTextAreaDescripcionArma;
    private javax.swing.JTextArea jTextAreaDescripcionObjeto;
    // End of variables declaration//GEN-END:variables
}
