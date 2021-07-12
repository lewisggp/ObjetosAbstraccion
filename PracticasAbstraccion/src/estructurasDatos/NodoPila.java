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
public class NodoPila {

    private int dato; // para este ejemplo un entero
    private NodoPila ste; //la autoreferencia

    public NodoPila(int d) {
        dato = d;
        ste = null;
    }

    public void setDato(int d) {
        dato = d;
    }

    public int getDato() {
        return dato;
    }

    public void setSte(NodoPila n) {
        ste = n;
    }

    public NodoPila getSte() {
        return ste;
    }
    
}
