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
public class NodoListaDoble extends NodoListaSimple {
    
    private NodoListaDoble previous;
    
    public NodoListaDoble(Object object) {
        super(object);
        previous = null;
    }

    public NodoListaDoble getPrevious() {
        return previous;
    }

    public void setPrevious(NodoListaDoble previous) {
        this.previous = previous;
    }
    
    @Override
    public NodoListaDoble getNext() {
        return (NodoListaDoble) super.getNext();
    }
    
}
