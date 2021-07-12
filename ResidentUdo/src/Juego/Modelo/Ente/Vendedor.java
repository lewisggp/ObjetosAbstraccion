/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo.Ente;

import Juego.Controlador.Cargador;
import Juego.Modelo.Estructura.Diccionario;
import Juego.Modelo.Estructura.NodoDiccionario;
import Juego.Modelo.Modificador.Arma;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Modificador.Escopeta;
import Juego.Modelo.Municion;
import Juego.Modelo.Modificador.Pistola;
import java.awt.Point;

/**
 *
 * @author Lewis
 */
public class Vendedor extends Ente {

    private Diccionario consumibles;
    private Diccionario armas;
    private Diccionario municiones;
    private int puntos;

    public Vendedor(int id, String nombre, String descripcion, String hojaSprites, Point posicionInicial, int vidaMaxima) {
        super(id, nombre, descripcion, hojaSprites, posicionInicial, vidaMaxima, 0);
        setDefaultObjetos();
        puntos = 0;
    }
    
    private Vendedor(String datoVendedor[], String objetos[]) {
        this(Integer.parseInt(datoVendedor[0]),
                    datoVendedor[1],
                    datoVendedor[2],
                    datoVendedor[3],
                    new Point(Integer.parseInt(datoVendedor[4]),
                            Integer.parseInt(datoVendedor[5])),
                    Integer.parseInt(datoVendedor[6]));
        
        consumibles = new Diccionario();
        armas = new Diccionario();
        municiones = new Diccionario();
                
        NodoDiccionario nodo = Cargador.toDiccionario(objetos).getFirstNodo();
        while(nodo != null) {
            if(nodo.getClave() instanceof Consumible) consumibles.add(nodo.getClave(), nodo.getValor());
            else if(nodo.getClave() instanceof Arma) armas.add(nodo.getClave(), nodo.getValor());
            else if(nodo.getClave() instanceof Municion) municiones.add(nodo.getClave(), nodo.getValor());
            else System.out.println(nodo.getClave().getClass().getSimpleName() + " no aceptado por vendedor");
            nodo = nodo.getNext();
        }
        setDefaultObjetos();
        
    }
    
    public Vendedor(String str[]) {
        this(str[0].split(": ")[1].split(", "), str);
    }

    public Vendedor(String str) {
        this(str.split(" > "));
    }
    
    // --------------------------------------------------- SETTERS
    
    public void setDefaultObjetos() {
        consumibles = new Diccionario();
        consumibles.add(new Consumible(11, "C1", "D1", 0, new Point(), 5, 3), 2);
        consumibles.add(new Consumible(12, "C2", "D2", 10, new Point(), 8, 3), 4);

        armas = new Diccionario();
        armas.add(new Escopeta(10, "Escopeta", "Descripcion", 2, new Point(), 2, 4, 5), 10);
        armas.add(new Pistola(11, "Pistola2", "Descripcion", 1, new Point(), 5, 3, 5), 5);
        armas.add(new Escopeta(12, "Escopeta", "Descripcion", 2, new Point(), 6, 4, 5), 15);
        armas.add(new Pistola(13, "Pistola2", "Descripcion", 1, new Point(), 1, 3, 5), 2);
        armas.add(new Escopeta(14, "Escopeta", "Descripcion", 2, new Point(), 10, 4, 5), 22);
        armas.add(new Pistola(15, "Pistola2", "Descripcion", 1, new Point(), 10, 3, 5), 18);
        
        municiones = new Diccionario();
        municiones.add(new Municion(10, "Municion", "Pistola", 4, new Point(), 5, "Pistola"), 2);
        municiones.add(new Municion(11, "Municion", "Pistola", 4, new Point(), 5, "Pistola"), 2);
        municiones.add(new Municion(12, "Municion", "Pistola", 4, new Point(), 5, "Pistola"), 2);
        municiones.add(new Municion(13, "Municion", "Pistola", 4, new Point(), 5, "Pistola"), 2);
        municiones.add(new Municion(14, "Municion", "Pistola", 4, new Point(), 5, "Pistola"), 2);
        municiones.add(new Municion(15, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"), 3);
        municiones.add(new Municion(16, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"), 3);
        municiones.add(new Municion(17, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"), 3);
        municiones.add(new Municion(18, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"), 3);
        municiones.add(new Municion(19, "Municion", "Escopeta", 0, new Point(), 5, "Escopeta"), 3);
    }
    
    // --------------------------------------------------- CONSUMIBLES
    
    public int getPrecioConsumible(Consumible consumible) {
        return Integer.parseInt(consumibles.getValor(consumible).toString());
    }

    public Diccionario getConsumibles() {
        return consumibles;
    }

    public Consumible deleteConsumible(Consumible consumible, int cantidad) {
        NodoDiccionario nodoConsumible = consumibles.localize(consumible);
        Consumible consumibleTemp = ((Consumible) nodoConsumible.getClave()).get(cantidad);
        puntos += consumibleTemp.getCantidad() * Integer.parseInt(nodoConsumible.getValor().toString());
        if(((Consumible) nodoConsumible.getClave()).getCantidad() == 0)
            consumibles.delete(nodoConsumible.getClave());
        return consumibleTemp;
    }
    
    // --------------------------------------------------- ARMAS
    
    public int getPrecioArma(Arma arma) {
        return (int) armas.getValor(arma);
    }

    public Diccionario getArmas() {
        return armas;
    }
    
    public Arma deleteArma(Arma arma) {
        NodoDiccionario nodoArma = armas.localize(arma);
        puntos += (int) nodoArma.getValor();
        armas.delete(nodoArma.getClave());
        return (Arma) nodoArma.getClave();
    }
    
    // --------------------------------------------------- MUNICIONES
    
    public int getPrecioMunicion(Municion municion) {
        return (int) municiones.getValor(municion);
    }

    public Diccionario getMuniciones() {
        return municiones;
    }
    
    public Municion deleteMunicion(Municion municion) {
        NodoDiccionario nodoMunicion = municiones.localize(municion);
        puntos += (int) nodoMunicion.getValor();
        municiones.delete(nodoMunicion.getClave());
        return (Municion) nodoMunicion.getClave();
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
                + getVidaActual()
                + consumibles.toString("", " > ")
                + armas.toString("", " > ")
                + municiones.toString("", " > ");
    }
    
}
