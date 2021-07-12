/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo;

import Juego.Controlador.HojaSprites;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public class Municion extends Objeto{
    
    private static HojaSprites spriteMuniciones = new HojaSprites("/objetos/Municiones.png", 16, true);
    
    private int balas;
    private String tipo;
        
    public Municion(int id, String nombre, String descripcion, int idSprite, Point posicion, int balas, String tipo) {
       super(id, nombre, descripcion, spriteMuniciones.getSprite(idSprite), posicion);
       this.balas = balas;
       this.tipo = tipo;
    }
    
    private Municion(String datoMuniciones[]) {
        this(Integer.parseInt(datoMuniciones[0]),
                    datoMuniciones[1],
                    datoMuniciones[2],
                    Integer.parseInt(datoMuniciones[3]),
                    new Point(Integer.parseInt(datoMuniciones[4]),
                            Integer.parseInt(datoMuniciones[5])),
                    Integer.parseInt(datoMuniciones[6]),
                    datoMuniciones[7]);
    }

    public Municion(String str) {
        this(str.split(", "));
    }
    
    // --------------------------------------------------- GETTERS
    
    public int getBalas(int balas) {
        if( this.balas < balas) {
            int b = this.balas;
            this.balas = 0;
            return b;
        } else {
            this.balas -= balas;
            return balas;
        }
    }
    
    public int getBalas() {
        return balas;
    }
    
    public String getTipo() {
        return tipo;
    }

    // --------------------------------------------------- SETTERS
    
    private void setBalas(int balas) {
        this.balas = balas;
    }
        
    public void spriteGrande() {
        if(sprite.getId() % 2 == 1) { // Esta en el sprite pequeño
            sprite = spriteMuniciones.getSprite(sprite.getId() - 1); // Al sprite anterior (Grande) estara en el inventario
        }
    }
    
    public void spritePequeno() {
        if(sprite.getId() % 2 == 0) { // Esta en el sprite grande
            sprite = spriteMuniciones.getSprite(sprite.getId() + 1); // Al sprite siguiente (Pequeño) estara en el mapa
        }
    }
    
    // --------------------------------------------------- AGREGAR
    
    public void agregarMunicion(Municion municion) {
        if( ! tipo.equalsIgnoreCase(municion.getTipo())) return;
        balas += municion.getBalas();
        municion.setBalas(0);
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    protected Point calcularArea(Point posicion) {
        area = new Rectangle(posicion.x,
                            posicion.y,
                            sprite.getAncho(),
                            sprite.getAlto() / 2);
        return new Point(0, sprite.getAlto() - area.height);
    }
    
    @Override
    public String toString() {
        return getId() + ", " 
                + getNombre() + ", " 
                + getDescripcion() + ", " 
                + getSprite().getId()+ ", " 
                + getPosicion().x + ", " 
                + getPosicion().y + ", " 
                + getBalas()+ ", "
                + getTipo();
    }

}
