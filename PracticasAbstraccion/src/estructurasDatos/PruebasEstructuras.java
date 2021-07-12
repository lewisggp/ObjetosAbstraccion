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
public class PruebasEstructuras {

    public static void main(String[] args) {
        System.out.println("\nProbando Colas");
        probarCola();
        System.out.println("\nProbando Pilas");
        probarPila();
        System.out.println("\nProbando Listas Simples");
        probarListaSimple();
        System.out.println("\nProbando Listas Dobles");
        probarListaDoble();
    }

    public static void probarListaDoble() {
        ListaDoble ld = new ListaDoble();
        ld.insertarFinal(new NodoListaDoble(4));
        System.out.println(ld);
        ld.insertarPrimero(new NodoListaDoble(1));
        System.out.println(ld);
        ld.insertarMedio(ld.localizar(1), new NodoListaDoble(2));
        System.out.println(ld);
        ld.insertarMedio(ld.localizar(4), new NodoListaDoble(6));
        System.out.println(ld);
        ld.insertarMedio(ld.localizar(2), new NodoListaDoble(3));
        System.out.println(ld);
        ld.insertarFinal(new NodoListaDoble(8));
        System.out.println(ld);
    }

    public static void probarListaSimple() {
        ListaSimple ls = new ListaSimple();
        ls.insertarFinal(new NodoListaSimple(4));
        System.out.println(ls);
        ls.insertarPrimero(new NodoListaSimple(1));
        System.out.println(ls);
        ls.insertarMedio(ls.localizar(1), new NodoListaSimple(2));
        System.out.println(ls);
        ls.insertarMedio(ls.localizar(4), new NodoListaSimple(6));
        System.out.println(ls);
        ls.insertarMedio(ls.localizar(2), new NodoListaSimple(3));
        System.out.println(ls);
        ls.insertarFinal(new NodoListaSimple(8));
        System.out.println(ls);
    }

    public static void probarPila() {
        Pila pl = new Pila();
        System.out.println(pl.push(new NodoPila(5)));
        System.out.println(pl);
        System.out.println(pl.push(new NodoPila(6)));
        System.out.println(pl);
        System.out.println("Cima: " + pl.cimaPila().getDato());
        System.out.println(pl.push(new NodoPila(3)));
        System.out.println(pl.push(new NodoPila(2)));
        System.out.println(pl);
        System.out.println("Quito: " + pl.pop().getDato());
        System.out.println(pl);
    }

    public static void probarCola() {
        Cola cl = new Cola();
        cl.insertar(new NodoCola(5));
        System.out.println(cl);
        cl.insertar(new NodoCola(3));
        System.out.println(cl);
        cl.insertar(new NodoCola(6));
        System.out.println(cl);
        cl.insertar(new NodoCola(4));
        System.out.println(cl);
        System.out.println("Retiro: " + cl.retirar().getDato());
        System.out.println(cl);
        System.out.println("Retiro: " + cl.retirar().getDato());
        System.out.println(cl);
    }

}
