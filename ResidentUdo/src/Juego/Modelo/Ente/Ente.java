/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo.Ente;

import Juego.Modelo.Animacion;
import java.awt.Point;

/**
 *
 * @author Lewis
 */
public abstract class Ente extends Animacion{

    protected int vidaMaxima;
    protected int vidaActual;
    protected int velocidad;

    public Ente(int id, String nombre, String descripcion, String hojaSprites, Point posicionInicial, int vidaMaxima, int velocidad) {
        super(id, nombre, descripcion, hojaSprites, posicionInicial);
        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;
        this.direccion = 2;
        this.velocidad = velocidad;
    }
    
    // --------------------------------------------------- GETTERS
    
    public int getVelocidad() {
        return velocidad;
    }
    
    public int getVidaActual() {
        return vidaActual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }
    
    public boolean isVivo() {
        return vidaActual > 0;
    }
    
    // --------------------------------------------------- SETTERS

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    // --------------------------------------------------- VIDA
    
    public void ganarVida(int aumentaVida) {
        if (vidaActual + aumentaVida > vidaMaxima) {
            vidaActual = vidaMaxima;
        } else {
            vidaActual += aumentaVida;
        }
    }

    public void perderVida(int ataqueRecibido) {
        if (vidaActual - ataqueRecibido < 0) {
            vidaActual = 0;
        } else {
            vidaActual -= ataqueRecibido;
        }
    }
    
    // --------------------------------------------------- MOVER

    public void mover(int dx, int dy) {
        area.translate(dx * velocidad, dy * velocidad);
    }
    
}
