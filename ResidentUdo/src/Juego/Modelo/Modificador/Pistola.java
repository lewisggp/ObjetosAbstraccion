package Juego.Modelo.Modificador;

import Juego.Controlador.Controlador;
import Juego.Modelo.Sprite;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import java.awt.Point;
import java.awt.Rectangle;

public class Pistola extends Arma {

    public Pistola(int id, String nombre, String descripcion, Sprite sprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, sprite, posicion, alcance, ataqueMinimo, ataqueMaximo, false, 5);
    }
    
    public Pistola(int id, String nombre, String descripcion, int idSprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, idSprite, posicion, alcance, ataqueMinimo, ataqueMaximo, false, 5);
    }
    
    private Pistola(String datoArma[]) {
        this(Integer.parseInt(datoArma[0]),
                            datoArma[1],
                            datoArma[2],
                            Integer.parseInt(datoArma[3]),
                            new Point(Integer.parseInt(datoArma[4]),
                                    Integer.parseInt(datoArma[5])),
                            Integer.parseInt(datoArma[6]),
                            Integer.parseInt(datoArma[7]),
                            Integer.parseInt(datoArma[8]));
    }

    public Pistola(String str) {
        this(str.split(", "));
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    public String toString() {
        return getId() + ", " 
                + getNombre() + ", " 
                + getDescripcion() + ", " 
                + getSprite().getId() + ", " 
                + getPosicion().x + ", " 
                + getPosicion().y + ", " 
                + getAlcance()+ ", "
                + getAtaqueMinimo() + ", "
                + getAtaqueMaximo();
    }
    
    @Override
    public ListaSimple getAlcance(Ente ente) {
        Rectangle alcanceAtaque = new Rectangle();
        if (ente.getDireccion() == 2 || ente.getDireccion() == 3) {
            // 2 = arriba, 3 = abajo
            // Ataque en Y
            alcanceAtaque.width = 4;
            alcanceAtaque.height = alcance * Controlador.LADO_SPRITE;
            alcanceAtaque.x = ente.getArea().x + ente.getArea().width / 2 - alcanceAtaque.width / 2;
            if (ente.getDireccion() == 3) {
                alcanceAtaque.y = ente.getArea().y + ente.getArea().height / 2;
            } else {
                alcanceAtaque.y = ente.getArea().y + ente.getArea().height / 2 - alcanceAtaque.height;
            }
        } else if (ente.getDireccion() == 4 || ente.getDireccion() == 5) {
            // 4 = derecha, 5 = izquierda
            // Ataque en X
            alcanceAtaque.width = alcance * Controlador.LADO_SPRITE;
            alcanceAtaque.height = 4;
            alcanceAtaque.y = ente.getArea().y + ente.getArea().height / 2 - alcanceAtaque.height / 2;
            if (ente.getDireccion() == 5) {
                alcanceAtaque.x = ente.getArea().x + ente.getArea().width / 2 - alcanceAtaque.width;
            } else {
                alcanceAtaque.x = ente.getArea().x + ente.getArea().width / 2;
            }
        }
        ListaSimple rectangulos = new ListaSimple();
        rectangulos.add(alcanceAtaque);
        return rectangulos;
    }

    @Override
    public int getPuntos(Ente ente, ListaSimple entes) {
        if( balasActual == 0 ) return 0;
        balasActual--;
        
        ListaSimple alcances = getAlcance(ente);
        double distanciaMasCercana = -1;
        Ente enteMasCercano = null;
        NodoListaSimple nodoEnte = entes.getFirstNodo();
        while (nodoEnte != null) {
            Ente enteTemp = (Ente) nodoEnte.getObject();
            if (enteTemp.isVivo()) {
                NodoListaSimple nodoRectangulo = alcances.getFirstNodo();
                while (nodoRectangulo != null) {
                    if (enteTemp.getArea().intersects((Rectangle) nodoRectangulo.getObject())) {
                        double distanciaTemp = Math.sqrt(Math.pow(enteTemp.getPosicion().x - ente.getPosicion().x, 2) + Math.pow(enteTemp.getPosicion().y - ente.getPosicion().y, 2));
                        if (distanciaMasCercana == -1 || distanciaTemp < distanciaMasCercana) {
                            distanciaMasCercana = distanciaTemp;
                            enteMasCercano = enteTemp;
                        }
                    }
                    nodoRectangulo = nodoRectangulo.getNext();
                }
            }
            nodoEnte = nodoEnte.getNext();
        }

        if (enteMasCercano != null) {
            quitarVida(enteMasCercano);
            if (!enteMasCercano.isVivo()) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

}
