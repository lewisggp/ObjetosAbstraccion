package Juego.Modelo.Modificador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Juego.Controlador.HojaSprites;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Sprite;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author Lewis
 */
public abstract class Arma extends Modificador {

    private static HojaSprites spritesArmas = new HojaSprites("/objetos/Armas.png", 16, true);
    
    protected int alcance;
    protected int ataqueMinimo;
    protected int ataqueMaximo;
    protected boolean penetrante;
    
    protected int balasActual;
    protected int balasMaximo;

    public Arma(int id, String nombre, String descripcion, Sprite sprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo, boolean penetrante, int balasMaximo) {
        super(id, nombre, descripcion, sprite, posicion);
        this.penetrante = penetrante;
        this.alcance = alcance;
        this.ataqueMinimo = ataqueMinimo;
        this.ataqueMaximo = ataqueMaximo;
        this.balasActual = balasMaximo;
        this.balasMaximo = balasMaximo;
    }
    
    public Arma(int id, String nombre, String descripcion, int idSprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo, boolean penetrante, int balasMaximo) {
        super(id, nombre, descripcion, spritesArmas.getSprite(idSprite), posicion);
        this.penetrante = penetrante;
        this.alcance = alcance;
        this.ataqueMinimo = ataqueMinimo;
        this.ataqueMaximo = ataqueMaximo;
        this.balasActual = balasMaximo;
        this.balasMaximo = balasMaximo;
    }

    public abstract ListaSimple getAlcance(Ente ente);

    public abstract int getPuntos(Ente ente, ListaSimple entes);

    // --------------------------------------------------- GETTERS
    
    public int getAlcance() {
        return alcance;
    }

    public int getAtaqueMinimo() {
        return ataqueMinimo;
    }

    public int getAtaqueMaximo() {
        return ataqueMaximo;
    }

    public int getAtaqueMedio() {
        Random r = new Random();
        return r.nextInt(ataqueMaximo - ataqueMinimo) + ataqueMinimo;
    }

    public int getBalasActual() {
        return balasActual;
    }
    
    public int getBalasMaximo() {
        return balasMaximo;
    }

    public boolean isPenetrante() {
        return penetrante;
    }

    // --------------------------------------------------- RECARGAR
    
    public void recargar(int balas) {
        balasActual = (balasActual + balas > balasMaximo) ? balasMaximo: balasActual + balas;
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    public void darVida(Ente ente) {
        if(balasActual == 0) return;
        int ataqueActual = getAtaqueMedio();
        ente.ganarVida(ataqueActual);
    }

    @Override
    public void quitarVida(Ente ente) {
        if(balasActual == 0) return;
        int ataqueActual = getAtaqueMedio();
        ente.perderVida(ataqueActual);
    }
    
}
