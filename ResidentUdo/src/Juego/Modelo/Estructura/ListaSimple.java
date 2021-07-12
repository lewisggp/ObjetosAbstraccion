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
public class ListaSimple {

    private NodoListaSimple primero;

    public ListaSimple() {
        primero = null;
    }

    // ---------------------------------------------------- AGREGAR
    
    protected void addFirst(NodoListaSimple n) {
        if (isEmpty()) {
            primero = n;
        } else {
            n.setNext(primero);
            primero = n;
        }
    }

    protected void addFirst(Object obj) {
        addFirst(new NodoListaSimple(obj));
    }

    protected void addLast(NodoListaSimple n) {
        if (isEmpty()) {
            primero = n;
        } else {
            NodoListaSimple x = primero;
            while (x.getNext() != null) {
                x = x.getNext();
            }
            x.setNext(n);
        }
    }

    protected void addLast(Object obj) {
        addLast(new NodoListaSimple(obj));
    }

    private void add(NodoListaSimple n) {
        addLast(n);
    }

    public void add(Object obj) {
        addLast(new NodoListaSimple(obj));
    }

    protected void add(int index, NodoListaSimple n) {
        NodoListaSimple x = primero;
        if (index == 0) {
            addFirst(n);
        }
        int cont = 1;
        while (x != null) {
            if (cont == index) {
                n.setNext(x.getNext());
                x.setNext(n);
                return;
            }
            cont++;
            x = x.getNext();
        }
        if (cont == index) {
            addLast(n);
        }
    }

    public void add(int index, Object obj) {
        add(index, new NodoListaSimple(obj));
    }

    public void add(ListaSimple listaSimple) {
        NodoListaSimple nodo = listaSimple.getFirstNodo();
        while(nodo != null) {
            add(nodo.get());
            nodo = nodo.getNext();
        }
    }
    
    // ---------------------------------------------------- ELIMINAR
    
    public Object delete(Object obj) {
        NodoListaSimple x = localize(obj);
        if (x != null) {
            if (x.equals(primero)) { //si el nodo a eliminar es el primero. Caso 1
                primero = primero.getNext();
            } else if (x.getNext() == null) {// si el nodo a eliminar es el último. Caso 2
                NodoListaSimple y = primero;
                while (!y.getNext().equals(x)) {
                    y = y.getNext();
                }
                y.setNext(null);
            } else {//si el nodo a eliminar esta en medio de dos nodos. Caso 3
                NodoListaSimple y = primero;
                while (!(y.getNext().equals(x))) {
                    y = y.getNext();
                }
                y.setNext(x.getNext());
                x.setNext(null);
            }
            return x.get();
        }
        return null;
    }

    public Object deleteByIndex(int index) {
        Object deleted = get(index);
        return deleted != null ? delete(deleted) : null;
    }

    public void deleteAll() {
        primero = null;
    }

    // ---------------------------------------------------- OBTENER
    
    public NodoListaSimple getFirstNodo() {
        if (!isEmpty()) {
            return primero;
        }
        return null;
    }

    public NodoListaSimple getNodo(int index) {
        NodoListaSimple nodo = primero;
        int cont = 0;
        while (nodo != null) {
            if (cont == index) {
                return nodo;
            }
            cont++;
            nodo = nodo.getNext();
        }
        return null;
    }

    public Object get(int index) {
        NodoListaSimple nodo = getNodo(index);
        return nodo != null ? nodo.get() : null;
    }

    // ---------------------------------------------------- UTILES
    
    public NodoListaSimple localize(Object obj) {
        NodoListaSimple x = primero;
        while (x != null) {
            if (x.getObject().equals(obj)) {
                return x;
            }
            x = x.getNext();
        }
        return null;
    }

    public boolean isEmpty() {
        if (primero == null) {
            return true;
        }
        return false;
    }

    public int size() {
        NodoListaSimple nodo = primero;
        int cont = 0;
        while (nodo != null) {
            cont++;
            nodo = nodo.getNext();
        }
        return cont;
    }

    public int indexOf(Object obj) {
        NodoListaSimple nodo = primero;
        int cont = 0;
        while (nodo != null) {
            if (nodo.getObject().equals(obj)) {
                return cont;
            }
            cont++;
            nodo = nodo.getNext();
        }
        return -1;
    }
    
    // --------------------------------------------------- STRING
    
    @Override
    public String toString() {
        String str = "[";
        NodoListaSimple nodo = getFirstNodo();
        while(nodo != null) {
            str += nodo.get().toString();
            if(nodo.getNext() != null) str += ", ";
            nodo = nodo.getNext();
        }
        return str + "]";
    }
    
    // --------------------------------------------------- STRING PARA ARCHIVOS
    
    public String toString(String nombre, String separador) {
        String str = nombre + "\n";
        if(size() == 0) return str;
        
        str += separador + "\n";
        NodoListaSimple nodo = getFirstNodo();
        while(nodo != null) {
            str += nodo.get().getClass().getSimpleName() + ": " + nodo.get().toString();
            if(nodo.getNext() != null) str += "\n" + separador + "\n";
            nodo = nodo.getNext();
        }
        return str;
    }
    
}
