package Juego.Modelo.Modificador;

import Juego.Controlador.Controlador;
import Juego.Modelo.Sprite;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import java.awt.Point;
import java.awt.Rectangle;

public class Desarmado extends Arma {

    public Desarmado(int id, String nombre, String descripcion, Sprite sprite, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, sprite, new Point(0, 0), alcance, ataqueMinimo, ataqueMaximo, false, 1);
    }
    
    public Desarmado(int id, String nombre, String descripcion, int idSprite, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, idSprite, new Point(0, 0), alcance, ataqueMinimo, ataqueMaximo, false, 1);
    }
    
    private Desarmado(String datoArma[]) {
        this(Integer.parseInt(datoArma[0]),
                            datoArma[1],
                            datoArma[2],
                            Integer.parseInt(datoArma[3]),
                            Integer.parseInt(datoArma[6]),
                            Integer.parseInt(datoArma[7]),
                            Integer.parseInt(datoArma[8]));
    }

    public Desarmado(String str) {
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
        Rectangle rectangulo = new Rectangle();
        if (ente.getDireccion() == 2 || ente.getDireccion() == 3) {
            // 2 = arriba, 3 = abajo
            // Ataque en Y
            rectangulo.width = 4;
            rectangulo.height = alcance * Controlador.LADO_SPRITE;
            rectangulo.x = ente.getArea().x + ente.getArea().width / 2 - rectangulo.width / 2;
            if (ente.getDireccion() == 3) {
                rectangulo.y = ente.getArea().y + ente.getArea().height / 2;
            } else {
                rectangulo.y = ente.getArea().y + ente.getArea().height / 2 - rectangulo.height;
            }
        } else if (ente.getDireccion() == 4 || ente.getDireccion() == 5) {
            // 4 = derecha, 5 = izquierda
            // Ataque en X
            rectangulo.width = alcance * Controlador.LADO_SPRITE;
            rectangulo.height = 4;
            rectangulo.y = ente.getArea().y + ente.getArea().height / 2 - rectangulo.height / 2;
            if (ente.getDireccion() == 5) {
                rectangulo.x = ente.getArea().x + ente.getArea().width / 2 - rectangulo.width;
            } else {
                rectangulo.x = ente.getArea().x + ente.getArea().width / 2;
            }
        }
        ListaSimple alcance = new ListaSimple();
        alcance.add(rectangulo);
        return alcance;
    }

    @Override
    public int getPuntos(Ente ente, ListaSimple entes) {
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
                return 3;
            }
            return 2;
        }
        return 0;
    }

}
