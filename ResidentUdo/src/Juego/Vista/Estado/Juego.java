package Juego.Vista.Estado;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import Juego.Controlador.Controlador;
import Juego.Controlador.Mapa;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoDijkstra;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Desarmado;
import Juego.Modelo.Objeto;
import Juego.Modelo.Teletransportador;
import Juego.Vista.Ventana;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Juego extends JPanel {

    private Controlador controlador;
    
    private final byte ladoSprite;
    private int ancho, alto;
    private float escalamiento = 2;
    private JPanel juego;
    private Inventario inventarioPanel;
    private Tienda tiendaPanel;

    private JButton pausa;
    private JButton gameOver;

    public Juego(final Ventana ventana, Controlador controlador) {
        ladoSprite = 16;
        setLayout(null);
        //setFocusable(false);
        this.ancho = (int) (ventana.getWidth() / escalamiento); // /2
        this.alto = (int) (ventana.getHeight() / escalamiento); // /2
        this.controlador = controlador;

        pausa = new JButton("Menu");
        pausa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.getControlador().getTeclado().getArriba().teclaLiberada();
                ventana.getControlador().getTeclado().getAbajo().teclaLiberada();
                ventana.getControlador().getTeclado().getDerecha().teclaLiberada();
                ventana.getControlador().getTeclado().getIzquierda().teclaLiberada();
                ventana.setContentPane("Pausa");
            }
        });

        gameOver = new JButton("GameOver");
        gameOver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setContentPane("GameOver");
            }
        });
        
        juego = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g); // Limpiar
                pintarJuego(g);
            }
        };
        
        juego.setBounds(0, 0, ventana.getWidth(), ventana.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        controlador.actualizar();
        super.paint(g); // Limpiar
        
        if (controlador.getTeclado().isInventarioActivo()) {
            remove(juego);
            remove(tiendaPanel);
            tiendaPanel.setActualizarTienda(true);
            
            add(inventarioPanel);
        } else if (controlador.getTeclado().isComprando()) {
            remove(juego);
            remove(inventarioPanel);
            inventarioPanel.setActualizarInventario(true);
            
            add(tiendaPanel);
        } else {
            remove(inventarioPanel);
            inventarioPanel.setActualizarInventario(true);
            remove(tiendaPanel);
            tiendaPanel.setActualizarTienda(true);
            
            add(juego);
        }

        // Dibujar Barras de Jugador
        int vidaJugador = controlador.getJugador().getVidaActual();
        int vidaMaxima = controlador.getJugador().getVidaMaxima();
        int resistenciaJugador = controlador.getJugador().getResistencia();
        int resistenciaMaxima = controlador.getJugador().getResistenciaMaxima();
        int puntos = controlador.getJugador().getPuntos();
        int anchoTemp = (int) (ancho * escalamiento);
        g.setColor(Color.BLACK);
        g.fillRect(4, 4, anchoTemp / 4 + 2, 8);
        g.fillRect(anchoTemp / 4 + 9, 4, anchoTemp / 4 + 2, 8);
        g.setColor(Color.RED.brighter());
        g.fillRect(5, 5, anchoTemp / 4 * vidaJugador / vidaMaxima, 3);
        g.setColor(Color.RED.darker());
        g.fillRect(5, 8, anchoTemp / 4 * vidaJugador / vidaMaxima, 3);
        g.setColor(Color.GREEN.brighter());
        g.fillRect(anchoTemp / 4 + 10, 5, anchoTemp / 4 * resistenciaJugador / resistenciaMaxima, 3);
        g.setColor(Color.GREEN.darker());
        g.fillRect(anchoTemp / 4 + 10, 8, anchoTemp / 4 * resistenciaJugador / resistenciaMaxima, 3);
        g.setColor(Color.BLACK);
        g.drawString("Puntos " + puntos, anchoTemp / 2 + 15, 13);
        if( ! ( controlador.getJugador().getArma() instanceof Desarmado ) ) {
            int balasActual = controlador.getJugador().getArma().getBalasActual();
            int balasMaximo = controlador.getJugador().getArma().getBalasMaximo();
            g.drawString("Balas " + balasActual + " / " + balasMaximo, anchoTemp * 3 / 4 + 15, 13);
        }
        
        if (controlador.gameOver() && !controlador.getJugador().isAnimacionEspecial()) {
            gameOver.doClick();
        }
    }

    public void pintarJuego(Graphics g) {
        if (escalamiento != 1) {
            final Graphics2D g2 = (Graphics2D) g;
            g2.scale(escalamiento, escalamiento);
        }
        
        // Ancho y alto del mapa en sprites
        // (No Pixeles. En PX seria multiplicado por 16)
        int spritesX = controlador.getMapa().getAncho();
        int spritesY = controlador.getMapa().getAlto();

        // Referencia del jugador para mover (Mapa, enemigos, objetos)
        // a una posicion relativa al jugador centrado en la ventana
        // Es decir, desde esta referencia se empezara a dibujar todo
        int referenciaX = (ancho - controlador.getJugador().getSprite().getAncho()) / 2 - controlador.getJugador().getPosicion().x;
        int referenciaY = (alto - controlador.getJugador().getSprite().getAlto()) / 2 - controlador.getJugador().getPosicion().y;

        int mitadMapa = 0;
        // Dibujar Mapa primera cada parte arriba
        for (int y = 0; y < spritesY && y * Mapa.LADO_SPRITE <= controlador.getJugador().getArea().y + controlador.getJugador().getArea().height; y++) {
            for (int x = 0; x < spritesX; x++) {
                mitadMapa = y;
                int id = controlador.getMapa().getIdTerreno(0, x, y);
                if(id >= 0) {
                    int puntoX = x * ladoSprite;
                    int puntoY = y * ladoSprite;
                    if (referenciaX + puntoX >= -ladoSprite && referenciaY + puntoY >= -ladoSprite && referenciaX + puntoX < ancho && referenciaY + puntoY < alto) {
                        g.drawImage(Mapa.getSprite(id).getImagen(),
                                referenciaX + puntoX,
                                referenciaY + puntoY, null);
                    }
                }                
            }
        }

        // \Dibujar segunda capa del mapa parte arriba
        for (int y = 0; y < mitadMapa; y++) {
            for (int x = 0; x < spritesX; x++) {
                int id = controlador.getMapa().getIdTerreno(1, x, y);
                if(id >= 0) {                    
                    int puntoX = x * ladoSprite;
                    int puntoY = y * ladoSprite;
                    if (referenciaX + puntoX >= -ladoSprite && referenciaY + puntoY >= -ladoSprite && referenciaX + puntoX < ancho && referenciaY + puntoY < alto) {
                        g.drawImage(Mapa.getSprite(id).getImagen(),
                                referenciaX + puntoX,
                                referenciaY + puntoY, null);
                    }
                }            
            }
        }

        ListaSimple listaObjetos = new ListaSimple();
        listaObjetos.add(controlador.getMapa().getConsumibles());
        listaObjetos.add(controlador.getMapa().getArmas());
        listaObjetos.add(controlador.getMapa().getEnemigos());
        listaObjetos.add(controlador.getMapa().getVendedores());
        listaObjetos.add(controlador.getMapa().getMuniciones());
        if(controlador.getTeclado().isDebug())
            listaObjetos.add(controlador.getMapa().getTeletransportadores());

        ListaSimple listaInferior = new ListaSimple();

        NodoListaSimple nodoTemp = listaObjetos.getFirstNodo();
        while (nodoTemp != null) {
            Objeto objetoTemp = (Objeto) nodoTemp.getObject();
            if (objetoTemp.getArea().y > controlador.getJugador().getArea().y) {
                listaInferior.add(objetoTemp);
            } else {
                if (objetoTemp.getArea().x >= 0 && objetoTemp.getArea().y >= 0 && objetoTemp.getArea().x < spritesX * ladoSprite && objetoTemp.getArea().y < spritesY * ladoSprite) {
                    g.drawImage(objetoTemp.getSprite().getImagen(),
                            referenciaX + objetoTemp.getArea().x - objetoTemp.getDxPosicion(),
                            referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion(), null);
                    if (controlador.getTeclado().isDebug()) {
                        g.setColor(Color.black);
                        g.drawRect(referenciaX + objetoTemp.getArea().x,
                                referenciaY + objetoTemp.getArea().y,
                                objetoTemp.getArea().width,
                                objetoTemp.getArea().height);
                    }
                    if(objetoTemp instanceof Ente) {
                        g.setColor(Color.black);
                        g.fillRect(referenciaX + objetoTemp.getArea().x,
                                referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                                objetoTemp.getArea().width,
                                3);
                        g.setColor(Color.green);
                        g.fillRect(referenciaX + objetoTemp.getArea().x,
                                referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                                objetoTemp.getArea().width * ((Ente) objetoTemp).getVidaActual() / ((Ente) objetoTemp).getVidaMaxima(),
                                3);
                        g.setColor(Color.black);
                        g.drawRect(referenciaX + objetoTemp.getArea().x,
                                referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                                objetoTemp.getArea().width,
                                3);
                    }
                }
            }
            nodoTemp = nodoTemp.getNext();
        }

        // Dibujar al jugador en el centro del mapa
        g.drawImage(controlador.getJugador().getSprite().getImagen(),
                referenciaX + controlador.getJugador().getArea().x - controlador.getJugador().getDxPosicion(),
                referenciaY + controlador.getJugador().getArea().y - controlador.getJugador().getDyPosicion(), null);

        // Dibujar primera capa del mapa parte arriba
        for (int y = mitadMapa + 1; y < spritesY; y++) {
            for (int x = 0; x < spritesX; x++) {
                int id = controlador.getMapa().getIdTerreno(0, x, y);
                if(id >= 0) {
                    int puntoX = x * ladoSprite;
                    int puntoY = y * ladoSprite;
                    if (referenciaX + puntoX >= -ladoSprite && referenciaY + puntoY >= -ladoSprite && referenciaX + puntoX < ancho && referenciaY + puntoY < alto) {
                        g.drawImage(Mapa.getSprite(id).getImagen(),
                                referenciaX + puntoX,
                                referenciaY + puntoY, null);
                    }
                }
            }
        }

        // Dibujar segunda capa del mapa parte arriba
        for (int y = mitadMapa; y < spritesY; y++) {
            for (int x = 0; x < spritesX; x++) {
                int id = controlador.getMapa().getIdTerreno(1, x, y);
                if(id >= 0) {
                    int puntoX = x * ladoSprite;
                    int puntoY = y * ladoSprite;
                    if (referenciaX + puntoX >= -ladoSprite && referenciaY + puntoY >= -ladoSprite && referenciaX + puntoX < ancho && referenciaY + puntoY < alto) {
                        g.drawImage(Mapa.getSprite(id).getImagen(),
                                referenciaX + puntoX,
                                referenciaY + puntoY, null);
                    }
                }
            }
        }

        // Segunda parte de objetos
        nodoTemp = listaInferior.getFirstNodo();
        while (nodoTemp != null) {
            Objeto objetoTemp = (Objeto) nodoTemp.getObject();
            if (objetoTemp.getArea().x >= 0 && objetoTemp.getArea().y >= 0 && objetoTemp.getArea().x < spritesX * ladoSprite && objetoTemp.getArea().y < spritesY * ladoSprite) {
                g.drawImage(objetoTemp.getSprite().getImagen(),
                        referenciaX + objetoTemp.getArea().x - objetoTemp.getDxPosicion(),
                        referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion(), null);
                if (controlador.getTeclado().isDebug()) {
                    g.drawRect(referenciaX + objetoTemp.getArea().x,
                            referenciaY + objetoTemp.getArea().y,
                            objetoTemp.getArea().width,
                            objetoTemp.getArea().height);
                }
                if(objetoTemp instanceof Ente) {
                    g.setColor(Color.black);
                    g.fillRect(referenciaX + objetoTemp.getArea().x,
                            referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                            objetoTemp.getArea().width,
                            3);
                    g.setColor(Color.green);
                    g.fillRect(referenciaX + objetoTemp.getArea().x,
                            referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                            objetoTemp.getArea().width * ((Ente) objetoTemp).getVidaActual() / ((Ente) objetoTemp).getVidaMaxima(),
                            3);
                    g.setColor(Color.black);
                    g.drawRect(referenciaX + objetoTemp.getArea().x,
                            referenciaY + objetoTemp.getArea().y - objetoTemp.getDyPosicion() / 2,
                            objetoTemp.getArea().width,
                            3);
                }
            }
            nodoTemp = nodoTemp.getNext();
        }

        // Debug
        if (controlador.getTeclado().isDebug()) {
            g.setColor(Color.red);
            
            //Dibujar Dijkstra
            NodoDijkstra[][] nodos = controlador.getDijkstra().getNodos();
            for (int i = 0; i < nodos.length; i++) {
                for (int j = 0; j < nodos[i].length; j++) {
                    if (nodos[i][j].isSolido()) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.green);
                    }
                    g.drawRect(referenciaX + nodos[i][j].getArea().x,
                            referenciaY + nodos[i][j].getArea().y,
                            nodos[i][j].getArea().width,
                            nodos[i][j].getArea().height);
                }
            }
            
            g.setColor(Color.red);
            
            // Dibujar TerrenoColisionable
            NodoListaSimple colision = controlador.getMapa().getTerrenoColisionable().getFirstNodo();
            while (colision != null) {
                Rectangle rectangulo = (Rectangle) colision.getObject();
                g.drawRect(referenciaX + rectangulo.x,
                        referenciaY + rectangulo.y,
                        rectangulo.width,
                        rectangulo.height);
                colision = colision.getNext();
            }
            
            // Dibujar mas Colisiones
            colision = controlador.getMapa().getColisiones().getFirstNodo();
            while (colision != null) {
                Rectangle rectangulo = (Rectangle) colision.getObject();
                g.drawRect(referenciaX + rectangulo.x,
                        referenciaY + rectangulo.y,
                        rectangulo.width,
                        rectangulo.height);
                colision = colision.getNext();
            }
            
            // Dibujar contorno jugador
            g.drawRect(referenciaX + controlador.getJugador().getPosicion().x - controlador.getJugador().getDxPosicion(),
                    referenciaY + controlador.getJugador().getPosicion().y - controlador.getJugador().getDyPosicion(),
                    controlador.getJugador().getSprite().getAncho(),
                    controlador.getJugador().getSprite().getAlto());
            
            // Area dl jugador
            g.drawRect(referenciaX + controlador.getJugador().getArea().x,
                    referenciaY + controlador.getJugador().getArea().y,
                    controlador.getJugador().getArea().width,
                    controlador.getJugador().getArea().height);
        }               

        // Dibujar Alcance del arma
        NodoListaSimple alcancesAtaques = controlador.getJugador().getArma().getAlcance(controlador.getJugador()).getFirstNodo();
        while (alcancesAtaques != null) {
            Rectangle rectangleTemp = (Rectangle) alcancesAtaques.getObject();
            g.drawRect(referenciaX + rectangleTemp.x,
                    referenciaY + rectangleTemp.y,
                    rectangleTemp.width,
                    rectangleTemp.height);
            alcancesAtaques = alcancesAtaques.getNext();
        }
        
        // Tooltip
        if (controlador.getRaton().isClickDerecho()) {
            Point posicionEnMapa = new Point(controlador.getRaton().getPosicion().x / 2 - referenciaX, 
                                            controlador.getRaton().getPosicion().y / 2 - referenciaY);
            Objeto objeto = controlador.getObjeto(posicionEnMapa);
            if(objeto != null) {
                String str = (objeto instanceof Teletransportador) ? ((Teletransportador) objeto).getMapaFinal() : objeto.getNombre();
                g.setColor(new Color(255, 102, 0));
                g.setFont( Ventana.font.deriveFont(10f) );
                g.drawString(str, referenciaX + posicionEnMapa.x, referenciaY + posicionEnMapa.y);
            }
        }
        
        // Propiedad del jugador
        boolean jugadorDebil = controlador.getJugador().getVidaActual() < controlador.getJugador().getVidaMaxima() / 2;
        if (!controlador.isDeDia() || jugadorDebil) {
            if (controlador.isDeDia() && jugadorDebil) {
                g.setColor(new Color(250, 0, 0, 80));
            } else if (jugadorDebil) {
                g.setColor(new Color(150, 0, 0, 150));
            } else {
                g.setColor(new Color(0, 0, 0, 80));
            }
            g.fillRect(0, 0, ancho, alto);
        }
        
    }

    public void crearPaneles(Ventana ventana) {        
        inventarioPanel = new Inventario(ventana);
        tiendaPanel = new Tienda(ventana);
    }
    
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    
    public Controlador getControlador() {
        return controlador;
    }
    
    public void keyPressed(KeyEvent e) {
        controlador.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                pausa.doClick();
                break;
            case KeyEvent.VK_R:
                inventarioPanel.toogleObjetoClickeado(e.getKeyChar());
                inventarioPanel.setActualizarInventario(true);
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_4:
            case KeyEvent.VK_5:
            case KeyEvent.VK_6:
            case KeyEvent.VK_7:
            case KeyEvent.VK_8:
            case KeyEvent.VK_9:
            case KeyEvent.VK_0:
                if(inventarioPanel.isObjetoClickeado()) {
                    inventarioPanel.toogleObjetoClickeado(e.getKeyChar());
                    inventarioPanel.setActualizarInventario(true);
                    return;
                }
                inventarioPanel.toogleObjetoClickeado(e.getKeyChar());
                inventarioPanel.setActualizarInventario(true);
                break;
        }
        
        controlador.keyReleased(e);
    }
    
    public void mousePressed(MouseEvent e) {
        controlador.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {
        controlador.mouseReleased(e);
    }
    
    public void mouseClicked(MouseEvent e) {
        controlador.mouseClicked(e);
    }

}
