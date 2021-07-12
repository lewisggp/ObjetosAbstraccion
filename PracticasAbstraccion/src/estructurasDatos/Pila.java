/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructurasDatos;

/**
 *
 * @author Lewis
 */
public class Pila {

    private NodoPila tope;
    //crear pila

    public Pila() {
        tope = null;
    }

    public String push(NodoPila n) {
        if (pilaVacia()) {
            tope = n;
            return "El nodo " + tope.getDato() + " es el primero ";
        } else {
            n.setSte(tope);
            tope = n;
            return "Ahora el nodo " + tope.getDato() + " es el primero ";
        }
    }

    public NodoPila pop() {
        if (!pilaVacia()) {
            NodoPila x = new NodoPila(tope.getDato());
            tope = tope.getSte();
            return x;
        }
        return null;
    }

    public boolean pilaVacia() {
        if (tope == null) {
            return true;
        }
        return false;
    }

    public void limpiarPila() {
        tope = null;
    }

    public NodoPila cimaPila() {
        return tope;
    }

    public int tamanoPila() {
        NodoPila nodo = tope;
        int cont = 1;
        while (nodo.getSte() != null) {
            cont++;
            nodo = nodo.getSte();
        }
        return cont;
    }

    public String toString() {
        String str = "[";
        NodoPila x = tope;
        if (x != null) {
            str += x.getDato();
            x = x.getSte();
        }
        while (x != null) {
            str += ", " + x.getDato();
            x = x.getSte();
        }
        str += "]";
        return str;
    }

}
