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
public class Diccionario extends ListaSimple {

    public Diccionario() {
        super();
    }
    
    protected void addFirst(Object clave, Object valor) {
        super.addFirst(new NodoDiccionario(clave, valor));
    }

    protected void addLast(Object clave, Object valor) {
        super.addLast(new NodoDiccionario(clave, valor));
    }

    public void add(Object clave, Object valor) {
        super.addLast(new NodoDiccionario(clave, valor));
    }

    public void add(int index, Object clave, Object valor) {
        super.add(index, new NodoDiccionario(clave, valor));
    }

    public Object getValor(Object clave) {
        NodoDiccionario nodo = localize(clave);
        return (nodo != null) ? nodo.get() : null;
    }
    
    public Object setValor(Object clave, Object valor) {
        if(indexOf(clave) == -1) return null;
        NodoDiccionario nodo = localize(clave);
        Object old = nodo.getValor();
        nodo.setValor(valor);
        return old;
    }

    // ---------------------------------------------------- SOBREESCRITOS
    
    @Override
    public void add(Object valor) {
        super.addLast(new NodoDiccionario(null, valor));
    }

    @Override
    public void add(int index, Object valor) {
        super.add(index, new NodoDiccionario(null, valor));
    }
    
    @Override
    public Object delete(Object clave) {
        return super.delete(clave);
    }

    @Override
    public NodoDiccionario getFirstNodo() {
        return (NodoDiccionario) super.getFirstNodo();
    }

    @Override
    public NodoDiccionario getNodo(int index) {
        return (NodoDiccionario) super.getNodo(index);
    }

    @Override
    public NodoDiccionario localize(Object clave) {
        return (NodoDiccionario) super.localize(clave);
    }

    @Override
    public int indexOf(Object clave) {
        return super.indexOf(clave);
    }
    
    // ---------------------------------------------------- STRING
    
    @Override
    public String toString() {
        String str = "[";
        NodoDiccionario nodo = getFirstNodo();
        while(nodo != null) {
            str += nodo.getClave().toString() + " => " + nodo.getValor().toString();
            if(nodo.getNext() != null) str += ", ";
            nodo = nodo.getNext();
        }        
        return str + "]";
    }

    // ---------------------------------------------------- STRING PARA ARCHIVOS
    
    @Override
    public String toString(String nombre, String separador) {
        String str = nombre + "\n";
        if(size() == 0) return str;
        
        str += separador + "\n";
        NodoDiccionario nodo = getFirstNodo();
        while(nodo != null) {
            str += nodo.getClave().getClass().getSimpleName() + ": " + nodo.getClave() + " => " + nodo.getValor().getClass().getSimpleName() + ": " + nodo.getValor();
            if(nodo.getNext() != null) str += "\n" + separador + "\n";
            nodo = nodo.getNext();
        }
        return str;
    }

}
