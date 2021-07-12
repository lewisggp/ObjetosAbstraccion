/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo;

import Juego.Controlador.HojaSprites;
import Juego.Modelo.Ente.Ente;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Lewis
 */
public class Teletransportador extends Objeto {

    private static final HojaSprites spritesTeletransportadores = new HojaSprites("/objetos/Teletransportadores.png", 16, true);
    
    private final Point posicionFinal;
    private final String mapaFinal;

    public Teletransportador(int id, String nombre, String descripcion, int idSprite, Point posicion, String mapaFinal, Point posicionFinal) {
        super(id, nombre, descripcion, spritesTeletransportadores.getSprite(idSprite), posicion);
        this.posicionFinal = posicionFinal;
        this.mapaFinal = mapaFinal;
    }
    
    private Teletransportador(String datoTeletransportador[]) {
        this(Integer.parseInt(datoTeletransportador[0]),
            datoTeletransportador[1],
            datoTeletransportador[2],
            Integer.parseInt(datoTeletransportador[3]),
            new Point(Integer.parseInt(datoTeletransportador[4]),
                    Integer.parseInt(datoTeletransportador[5])),
            datoTeletransportador[6],
            new Point(Integer.parseInt(datoTeletransportador[7]),
                    Integer.parseInt(datoTeletransportador[8])));
    }

    public Teletransportador(String str) {
        this(str.split(", "));
    }
    
    // --------------------------------------------------- GETTERS
    
    public Point getPosicionFinal() {
        return posicionFinal;
    }

    public String getMapaFinal() {
        return mapaFinal;
    }

    // --------------------------------------------------- TELETRANSPORTAR
    
    public void teletransportar(Ente ente) {
        ente.getArea().setLocation(posicionFinal);
    }

    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    protected Point calcularArea(Point posicion) {
        area = new Rectangle(posicion.x,
                            posicion.y,
                            sprite.getAncho(),
                            sprite.getAlto());
        return new Point();
    }

    @Override
    public String toString() {
        return getId() + ", "
                + getNombre() + ", "
                + getDescripcion() + ", "
                + getSprite().getId()+ ", "
                + getPosicion().x + ", "
                + getPosicion().y + ", "
                + getMapaFinal() + ", "
                + getPosicionFinal().x + ", "
                + getPosicionFinal().y;
    }

}
