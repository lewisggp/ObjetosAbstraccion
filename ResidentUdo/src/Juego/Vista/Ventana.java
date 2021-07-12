package Juego.Vista;

import Juego.Controlador.Cargador;
import Juego.Controlador.Controlador;
import Juego.Modelo.Estructura.Diccionario;
import Juego.Vista.Estado.Datos;
import Juego.Vista.Estado.GameOver;
import Juego.Vista.Estado.Historia;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import Juego.Vista.Estado.Instrucciones;
import Juego.Vista.Estado.Juego;
import Juego.Vista.Estado.Login;
import Juego.Vista.Estado.Menu;
import Juego.Vista.Estado.Opcion;
import Juego.Vista.Estado.Pausa;
import Juego.Vista.Estado.Presentacion;
import Juego.Vista.Estado.Puntajes;
import Juego.Vista.Estado.Register;
import Juego.Vista.Estado.Tutorial;
import java.awt.Font;
import java.awt.Point;

public class Ventana extends JFrame implements MouseListener, KeyListener {

    private String user;
    private String pass;
    private int puntosUsuario;
    private String jugadorSeleccionado;

    private final Historia historia;
    private final Login login;
    private final Register register;
    private final Datos datos;
    private final Menu menu;
    private final Presentacion presentacion;
    private final Puntajes puntajes;
    private final Instrucciones instrucciones;
    private final Pausa pausa;
    private final GameOver gameOver;
    private Juego juego;
    private Tutorial tutorial;
    
    public static Font font;

    public Ventana(int ancho, int alto) {
        setSize(ancho, alto);
        setLocationRelativeTo(null);
        setUndecorated(true);

        user = "User";
        puntosUsuario = 0;
        jugadorSeleccionado = "Blue_32x32";        
        font = Cargador.fuente("/font/Slackey.ttf");

        login = new Login(this);
        register = new Register(this);
        historia = new Historia(this);
        datos = new Datos(this);
        menu = new Menu(this);
        presentacion = new Presentacion(this);
        puntajes = new Puntajes(this);
        instrucciones = new Instrucciones(this);
        pausa = new Pausa(this);
        gameOver = new GameOver(this);
                
        setContentPane(presentacion);
        
        Diccionario ultimaSesion = Cargador.ultimaSesion();
        if(ultimaSesion.size() > 2 && (boolean) ultimaSesion.getValor("Mantener")) {
            login.enter();
        }

        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void run() {
        long ultimoTiempo;
        int fps = 0;
        while (true) {
            repaint();
            ultimoTiempo = System.nanoTime();
            try {
                Thread.sleep(1000 / 64);
            } catch (InterruptedException e) {

            }
            fps = (int) (1000000000 / (System.nanoTime() - ultimoTiempo));
            // System.out.println("FPS " + fps);
        }
    }

    // --------------------------------------------------- GETTERS
    
    public String getUser() {
        return user;
    }
    
    public Controlador getControlador() {
        return juego.getControlador();
    }

    public String getJugadorSeleccionado() {
        return jugadorSeleccionado;
    }
    
    // --------------------------------------------------- SETTERS

    private void setUsuario(String usuario) {
        this.user = usuario;
        //cargarUsuario(usuario);
    }
    
    public void setJugadorSeleccionado(String jugadorSeleccionado) {
        this.jugadorSeleccionado = jugadorSeleccionado;
        juego.getControlador().getJugador().setAnimacion(jugadorSeleccionado);
    }

    public void setContentPane(String panel) {
        //removeAll();
        switch (panel) {
            case "Historia":
                setContentPane(historia);
                break;
            case "Login":
                setContentPane(login);
                break;
            case "Register":
                setContentPane(register);
                break;
            case "Datos":
                setContentPane(datos);
                break;
            case "Instrucciones":
                setContentPane(instrucciones);
                break;
            case "Puntajes":
                setContentPane(puntajes);
                break;
            case "Pausa":
                pausa.setPanelAnterior(getContentPane().getClass().getSimpleName());
                setContentPane(pausa);
                break;
            case "Juego":                
                if (juego == null) {
                    juego = new Juego(this, new Controlador(user, jugadorSeleccionado, puntosUsuario));
                    juego.crearPaneles(this);
                    tutorial = new Tutorial(this);
                }
                setContentPane(juego);
                
                if (tutorial.getPaso() == 0) {
                    remove(tutorial);
                } else {
                    add(tutorial);
                }
                break;
            case "Menu":
                setContentPane(menu);
                break;
            case "GameOver":
                juego = null;
                setContentPane(gameOver);
                break;
        }
        validate();
    }
        
    // --------------------------------------------------- CARGAR USUARIO
    
    public void cargarNuevoUsuario(String usuario) {
        setUsuario(usuario);
        jugadorSeleccionado = Cargador.ENTE_DEFAULT;
        juego = new Juego(this, new Controlador(usuario, jugadorSeleccionado, 0));
        juego.crearPaneles(this);
        tutorial = new Tutorial(this);
        datos.setSeleccionado(jugadorSeleccionado);
    }
    
    public void cargarUsuario(String usuario) {
        setUsuario(usuario);
        String[] info = Cargador.texto("/registros/usuarios/" + usuario + "/" + usuario + ".txt").split("-sig-");
        String nombreMapa = "UDO";
        for(int i = 0; i < info.length; i++) {
            String[] infoTemp = info[i].split("-");
            switch (infoTemp[0].toUpperCase()) {
                case "PUNTOS":
                    puntosUsuario = (int) Cargador.toObjeto(infoTemp[1]);
                    break;
                case "DISPONIBLES":
                    datos.setDisponibles(Cargador.toListaCircular(infoTemp));
                    break;
                case "SELECCIONADO":
                    jugadorSeleccionado = (String) Cargador.toObjeto(infoTemp[1]);
                    juego = new Juego(this, new Controlador(usuario, jugadorSeleccionado, puntosUsuario));
                    juego.crearPaneles(this);
                    tutorial = new Tutorial(this);
                    datos.setSeleccionado(jugadorSeleccionado);
                    break;
                case "MAPA":
                    nombreMapa = (String) Cargador.toObjeto(infoTemp[1]);
                    getControlador().setMapa(nombreMapa);
                    break;
                case "POSICION":
                    getControlador().getJugador().getArea().setLocation((Point) Cargador.toObjeto(infoTemp[1]));
                    break;
                case "VIDA":
                    getControlador().getJugador().perderVida(getControlador().getJugador().getVidaMaxima() - (int) Cargador.toObjeto(infoTemp[1]));
                    break;
                case "CONSUMIBLES":
                    getControlador().getInventario().setConsumibles(Cargador.toLista(infoTemp));
                    break;
                case "ARMAS":
                    getControlador().getInventario().setArmas(Cargador.toLista(infoTemp));
                    break;
                case "MUNICIONES":
                    getControlador().getInventario().setMuniciones(Cargador.toLista(infoTemp));
                    break;
                case "ARMA SELECCIONADA":
                    getControlador().seleccionarArma(getControlador().getInventario().getArma((int) Cargador.toObjeto(infoTemp[1])));
                    break;
                case "ATAJOS":
                    getControlador().setAtajos(Cargador.toDiccionario(infoTemp));
                    break;
            }
        }
        getControlador().getMapa().setInformacion(Cargador.texto("/registros/usuarios/" + usuario + "/mapa" + nombreMapa + ".txt").split("-sig-"));
    }

    // --------------------------------------------------- SALIR SESION
    
    public void guardarSesion(String user, String pass, boolean mantener, boolean recordar) {
        this.user = user;
        this.pass = pass;
        Cargador.guardarSesion(user, pass, mantener, recordar);
    }
        
    public void salir() {
        if(juego != null && Opcion.showConfirm(this, true, "Desea guardar la partida?")){
            Cargador.guardarPartida(getControlador(), datos.getDisponibles());
        }
        System.exit(0);
    }
    
    public void logOut() {
        if(Opcion.showConfirm(this, true, "Esta seguro de salir?")) {
            guardarSesion(user, pass, false, login.isRecordarSesion() || register.isRecordarSesion()); // Mantener = false
            salir();
        }
    }
    
    // --------------------------------------------------- TECLADO
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (getContentPane().getClass().getSimpleName()) {
            case "Juego":
            case "Inventario":
            case "Tienda":
                juego.keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (getContentPane().getClass().getSimpleName()) {
            case "Inventario":
                juego.getControlador().reproducirSonido("Bolso", Controlador.SONIDO_REPRODUCIR_SINO);
            case "Juego":
            case "Tienda":
                juego.keyReleased(e);
                break;
            case "Presentacion":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) presentacion.enter();
                break;
            case "Login":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) login.enter();
                break;
            case "Register":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) register.enter();
                break;
            case "Historia":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) historia.enter();
                break;
            case "Datos":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) datos.enter();
                break;
            case "Menu":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) menu.enter();
                break;
            case "Puntajes":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) puntajes.enter();
                break;
            case "Instrucciones":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) instrucciones.enter();
                break;
            case "GameOver":
                if(e.getKeyCode() == KeyEvent.VK_ENTER) gameOver.enter();
                break;
            case "Pausa":
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) pausa.renaudar();               
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    // --------------------------------------------------- RATON
    
    @Override
    public void mousePressed(MouseEvent e) {
        switch (getContentPane().getClass().getSimpleName()) {
            case "Juego":
            case "Inventario":
            case "Tienda":
                juego.mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (getContentPane().getClass().getSimpleName()) {
            case "Juego":
            case "Inventario":
            case "Tienda":
                juego.mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (getContentPane().getClass().getSimpleName()) {
            case "Juego":
            case "Inventario":
            case "Tienda":
                juego.mouseClicked(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
