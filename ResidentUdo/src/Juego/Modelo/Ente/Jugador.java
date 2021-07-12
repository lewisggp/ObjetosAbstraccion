package Juego.Modelo.Ente;

import java.awt.Point;
import Juego.Modelo.Modificador.Arma;

public class Jugador extends Ente {

    private final int resistenciaMaxima;
    private Arma arma;
    private int puntos;
    private int contadorResistencia;
    private int resistencia;
    private boolean recuperado;

    public Jugador(int id, String usuario, String descripcion, String jugadorSeleccionado, int puntosUsuario, Point posicionInicial, int vidaMaxima, int velocidad, Arma arma) {
        super(id, usuario, descripcion, jugadorSeleccionado, posicionInicial, vidaMaxima, velocidad);

        puntos = puntosUsuario;
        this.velocidad = velocidad;
        resistenciaMaxima = 100;
        contadorResistencia = 0;
        resistencia = resistenciaMaxima;
        recuperado = true;
        this.arma = arma;
    }
    
    // --------------------------------------------------- GETTERS
    
    public Arma getArma() {
        return arma;
    }
    
    public int getResistencia() {
        return resistencia;
    }

    public int getResistenciaMaxima() {
        return resistenciaMaxima;
    }
    
    public boolean isRecuperado() {
        return recuperado;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    // --------------------------------------------------- SETTERS
    
    public void setArma(Arma arma) {
        this.arma = arma;
    }
    
    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public void setRecuperado(boolean recuperado) {
        this.recuperado = recuperado;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    // --------------------------------------------------- RESISTENCIA
    
    public void aumentarResistencia() {
        if (resistencia < resistenciaMaxima) {
            if (contadorResistencia == 100) {
                resistencia++;
            } else {
                contadorResistencia++;
            }
        }
    }

    public void disminuirResistencia() {
        if (resistencia > 0) {
            resistencia--;
        }
        contadorResistencia = 0;
    }

    // --------------------------------------------------- PUNTOS

    public void addPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void removePuntos(int puntos) {
        if (puntos < this.puntos) {
            this.puntos -= puntos;
        } else {
            this.puntos = 0;
        }
    }

    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        return "Jugador: [ID" + id + ", Area:" + area + ", Arma:" + arma + ", Direccion:" + direccion + ", Vida:" + vidaActual + ", Resistencia:" + resistencia + ", Puntos:" + puntos + "]";
    }

}
