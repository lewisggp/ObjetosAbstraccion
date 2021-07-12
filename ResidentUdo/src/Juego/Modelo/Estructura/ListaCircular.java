/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo.Estructura;

/**
 *
 * @author Lewis
 */
public class ListaCircular {

    private NodoListaDoble primero;
    private NodoListaDoble ultimo;

    public ListaCircular() {
        primero = null;
        ultimo = null;
    }
    
    // --------------------------------------------------- AGREGAR
    
    public void add(NodoListaDoble nuevo) {
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
            primero.setNext(ultimo);
            primero.setPrevious(ultimo);
            ultimo.setPrevious(primero);
            ultimo.setNext(primero);
        } else {
            NodoListaDoble temporal = ultimo;
            ultimo = nuevo;
            temporal.setNext(ultimo);
            ultimo.setPrevious(temporal);
            ultimo.setNext(primero);
            primero.setPrevious(ultimo);
            //System.out.println("Agregado " + nuevo.get() + " despues de " + nuevo.getPrevious().get() + " y antes de " + nuevo.getNext().get());
        }
    }

    public void add(Object obj) {
        ListaCircular.this.add(new NodoListaDoble(obj));
    }

    // --------------------------------------------------- OBTENER
    
    public NodoListaDoble getFirstNodo() {
        return primero;
    }
    
    public NodoListaDoble getLastNodo() {
        return ultimo;
    }

    // --------------------------------------------------- UTILES
    
    public int size() {
        NodoListaSimple nodo = primero;
        int count = 0;
        if (nodo != null) {
            do {
                count++;
                nodo = nodo.getNext();
            } while (nodo != null && nodo != primero);
        }
        return count;
    }

    public boolean isEmpty() {
        return this.ultimo == null && this.primero == null;
    }

    public boolean contains(Object obj) {
        NodoListaSimple nodo = primero;
        if (nodo != null) {
            do {
                if(nodo.getObject().equals(obj)) {
                    return true;
                }
                nodo = nodo.getNext();
            } while (nodo != null && nodo != primero);
        }
        return false;
    }    
        
    public void girarNext() {
        primero = primero.getNext();
        ultimo = ultimo.getNext();
    }
    
    public void girarPrevious() {
        primero = primero.getPrevious();
        ultimo = ultimo.getPrevious();
    }
    
    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        String str = "]";
        
        NodoListaSimple nodoDisponible = getFirstNodo();
        if (nodoDisponible != null) {
            do {
                str += nodoDisponible.get().toString();
                if(nodoDisponible.getNext() != null && nodoDisponible.getNext() != getFirstNodo()) str += ", ";
                nodoDisponible = nodoDisponible.getNext();
            } while (nodoDisponible != null && nodoDisponible != getFirstNodo());
        }
        return str + "]";
    }
    
    // --------------------------------------------------- STRING PARA ARCHIVOS
    
    public String toString(String nombre, String separador) {
        String str = nombre + "\n";
        if(size() == 0) return str;
        
        str += separador + "\n";
        NodoListaSimple nodoDisponible = getFirstNodo();
        if (nodoDisponible != null) {
            do {
                str += nodoDisponible.get().getClass().getSimpleName() + ": " + nodoDisponible.get();
                if(nodoDisponible.getNext() != null && nodoDisponible.getNext() != getFirstNodo()) str += "\n" + separador + "\n";
                nodoDisponible = nodoDisponible.getNext();
            } while (nodoDisponible != null && nodoDisponible != getFirstNodo());
        }
        return str;
    }
    
}
