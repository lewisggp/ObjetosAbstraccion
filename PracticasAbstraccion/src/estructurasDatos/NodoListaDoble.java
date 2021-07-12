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
public class NodoListaDoble {

    private int dato;
    private NodoListaDoble ant, ste;

    public NodoListaDoble(int d) {
        dato = d;
        ant = null;
        ste = null;
    }

    public void setDato(int d) {
        dato = d;
    }

    public void setAnt(NodoListaDoble a) {
        ant = a;
    }

    public void setSte(NodoListaDoble s) {
        ste = s;
    }

    public int getDato() {
        return dato;
    }

    public NodoListaDoble getAnt() {
        return ant;
    }

    public NodoListaDoble getSte() {
        return ste;
    }
    
}
