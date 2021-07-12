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
public class NodoListaSimple {

    private Object object;
    private NodoListaSimple next;

    public NodoListaSimple(Object object) {
        this.object = object;
        next = null;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }

    public NodoListaSimple getNext() {
        return next;
    }

    public void setNext(NodoListaSimple next) {
        this.next = next;
    }

    public Object get() {
        return object;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
