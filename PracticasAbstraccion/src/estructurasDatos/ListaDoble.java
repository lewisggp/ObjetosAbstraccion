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
public class ListaDoble {

    private NodoListaDoble primero;

    public ListaDoble() {
        primero = null;
    }

    public boolean esVacia() {
        if (primero == null) {
            return true;
        }
        return false;
    }

    public void insertarPrimero(NodoListaDoble n) {
        if (esVacia()) {
            primero = n;
        } else {
            n.setSte(primero);
            primero.setAnt(n);
            primero = n;
        }
    }

    public void insertarFinal(NodoListaDoble n) {
        if (esVacia()) {
            primero = n;
        } else {
            NodoListaDoble x = primero;
            while (x.getSte() != null) {
                x = x.getSte();
            }
            x.setSte(n);
            n.setAnt(x);
        }
    }

    public void insertarMedio(NodoListaDoble despuesDe, NodoListaDoble n) {
        if (!esVacia()) {
            NodoListaDoble x = localizar(despuesDe.getDato());
            if (x.getSte() == null) {
                x.setSte(n);
                n.setAnt(x);
            } else if (x != null) {
                n.setSte(x.getSte());
                x.getSte().setAnt(n);
                x.setSte(n);
                n.setAnt(x);
            }
        }
    }

    public NodoListaDoble localizar(int d) {
        NodoListaDoble x = primero;
        if (!esVacia()) {
            while (x != null) {
                if (x.getDato() == d) {
                    return x;
                }
                x = x.getSte();
            }
        }
        return null;
    }

    public NodoListaDoble suprimir(int d) {
        NodoListaDoble x = localizar(d);
        NodoListaDoble y;
        if (x != null) {
            if (x.equals(primero)) {
                y = new NodoListaDoble(x.getDato());
                primero = primero.getSte();
                primero.setAnt(null);
            } else if (x.getSte() == null) {
                y = primero;
                while (!y.getSte().equals(x)) {
                    y = y.getSte();
                }
                y.getSte().setAnt(null);
                y.setSte(null);
            } else {
                y = primero;
                while (!(y.getSte().equals(x))) {
                    y = y.getSte();
                }
                y.setSte(x.getSte());
                x.getSte().setAnt(y);
                x.setAnt(null);
                x.setSte(null);
            }
            return x;
        }
        return null;
    }

    public NodoListaDoble primero() {
        if (!esVacia()) {
            return primero;
        }
        return null;
    }

    public void anula() {
        primero = null;
    }

    public String toString() {
        String str = "[";
        NodoListaDoble x = primero;
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
