package Juego.Modelo.Ente;

import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Municion;
import java.awt.Point;

public class Enemigo extends Ente {

    private final int ataque;
    private final ListaSimple objetos;
    
    public Enemigo(int id, String nombre, String descripcion, String hojaSprites, Point posicionInicial, int vidaMaxima, int velocidad, int ataque) {
        super(id, nombre, descripcion, hojaSprites, posicionInicial, vidaMaxima, velocidad);
        this.ataque = ataque;
        objetos = getObjetosDefault();
    }
        
    private Enemigo(String datoEnemigo[]) {
        this(Integer.parseInt(datoEnemigo[0]),
                    datoEnemigo[1],
                    datoEnemigo[2],
                    datoEnemigo[3],
                    new Point(Integer.parseInt(datoEnemigo[4]),
                            Integer.parseInt(datoEnemigo[5])),
                    Integer.parseInt(datoEnemigo[6]),
                    Integer.parseInt(datoEnemigo[7]),
                    Integer.parseInt(datoEnemigo[8]));
    }

    public Enemigo(String str) {
        this(str.split(", "));
    }
    
    // --------------------------------------------------- GETTERS
    
    public int getAtaque() {
        return ataque;
    }
    
    public ListaSimple getObjetos() {
        if( ! isVivo()) {
            return objetos;
        }
        return null;
    }

    private static ListaSimple getObjetosDefault() {
        ListaSimple objetos = new ListaSimple();
        objetos.add(new Consumible(11, "C1", "D1", 0, new Point(), 5, 3));
        objetos.add(new Municion(10, "Municion", "Pistola", 4, new Point(), 5, "Pistola"));
        objetos.add(new Municion(15, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"));
        return objetos;
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS

    @Override
    public String toString() {
        return getId() + ", " 
                + getNombre() + ", " 
                + getDescripcion() + ", " 
                + getNombreAnimacion() + ", " 
                + getPosicion().x + ", " 
                + getPosicion().y + ", " 
                + getVidaActual()+ ", "
                + getVelocidad() + ", "
                + getAtaque();
    }

}
