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
public class NodoListaSimple {

    private int dato;
    private NodoListaSimple ste; //la autoreferencia

    public NodoListaSimple(int d) {
        dato = d;
        ste = null;
    }

    public void setDato(int d) {
        dato = d;
    }

    public void setSte(NodoListaSimple s) {
        ste = s;
    }

    public int getDato() {
        return dato;
    }

    public NodoListaSimple getSte() {
        return ste;
    }

}
