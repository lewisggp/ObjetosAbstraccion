package Juego.Controlador;

import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Desarmado;
import Juego.Modelo.Modificador.Arma;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Municion;

public class Inventario {

    private Arma arma;
    private ListaSimple armas;
    private ListaSimple consumibles;
    private ListaSimple municiones;

    public Inventario() {
        arma = new Desarmado(0, "Desarmado", "Jugador sin armas", 0, 1, 1, 2);
        armas = new ListaSimple();
        armas.add(arma);
        consumibles = new ListaSimple();
        municiones = new ListaSimple();
    }

    // --------------------------------------------------- GETTERS
    
    public ListaSimple getArmas() {
        return armas;
    }

    public ListaSimple getConsumibles() {
        return consumibles;
    }
    
    public ListaSimple getMuniciones() {
        return municiones;
    }
    
    public Arma getArma() {
        return arma;
    }
    
    public Arma getArma(int id) {
        NodoListaSimple nodo = armas.getFirstNodo();
        while(nodo != null) {
            Arma temp = (Arma) nodo.get();
            if(temp.getId() == id) {
                return temp;
            }
            nodo = nodo.getNext();
        }
        return null;
    }
    
    public Municion getMunicion(String tipo) {
        NodoListaSimple municion = municiones.getFirstNodo();
        Municion m = null;
        while(municion != null) {
            Municion temp = (Municion) municion.get();
            if( temp.getTipo().equalsIgnoreCase(tipo) && ( m == null || temp.getBalas() < m.getBalas() ) ) {
                m = temp;
            }
            municion = municion.getNext();
        }
        return m;
    }
    
    // --------------------------------------------------- SETTERS
    
    public void setArmas(ListaSimple armas) {
        this.armas = armas;
        arma = (Arma) armas.get(0);
    }
    
    public void setConsumibles(ListaSimple consumibles) {
        this.consumibles = consumibles;
    }
    
    public void setMuniciones(ListaSimple municiones) {
        this.municiones = municiones;
    }
    
    public void setArma(Arma arma) {
        this.arma = arma;
    }
    
    // --------------------------------------------------- AGREGAR SI NO EXISTE

    public void agregarArma(Arma arma) {
        boolean encontrado = false;
        NodoListaSimple nodoArma = armas.getFirstNodo();
        while (nodoArma != null && ! encontrado) {
            Arma armaTemp = (Arma) nodoArma.getObject();
            if (armaTemp.getId() == arma.getId()) {
                encontrado = true;
                break;
            }
            nodoArma = nodoArma.getNext();
        }
        if (!encontrado) {
            armas.add(arma);
        }
    }
    
    public void agregarConsumible(Consumible consumible) {
        boolean encontrado = false;
        NodoListaSimple nodoConsumible = consumibles.getFirstNodo();
        while (nodoConsumible != null && ! encontrado) {
            Consumible consumibleTemp = (Consumible) nodoConsumible.getObject();
            if (consumibleTemp.getId() == consumible.getId()) {
                consumibleTemp.aumentarCantidad(consumible.getCantidad());
                encontrado = true;
            }
            nodoConsumible = nodoConsumible.getNext();
        }
        if (!encontrado) {
            consumibles.add(consumible);
        }
    }
        
    public void agregarMunicion(Municion municion) {
        boolean encontrado = false;
        NodoListaSimple nodoMunicion = municiones.getFirstNodo();
        while (nodoMunicion != null && ! encontrado) {
            Municion municionTemp = (Municion) nodoMunicion.getObject();
            if (municionTemp.getTipo().equalsIgnoreCase(municion.getTipo())) {
                municionTemp.agregarMunicion(municion);
                encontrado = true;
            }
            nodoMunicion = nodoMunicion.getNext();
        }
        if (!encontrado) {
            municiones.add(municion);
        }
    }
    
    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        return "[Inventario - Arma: " + arma + ", Armas: " + armas.size() + ", Consumibles: " + consumibles.size() + ", Municiones " + municiones.size() + "]";
    }

}
