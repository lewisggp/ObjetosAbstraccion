/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Vista.Estado;

import Juego.Controlador.Controlador;
import Juego.Modelo.Estructura.NodoDiccionario;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Arma;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Municion;
import Juego.Modelo.Objeto;
import Juego.Vista.Ventana;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 *
 * @author Lewis
 */
public class Inventario extends javax.swing.JPanel {

    private Ventana ventana;

    private Consumible consumibleSeleccionado;
    private Arma armaSeleccionada;
    private Municion municionSeleccionado;
    
    private Objeto objetoClickeado;

    private JButton pausa;
    
    private boolean actualizarInventario;

    /**
     * Creates new form Inventario
     */
    public Inventario(final Ventana ventana) {
        initComponents();
        setBounds(0, 0, ventana.getWidth(), ventana.getHeight());
        this.ventana = ventana;
      
        pausa = new JButton("Pausa");
        pausa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setContentPane("Pausa");
            }
        });
        
        jTabbedPane.addMouseListener(ventana);
        jPanelObjetosLista.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                objetoClickeado = null;
            }
        });
        jPanelArmasLista.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                objetoClickeado = null;
            }
        });
        jPanelMunicionLista.addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                objetoClickeado = null;
            }
        });
        actualizarInventario = true;
    }
        
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (actualizarInventario) {
            actualizarInventario();
        }
        if (objetoClickeado != null && ventana.getMousePosition() != null) {
            g.drawImage(objetoClickeado.getSprite().getImagen().getScaledInstance(32, 32, Image.SCALE_DEFAULT), ventana.getMousePosition().x, ventana.getMousePosition().y, null);
        }
    }
    
    public void setActualizarInventario(boolean b) {
        actualizarInventario = b;
    }
    
    public boolean isObjetoClickeado(){
        return objetoClickeado != null;
    }
    
    public void toogleObjetoClickeado(char tecla) {
        if(tecla == ' ') return;
        if(objetoClickeado == null) return;
        atajoSeleccionado(tecla + "");
    }
        
    private void atajoSeleccionado(String tecla) {
        if(objetoClickeado == null) return;
        ventana.getControlador().registrarAtajo(tecla, objetoClickeado);
        objetoClickeado = null;
        actualizarInventario = true;
    }
    
    public void actualizarInventario() {
        
        // CONSUMIBLES
        
        jPanelObjetosLista.removeAll();

        NodoListaSimple nodoConsumible = ventana.getControlador().getInventario().getConsumibles().getFirstNodo();
        while (nodoConsumible != null) {
            final Consumible consumibleTemp = (Consumible) nodoConsumible.getObject();
            final JButton btnTemp = new JButton();
            btnTemp.setFocusable(false);
            btnTemp.setIcon(new ImageIcon(consumibleTemp.getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            btnTemp.setPreferredSize(new Dimension(64, 64));
            btnTemp.setRolloverEnabled(false);
            
            btnTemp.setFocusable(false);
            btnTemp.setBorder(null);
            btnTemp.setBorderPainted(false);
            btnTemp.setContentAreaFilled(false);
            btnTemp.setRequestFocusEnabled(false);
            btnTemp.addMouseListener(ventana);
            
            btnTemp.addMouseListener( new MouseAdapter() {
                public void mousePressed( MouseEvent e ) {
                    consumibleSeleccionado = consumibleTemp;
                    actualizarInventario = true;
                    jLabelObjetoImagen.setText(null);
                    objetoClickeado = consumibleSeleccionado;
                }
            });
            jPanelObjetosLista.add(btnTemp);
            nodoConsumible = nodoConsumible.getNext();
        }

        if (consumibleSeleccionado != null) {
            jLabelObjetoImagen.setIcon(new ImageIcon(consumibleSeleccionado.getSprite().getImagen().getScaledInstance(128, 128, Image.SCALE_SMOOTH)));
            jLabelObjetoNombre.setText(consumibleSeleccionado.getNombre());
            jTextAreaDescripcionObjeto.setText(consumibleSeleccionado.getDescripcion());
            
            jLabelObjetoVida.setText("+" + consumibleSeleccionado.getAumentaVida() + " <3");
            jLabelObjetoCantidad.setText("Cantidad: " + consumibleSeleccionado.getCantidad());
        }

        // ARMAS
        
        jPanelArmasLista.removeAll();
        NodoListaSimple nodoArma = ventana.getControlador().getInventario().getArmas().getFirstNodo();
        while (nodoArma != null) {
            final Arma armaTemp = (Arma) nodoArma.getObject();
            final JButton btnTemp = new JButton();
            btnTemp.setFocusable(true);
            btnTemp.setIcon(new ImageIcon(armaTemp.getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            btnTemp.setPreferredSize(new Dimension(64, 64));
            btnTemp.setRolloverEnabled(false);
            
            btnTemp.setFocusable(false);
            btnTemp.setBorder(null);
            btnTemp.setBorderPainted(false);
            btnTemp.setContentAreaFilled(false);
            btnTemp.setRequestFocusEnabled(false);
            btnTemp.addMouseListener(ventana);
            
            btnTemp.addMouseListener( new MouseAdapter() {
                public void mousePressed( MouseEvent e ) {
                    armaSeleccionada = armaTemp;
                    actualizarInventario = true;
                    jLabelArmaImagen.setText(null);
                    objetoClickeado = armaSeleccionada;
                }
            });
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
        }
        
        // MUNICIONES
        
        jPanelMunicionLista.removeAll();
        NodoListaSimple nodoMuniciones = ventana.getControlador().getInventario().getMuniciones().getFirstNodo();
        while (nodoMuniciones != null) {
            final Municion municionTemp = (Municion) nodoMuniciones.getObject();
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
            btnTemp.addMouseListener(ventana);
            
            btnTemp.addMouseListener( new MouseAdapter() {
                public void mousePressed( MouseEvent e ) {
                    municionSeleccionado = municionTemp;
                    actualizarInventario = true;
                    jLabelMunicionImagen.setText(null);
                    objetoClickeado = municionSeleccionado;
                }
            });
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
        
        jLabel1.setText("1");
        jLabel1.setIcon(null);
        jLabel2.setText("2");
        jLabel2.setIcon(null);
        jLabel3.setText("3");
        jLabel3.setIcon(null);
        jLabel4.setText("4");
        jLabel4.setIcon(null);
        jLabel5.setText("5");
        jLabel5.setIcon(null);
        jLabel6.setText("6");
        jLabel6.setIcon(null);
        jLabel7.setText("7");
        jLabel7.setIcon(null);
        jLabel8.setText("8");
        jLabel8.setIcon(null);
        jLabel9.setText("9");
        jLabel9.setIcon(null);
        jLabel0.setText("0");
        jLabel0.setIcon(null);
        NodoDiccionario nodoAtajo = ventana.getControlador().getAtajos().getFirstNodo();
        while(nodoAtajo != null) {
            switch(nodoAtajo.getClave().toString()) {
                case "1":
                    jLabel1.setText(null);
                    jLabel1.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "2":
                    jLabel2.setText(null);
                    jLabel2.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "3":
                    jLabel3.setText(null);
                    jLabel3.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "4":
                    jLabel4.setText(null);
                    jLabel4.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "5":
                    jLabel5.setText(null);
                    jLabel5.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "6":
                    jLabel6.setText(null);
                    jLabel6.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "7":
                    jLabel7.setText(null);
                    jLabel7.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "8":
                    jLabel8.setText(null);
                    jLabel8.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "9":
                    jLabel9.setText(null);
                    jLabel9.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
                case "0":
                    jLabel0.setText(null);
                    jLabel0.setIcon(new ImageIcon(((Objeto) nodoAtajo.getValor()).getSprite().getImagen().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                    break;
            }
            nodoAtajo = nodoAtajo.getNext();
        }
         
        actualizarInventario = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelAtajos = new javax.swing.JLabel();
        jPanelAtajos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel0 = new javax.swing.JLabel();
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
        jButtonObjetoConsumir = new javax.swing.JButton();
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
        jButtonArmaPortar = new javax.swing.JButton();
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
        jButtonMunicionRecargar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setFocusable(false);
        setRequestFocusEnabled(false);
        setLayout(null);

        jLabelAtajos.setFont(Ventana.font);
        jLabelAtajos.setForeground(new java.awt.Color(0, 0, 0));
        jLabelAtajos.setText("Atajos");
        add(jLabelAtajos);
        jLabelAtajos.setBounds(30, 560, 410, 40);

        jPanelAtajos.setFocusable(false);
        jPanelAtajos.setOpaque(false);
        jPanelAtajos.setRequestFocusEnabled(false);

        jLabel1.setFont(Ventana.font);
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("1");
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });

        jLabel2.setFont(Ventana.font);
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("2");
        jLabel2.setRequestFocusEnabled(false);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });

        jLabel3.setFont(Ventana.font);
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("3");
        jLabel3.setRequestFocusEnabled(false);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });

        jLabel4.setFont(Ventana.font);
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("4");
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
        });

        jLabel5.setFont(Ventana.font);
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("5");
        jLabel5.setRequestFocusEnabled(false);
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });

        jLabel6.setFont(Ventana.font);
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("6");
        jLabel6.setRequestFocusEnabled(false);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
            }
        });

        jLabel7.setFont(Ventana.font);
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("7");
        jLabel7.setRequestFocusEnabled(false);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });

        jLabel8.setFont(Ventana.font);
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("8");
        jLabel8.setRequestFocusEnabled(false);
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
            }
        });

        jLabel9.setFont(Ventana.font);
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("9");
        jLabel9.setRequestFocusEnabled(false);
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });

        jLabel0.setFont(Ventana.font);
        jLabel0.setForeground(new java.awt.Color(0, 0, 0));
        jLabel0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel0.setText("0");
        jLabel0.setRequestFocusEnabled(false);
        jLabel0.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jLabel0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel0MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelAtajosLayout = new javax.swing.GroupLayout(jPanelAtajos);
        jPanelAtajos.setLayout(jPanelAtajosLayout);
        jPanelAtajosLayout.setHorizontalGroup(
            jPanelAtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAtajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanelAtajosLayout.setVerticalGroup(
            jPanelAtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAtajosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanelAtajos);
        jPanelAtajos.setBounds(30, 590, 850, 100);

        jTabbedPane.setBackground(new java.awt.Color(255, 153, 51));
        jTabbedPane.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setFocusable(false);
        jTabbedPane.setFont(Ventana.font);
        jTabbedPane.setRequestFocusEnabled(false);

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
        jPanelObjeto.add(jLabelObjetoVida, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 310, 40));

        jLabelObjetoCantidad.setFont(Ventana.font);
        jLabelObjetoCantidad.setText("Cantidad");
        jPanelObjeto.add(jLabelObjetoCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 310, 40));

        jButtonObjetoConsumir.setFont(Ventana.font);
        jButtonObjetoConsumir.setForeground(new java.awt.Color(255, 255, 255));
        jButtonObjetoConsumir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonObjetoConsumir.setText("Consumir");
        jButtonObjetoConsumir.setBorderPainted(false);
        jButtonObjetoConsumir.setContentAreaFilled(false);
        jButtonObjetoConsumir.setFocusable(false);
        jButtonObjetoConsumir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonObjetoConsumir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonObjetoConsumir.setRequestFocusEnabled(false);
        jButtonObjetoConsumir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonObjetoConsumir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObjetoConsumirActionPerformed(evt);
            }
        });
        jPanelObjeto.add(jButtonObjetoConsumir, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

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

        jPanelObjeto.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 310, 120));

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
                    .addComponent(jPanelObjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jPanelArma.add(jLabelArmaAtaque, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 310, 40));

        jLabelArmaAlcance.setFont(Ventana.font);
        jLabelArmaAlcance.setText("Alcance");
        jPanelArma.add(jLabelArmaAlcance, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 310, 40));

        jLabelArmaPenetrante.setFont(Ventana.font);
        jLabelArmaPenetrante.setText("Penetrante");
        jPanelArma.add(jLabelArmaPenetrante, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 310, 40));

        jButtonArmaPortar.setFont(Ventana.font);
        jButtonArmaPortar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonArmaPortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonArmaPortar.setText("Portar");
        jButtonArmaPortar.setBorderPainted(false);
        jButtonArmaPortar.setContentAreaFilled(false);
        jButtonArmaPortar.setFocusable(false);
        jButtonArmaPortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonArmaPortar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonArmaPortar.setRequestFocusEnabled(false);
        jButtonArmaPortar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonArmaPortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArmaPortarActionPerformed(evt);
            }
        });
        jPanelArma.add(jButtonArmaPortar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

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

        jPanelArma.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 310, 120));

        javax.swing.GroupLayout jPanelArmasLayout = new javax.swing.GroupLayout(jPanelArmas);
        jPanelArmas.setLayout(jPanelArmasLayout);
        jPanelArmasLayout.setHorizontalGroup(
            jPanelArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelArmasLayout.createSequentialGroup()
                .addComponent(jScrollPaneArmas, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelArma, javax.swing.GroupLayout.PREFERRED_SIZE, 379, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanelArmasLayout.setVerticalGroup(
            jPanelArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneArmas)
            .addComponent(jPanelArma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jButtonMunicionRecargar.setFont(Ventana.font);
        jButtonMunicionRecargar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMunicionRecargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1.png"))); // NOI18N
        jButtonMunicionRecargar.setText("Recargar");
        jButtonMunicionRecargar.setBorderPainted(false);
        jButtonMunicionRecargar.setContentAreaFilled(false);
        jButtonMunicionRecargar.setFocusable(false);
        jButtonMunicionRecargar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMunicionRecargar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Press.png"))); // NOI18N
        jButtonMunicionRecargar.setRequestFocusEnabled(false);
        jButtonMunicionRecargar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/estilos/Btn-1-Rollover.png"))); // NOI18N
        jButtonMunicionRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMunicionRecargarActionPerformed(evt);
            }
        });
        jPanelMunicion.add(jButtonMunicionRecargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

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
            .addComponent(jPanelMunicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Municiones", jPanelMuniciones);

        add(jTabbedPane);
        jTabbedPane.setBounds(6, 22, 1268, 692);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonObjetoConsumirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonObjetoConsumirActionPerformed
        // TODO add your handling code here:
        if (consumibleSeleccionado == null) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona un objeto");
            return;
        }
        
        int resultado = ventana.getControlador().consumir(consumibleSeleccionado, 1);
        switch(resultado) {
            case Controlador.OBJETO_CONSUMIDO:
                //Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Consumido existosamente");
                break;
                
            case Controlador.CONSUMIBLE_NO_ENCONTRADO:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Consumible no encontrado");
                break;
                
            case Controlador.CONSUMIBLE_VACIO:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Este consumible esta vacio");
                break;
                
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al consumir");
                break;
        }
        
        if (consumibleSeleccionado.getCantidad() == 0) {
            consumibleSeleccionado = null;
            jLabelObjetoImagen.setIcon(null);
            jLabelObjetoImagen.setText("Imagen");
            jLabelObjetoNombre.setText("Nombre");
            jTextAreaDescripcionObjeto.setText("Descripcion");
            jLabelObjetoVida.setText("Vida");
            jLabelObjetoCantidad.setText("Cantidad");
        }
        actualizarInventario = true;
        objetoClickeado = null;
    }//GEN-LAST:event_jButtonObjetoConsumirActionPerformed

    private void jButtonArmaPortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArmaPortarActionPerformed
        // TODO add your handling code here:
        if (armaSeleccionada == null) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona un arma");
            return;
        }
        
        int resultado = ventana.getControlador().seleccionarArma(armaSeleccionada);
        switch(resultado) {
            case Controlador.ARMA_CAMBIADA:
                //Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Portado existosamente");
                break;
                
            case Controlador.ARMA_YA_PORTADA:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Ya la estas portando");
                break;
                
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al portar");
                break;
        }
        
        actualizarInventario = true;
        objetoClickeado = null;
    }//GEN-LAST:event_jButtonArmaPortarActionPerformed

    private void jButtonMunicionRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMunicionRecargarActionPerformed
        // TODO add your handling code here:
        if( municionSeleccionado == null ) {
            Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Selecciona una municion");
            return;
        }
        
        int resultado = ventana.getControlador().recargar(municionSeleccionado);
        
        switch(resultado) {
            case Controlador.ARMA_FULL_MUNICION:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Arma llena de municiones");
                break;
                
            case Controlador.MUNICION_INVALIDA:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Municion distinta a tu arma");
                break;
                
            case Controlador.MUNICION_VACIA:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Esta municion esta vacia. (?)"); // No deberia pasar porque se elimina
                break;
                
            case Controlador.RECARGA_EXISTOSA:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Recarga existosa");
                break;
                
            case Controlador.RECARGA_FALLIDA:
            default:
                Opcion.showMessage((javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), true, "Error al recargar");
                break;
        }
        
        if (municionSeleccionado.getBalas() == 0) {
            municionSeleccionado = null;
            jLabelMunicionImagen.setIcon(null);
            jLabelMunicionImagen.setText("Imagen");
            jLabelMunicionNombre.setText("Municion");
            jLabelMunicionTipo.setText("Arma");
            jLabelMunicionBalas.setText("Balas");
        }
        
        actualizarInventario = true;
        objetoClickeado = null;
    }//GEN-LAST:event_jButtonMunicionRecargarActionPerformed
    
    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("1");
    }//GEN-LAST:event_jLabel1MouseReleased

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("2");
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("3");
    }//GEN-LAST:event_jLabel3MouseReleased

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("4");
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("5");
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("6");
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("7");
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("8");
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("9");
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabel0MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseReleased
        // TODO add your handling code here:
        atajoSeleccionado("0");
    }//GEN-LAST:event_jLabel0MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonArmaPortar;
    private javax.swing.JButton jButtonMunicionRecargar;
    private javax.swing.JButton jButtonObjetoConsumir;
    private javax.swing.JLabel jLabel0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelArmaAlcance;
    private javax.swing.JLabel jLabelArmaAtaque;
    private javax.swing.JLabel jLabelArmaImagen;
    private javax.swing.JLabel jLabelArmaNombre;
    private javax.swing.JLabel jLabelArmaPenetrante;
    private javax.swing.JLabel jLabelAtajos;
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
    private javax.swing.JPanel jPanelAtajos;
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
