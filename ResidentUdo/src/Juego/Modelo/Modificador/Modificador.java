package Juego.Modelo.Modificador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Juego.Modelo.Objeto;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Sprite;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public abstract class Modificador extends Objeto {

    public Modificador(int id, String nombre, String descripcion, Sprite sprite, Point posicion) {
        super(id, nombre, descripcion, sprite, posicion);
    }

    public abstract void darVida(Ente ente);

    public abstract void quitarVida(Ente ente);
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    protected Point calcularArea(Point posicion) {
        area = new Rectangle(posicion.x,
                            posicion.y,
                            sprite.getAncho(),
                            sprite.getAlto() / 2);
        return new Point(0, sprite.getAlto() - area.height);
    }

}
