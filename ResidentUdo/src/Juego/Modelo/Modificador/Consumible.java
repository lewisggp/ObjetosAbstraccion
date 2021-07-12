package Juego.Modelo.Modificador;

import Juego.Controlador.HojaSprites;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Sprite;
import java.awt.Point;
import java.awt.Rectangle;

public class Consumible extends Modificador {

    private static HojaSprites spriteConsumibles = new HojaSprites("/objetos/Consumibles.png", 16, true);
    private final static int cantidadMaxima = 99;
    
    private int cantidad;
    private int aumentaVida;

    public Consumible(int id, String nombre, String descripcion, Sprite sprite, Point posicion, int aumentaVida, int cantidad) {
        super(id, nombre, descripcion, sprite, posicion);

        this.aumentaVida = aumentaVida;
        if (cantidad <= cantidadMaxima) {
            this.cantidad = cantidad;
        } else {
            this.cantidad = cantidadMaxima;
        }
    }
    
    public Consumible(int id, String nombre, String descripcion, int idSprite, Point posicion, int aumentaVida, int cantidad) {
        super(id, nombre, descripcion, spriteConsumibles.getSprite(idSprite), posicion);

        this.aumentaVida = aumentaVida;
        if (cantidad <= cantidadMaxima) {
            this.cantidad = cantidad;
        } else {
            this.cantidad = cantidadMaxima;
        }
    }
    
    private Consumible(String datoConsumible[]) {
        this(Integer.parseInt(datoConsumible[0]),
                    datoConsumible[1],
                    datoConsumible[2],
                    Integer.parseInt(datoConsumible[3]),
                    new Point(Integer.parseInt(datoConsumible[4]),
                            Integer.parseInt(datoConsumible[5])),
                    Integer.parseInt(datoConsumible[6]),
                    Integer.parseInt(datoConsumible[7]));
    }

    public Consumible(String str) {
        this(str.split(", "));
    }
    
    // --------------------------------------------------- GETTERS

    public int getAumentaVida() {
        return aumentaVida;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCantidadMaxima() {
        return cantidadMaxima;
    }
    
    public Consumible get(int cantidad) {
        if (this.cantidad - cantidad > 0) {
            this.cantidad -= cantidad;
            return new Consumible(id, nombre, descripcion, sprite, area.getLocation(), aumentaVida, cantidad);
        } else {
            int cantidadTemp = this.cantidad;
            this.cantidad = 0;
            return new Consumible(id, nombre, descripcion, sprite, area.getLocation(), aumentaVida, cantidadTemp);
        }
    }

    // --------------------------------------------------- CANTIDAD
    
    public void aumentarCantidad(int cantidad) {
        if (this.cantidad + cantidad <= cantidadMaxima) {
            this.cantidad += cantidad;
        } else {
            this.cantidad = cantidadMaxima;
        }
    }
    
    public void disminuirCantidad(int cantidad) {
        if (this.cantidad - cantidad > 0) {
            this.cantidad -= cantidad;
        } else {
            this.cantidad = 0;
        }
    }   
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    public String toString() {
        return getId() + ", " 
                + getNombre() + ", " 
                + getDescripcion() + ", " 
                + getSprite().getId() + ", " 
                + getPosicion().x + ", " 
                + getPosicion().y + ", " 
                + getAumentaVida() + ", "
                + getCantidad();
    }

    @Override
    public void darVida(Ente ente) {
        ente.ganarVida(aumentaVida);
    }

    @Override
    public void quitarVida(Ente ente) {
        ente.perderVida(aumentaVida);
    }

}
