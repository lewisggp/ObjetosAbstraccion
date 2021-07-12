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
public class NodoCola {

    private int dato;
    private NodoCola ste; //la autoreferencia

    public NodoCola(int d) {
        dato = d;
        ste = null;
    }

    public void setDato(int d) {
        dato = d;
    }

    public void setSte(NodoCola e) {
        ste = e;
    }

    public int getDato() {
        return dato;
    }

    public NodoCola getSte() {
        return ste;
    }

}
