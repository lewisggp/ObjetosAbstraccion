/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public abstract class Objeto {
    
    protected final int id;
    protected final String nombre;
    protected final String descripcion;
    protected final Point dPosicion;
    protected Sprite sprite;    
    protected Rectangle area;

    public Objeto(int id, String nombre, String descripcion, Sprite sprite, Point posicion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sprite = sprite;
        dPosicion = calcularArea(posicion);
    }
        
    public Objeto(int id, String nombre, String descripcion, Point posicion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        dPosicion = calcularArea(posicion);
    }
    
    protected abstract Point calcularArea(Point posicion);
    public abstract String toString(); // Para guardar en archivos
    
    // --------------------------------------------------- GETTERS
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getArea() {
        return area;
    }

    public Point getPosicion() {
        return area.getLocation();
    }
    
    // --------------------------------------------------- SETTERS
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void setArea(Rectangle area) {
        this.area = area;
    }
    
    public int getDxPosicion() {
        return dPosicion.x;
    }
    
    public int getDyPosicion() {
        return dPosicion.y;
    }

}
