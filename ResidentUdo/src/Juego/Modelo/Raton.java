package Juego.Modelo;

import java.awt.Point;

public class Raton {

    private Point posicion;
    private boolean click;
    private boolean clickDerecho;

    public Raton() {
        posicion = new Point();
        click = false;
        clickDerecho = false;
    }

    // --------------------------------------------------- GETTERS
    
    public Point getPosicion() {
        return posicion;
    }

    public boolean isClick() {
        return click;
    }

    public boolean isClickDerecho() {
        return clickDerecho;
    }

    // --------------------------------------------------- SETTERS

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public void setClickDerecho(boolean clickDerecho) {
        this.clickDerecho = clickDerecho;
    }
        
    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        return "[Raton - ClickIzquierdo: " + click + ", ClickDerecho: " + clickDerecho + ", Posicion: (" + posicion.x + ", " + posicion.y + ")]";
    }

}
