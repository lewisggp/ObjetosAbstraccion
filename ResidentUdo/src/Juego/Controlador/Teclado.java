package Juego.Controlador;

import Juego.Modelo.Tecla;

public class Teclado {

    private final Tecla arriba;
    private final Tecla abajo;
    private final Tecla izquierda;
    private final Tecla derecha;
    private final Tecla atacando;
    private final Tecla recargando;  
    private final Tecla atajo;
    private char atajoPulsado;
    
    private boolean recogiendo;
    private boolean corriendo;
    private boolean inventarioActivo;
    private boolean comprando;
    private boolean debug;

    public Teclado() {
        arriba = new Tecla();
        abajo = new Tecla();
        izquierda = new Tecla();
        derecha = new Tecla();
        atacando = new Tecla();
        recargando = new Tecla();
        
        atajo = new Tecla();
        atajoPulsado = ' ';
        
        recogiendo = false;
        corriendo = false;
        inventarioActivo = false;
        comprando = false;
        debug = false;
    }
    
    // --------------------------------------------------- GETTERS
    
    public static long getTime() {
        return System.nanoTime();
    }

    public Tecla getArriba() {
        return arriba;
    }

    public Tecla getAbajo() {
        return abajo;
    }

    public Tecla getIzquierda() {
        return izquierda;
    }

    public Tecla getDerecha() {
        return derecha;
    }

    public Tecla getAtacando() {
        return atacando;
    }

    public Tecla getRecargando() {
        return recargando;
    }

    public Tecla getAtajo() {
        return atajo;
    }

    public char getAtajoPulsado() {
        return atajoPulsado;
    }
    
    public boolean isRecogiendo() {
        return recogiendo;
    }

    public boolean isCorriendo() {
        return corriendo;
    }

    public boolean isInventarioActivo() {
        return inventarioActivo;
    }

    public boolean isComprando() {
        return comprando;
    }

    public boolean isDebug() {
        return debug;
    }

    // --------------------------------------------------- SETTERS
    
    public void setAtajoPulsado(char atajo) {
        atajoPulsado = atajo;
    }

    public void setRecogiendo(boolean recogiendo) {
        this.recogiendo = recogiendo;
    }
        
    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }

    public void setInventarioActivo(boolean inventarioActivo) {
        this.inventarioActivo = inventarioActivo;
    }

    public void setComprando(boolean comprando) {
        this.comprando = comprando;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    // --------------------------------------------------- STRING   
    
    @Override
    public String toString() {
        return "[Teclado - Atacando:" + atacando + ", Corriendo:" + corriendo + ", Recogiendo:" + recogiendo + ", inventarioActivo:" + inventarioActivo + "]";
    }

}
