/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo.Estructura;

import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public class NodoDijkstra {

    private NodoDijkstra[] vecinos;
    private boolean solido;
    private boolean visitado;
    private Rectangle area;
    private int valor;

    public NodoDijkstra(Rectangle area) {
        this.area = area;
        valor = Integer.MAX_VALUE;
    }

    public void reiniciar() {
        setValor(Integer.MAX_VALUE);
        setVisitado(false);
        setSolido(false);
    }

    public NodoDijkstra[] getVecinos() {
        return vecinos;
    }

    public void setVecinos(NodoDijkstra[] vecinos) {
        this.vecinos = vecinos;
    }

    public boolean isSolido() {
        return solido;
    }

    public void setSolido(boolean solido) {
        this.solido = solido;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
