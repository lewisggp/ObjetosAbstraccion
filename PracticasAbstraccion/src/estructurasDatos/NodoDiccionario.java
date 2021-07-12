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
public class NodoDiccionario extends NodoLista {
    
    private Object valor;
    
    public NodoDiccionario(Object clave, Object valor) {
        super(clave);
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    public Object getClave() {
        return super.getObject();
    }
    
    public void setClave(Object clave) {
        super.setObject(clave);
    }
    
    @Override
    public void setObject(Object clave) {
        super.setObject(clave);
    }
    
    @Override
    public NodoDiccionario getNext() {
        return (NodoDiccionario) super.getNext();
    }
    
    @Override
    public Object get() {
        return valor;
    }
    
    @Override
    public String toString() {
        return super.toString() + "-" + valor.toString();
    }
    
}
