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
public class Cola {

    private NodoCola frente, fin;

    public Cola() {
        frente = null;
        fin = null;
    }

    public void insertar(NodoCola nuevo) {
        if (colaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSte(new NodoCola(nuevo.getDato()));
            fin = fin.getSte();
        }
    }

    public NodoCola retirar() {
        if (colaVacia()) {
            return null;
        } else {
            NodoCola x = new NodoCola(frente.getDato());
            frente = frente.getSte();
            return x;
        }
    }

    public boolean colaVacia() {
        if (frente == null) {
            return true;
        }
        return false;
    }

    public NodoCola Frente() {
        if (colaVacia()) {
            return null;
        }
        return frente;
    }

    public int tamañoCola() {
        NodoCola x = frente;
        int cont = 1;
        if (!colaVacia()) {
            while (x != fin) {
                cont++;
                x = x.getSte();
            }
            return cont;
        }
        return -1;
    }

    public String toString() {
        String str = "[";
        NodoCola x = frente;
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
