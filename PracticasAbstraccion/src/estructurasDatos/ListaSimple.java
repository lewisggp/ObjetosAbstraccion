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
public class ListaSimple {

    private NodoListaSimple primero; //primer elemento de la lista

    public ListaSimple() {
        primero = null;
    }

    public boolean esVacia() {
        if (primero == null) {
            return true;
        }
        return false;
    }

    public void insertarPrimero(NodoListaSimple n) {
        if (esVacia()) {
            primero = n;
        } else {
            n.setSte(primero);
            primero = n;
        }
    }

    public void insertarFinal(NodoListaSimple n) {
        if (esVacia()) {
            primero = n;
        } else {
            NodoListaSimple x = primero;
            while (x.getSte() != null) {
                x = x.getSte();
            }
            x.setSte(n);
        }
    }

    public void insertarMedio(NodoListaSimple despuesDe, NodoListaSimple n) {
        if (!esVacia()) { //inserta en nodo n, después del nodo despuesDe
            NodoListaSimple x = localizar(despuesDe.getDato());
            if (x != null) {
                n.setSte(x.getSte());
                x.setSte(n);
            }
        }
    }

    public NodoListaSimple suprimir(int d) {
        NodoListaSimple x = localizar(d);
        NodoListaSimple y;
        if (x != null) {
            if (x.equals(primero)) { //si el nodo a eliminar es el primero. Caso 1
                y = new NodoListaSimple(x.getDato());
                primero = primero.getSte();
            } else if (x.getSte() == null) {// si el nodo a eliminar es el último. Caso 2
                y = primero;
                while (!y.getSte().equals(x)) {
                    y = y.getSte();
                }
                y.setSte(null);
            } else {//si el nodo a eliminar esta en medio de dos nodos. Caso 3
                y = primero;
                while (!(y.getSte().equals(x))) {
                    y = y.getSte();
                }
                y.setSte(x.getSte());
                x.setSte(null);
            }
            return x;
        }
        return null;
    }

    public NodoListaSimple primero() {
        if (!esVacia()) {
            return primero;
        }
        return null;
    }

    public void anula() {
        primero = null;
    }

    public NodoListaSimple localizar(int d) {
        NodoListaSimple x = primero;
        while (x != null) {
            if (x.getDato() == d) {
                return x;
            }
            x = x.getSte();
        }
        return null;
    }

    public String toString() {
        String str = "[";
        NodoListaSimple x = primero;
        if(x != null) {
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
