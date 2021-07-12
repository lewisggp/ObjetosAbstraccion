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
public class Diccionario extends Lista {

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
        return localize(clave).get();
    }

    // ---------------------------------------------------- Sobreescritos
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

}
