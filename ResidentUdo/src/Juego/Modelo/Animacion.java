/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo;

import Juego.Controlador.Cargador;
import Juego.Controlador.Controlador;
import Juego.Controlador.HojaSprites;
import Juego.Modelo.Estructura.Diccionario;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public abstract class Animacion extends Objeto {

    private String nombreAnimacion;
    private Diccionario hojasSprites;
    private HojaSprites hojaActual;
    private int claveHojaActual;
    protected int direccion;
    private boolean enMovimiento;
    private boolean animacionEspecial;

    public Animacion(int id, String nombre, String descripcion, String ruta, Point posicionInicial) {
        super(id, nombre, descripcion, posicionInicial);
        setAnimacion(ruta);
    }
    
    // --------------------------------------------------- GETTERS
    
    public String getNombreAnimacion() {
        return nombreAnimacion; 
    }

    public int getHojaActual() {
        return claveHojaActual;
    }

    public int getDireccion() {
        return direccion;
    }
    
    public boolean isAnimacionEspecial() {
        return animacionEspecial;
    }

    public boolean isEnMovimiento() {
        return enMovimiento;
    }

    // --------------------------------------------------- SETTERS
    
    public void setAnimacion(String ruta) {
        nombreAnimacion = ruta;
        hojasSprites = Cargador.animacion("/entes/" + ruta, Controlador.LADO_SPRITE, Controlador.LADO_SPRITE);
        animacionEspecial = false;
        hojaActual = (HojaSprites) hojasSprites.get(0);
        claveHojaActual = 0;
        setSprite(hojaActual.getSprite(0));
        //super.generateArea();

        enMovimiento = false;
    }
    
    public void changeSprite() {        
        if (enMovimiento || animacionEspecial) {
            //sprite = hojaActual.getSprite((direccion + 1) * hojaActual.getSpritesX() + x);
            int x = sprite.getId() % hojaActual.getSpritesX() + 1;
            if (x >= hojaActual.getSpritesX()) {
                animacionEspecial = false;
                x = 0;
            }
            sprite = hojaActual.getSprite(x);
        } else {
            int x = sprite.getId() % ((HojaSprites) hojasSprites.getValor(1)).getSpritesX() + 1;
            if (x >= ((HojaSprites) hojasSprites.getValor(1)).getSpritesX()) {
                x = 0;
            }
            sprite = ((HojaSprites) hojasSprites.getValor(1)).getSprite(x);
        }
    }

    public void changeHoja(int i) {
        if (hojasSprites.indexOf(i) != -1) {
            claveHojaActual = i;
            hojaActual = (HojaSprites) hojasSprites.getValor(claveHojaActual);
            if (i > 5) {
                animacionEspecial = true;
                sprite = hojaActual.getSprite(0);
            }
        }
    }
    
    public void changeDireccion(int dx, int dy) {
        if (dx > 0) {
            direccion = 4; // Este
            changeHoja(4);
        } else if (dx < 0) {
            direccion = 5; // Oeste
            changeHoja(5);
        }
        if (dy < 0) {
            direccion = 2; // Norte
            changeHoja(2);
        } else if (dy > 0) {
            direccion = 3; // Sur
            changeHoja(3);
        }
    }
    
    public void setEnMovimiento(boolean enMovimiento) {
        this.enMovimiento = enMovimiento;
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    protected Point calcularArea(Point posicion) {
        area = new Rectangle(posicion.x,
                            posicion.y,
                            Controlador.LADO_SPRITE / 4,
                            Controlador.LADO_SPRITE / 4);
        return new Point(Controlador.LADO_SPRITE / 2 - area.width / 2,
                        Controlador.LADO_SPRITE * 3 / 4 - area.height / 2);
    }
    
}
