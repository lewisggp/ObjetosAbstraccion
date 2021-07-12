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
public class Lista {

    private NodoLista primero; //primer elemento de la lista

    public Lista() {
        primero = null;
    }

    protected NodoLista createNodo(Object obj) {
        return new NodoLista(obj);
    }

    // ---------------------------------------------------- Agregar
    protected void addFirst(NodoLista n) {
        if (isEmpty()) {
            primero = n;
        } else {
            n.setNext(primero);
            primero = n;
        }
    }

    protected void addFirst(Object obj) {
        addFirst(createNodo(obj));
    }

    protected void addLast(NodoLista n) {
        if (isEmpty()) {
            primero = n;
        } else {
            NodoLista x = primero;
            while (x.getNext() != null) {
                x = x.getNext();
            }
            x.setNext(n);
        }
    }

    protected void addLast(Object obj) {
        addLast(createNodo(obj));
    }

    private void add(NodoLista n) {
        addLast(n);
    }

    public void add(Object obj) {
        addLast(createNodo(obj));
    }

    protected void add(int index, NodoLista n) {
        NodoLista x = primero;
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
        add(createNodo(obj));
    }

    // ---------------------------------------------------- Eliminar
    public Object delete(Object obj) {
        NodoLista x = localize(obj);
        if (x != null) {
            if (x.equals(primero)) { //si el nodo a eliminar es el primero. Caso 1
                primero = primero.getNext();
            } else if (x.getNext() == null) {// si el nodo a eliminar es el último. Caso 2
                NodoLista y = primero;
                while (!y.getNext().equals(x)) {
                    y = y.getNext();
                }
                y.setNext(null);
            } else {//si el nodo a eliminar esta en medio de dos nodos. Caso 3
                NodoLista y = primero;
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

    // ---------------------------------------------------- Obtener
    public NodoLista getFirstNodo() {
        if (!isEmpty()) {
            return primero;
        }
        return null;
    }

    public NodoLista getNodo(int index) {
        NodoLista nodo = primero;
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
        NodoLista nodo = getNodo(index);
        return nodo != null ? nodo.get() : null;
    }

    // ---------------------------------------------------- Utiles
    public NodoLista localize(Object obj) {
        NodoLista x = primero;
        while (x != null) {
            if (x.getObject() == obj) {
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
        NodoLista nodo = primero;
        int cont = 0;
        while (nodo != null) {
            cont++;
            nodo = nodo.getNext();
        }
        return cont;
    }

    public int indexOf(Object obj) {
        NodoLista nodo = primero;
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

    @Override
    public String toString() {
        String str = "[";
        NodoLista x = getFirstNodo();
        if(x != null) {
            str += x.toString();
            x = x.getNext();
        }
        while(x != null) {
            str += ", " + x.toString();
            x = x.getNext();
        }
        str += "]";
        return str;
    }
    
}
