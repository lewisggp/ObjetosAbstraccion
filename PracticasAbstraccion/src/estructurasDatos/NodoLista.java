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
public class NodoLista {

    private Object object;
    private NodoLista next; //la autoreferencia

    public NodoLista(Object object) {
        this.object = object;
        next = null;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }

    public NodoLista getNext() {
        return next;
    }

    public void setNext(NodoLista next) {
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
