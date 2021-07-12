package Juego.Modelo.Modificador;

import Juego.Controlador.Controlador;
import Juego.Modelo.Sprite;
import Juego.Modelo.Ente.Ente;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import java.awt.Point;
import java.awt.Rectangle;

public class Escopeta extends Arma {

    public Escopeta(int id, String nombre, String descripcion, Sprite sprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, sprite, posicion, alcance, ataqueMinimo, ataqueMaximo, true, 3);
    }
        
    public Escopeta(int id, String nombre, String descripcion, int idSprite, Point posicion, int alcance, int ataqueMinimo, int ataqueMaximo) {
        super(id, nombre, descripcion, idSprite, posicion, alcance, ataqueMinimo, ataqueMaximo, true, 3);
    }
    
    private Escopeta(String datoArma[]) {
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

    public Escopeta(String str) {
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

        int ancho = 4;
        int cantidad = alcance * Controlador.LADO_SPRITE / ancho;

        ListaSimple alcanceAtaques = new ListaSimple();

        if (ente.getDireccion() == 2 || ente.getDireccion() == 3) {
            // 2 = arriba, 3 = abajo
            // Ataque en Y
            if (ente.getDireccion() == 3) {
                for (int i = 0, c = 2; i < cantidad; i++) {
                    alcanceAtaques.add(new Rectangle(ente.getArea().x + ente.getArea().width / 2 - ancho / 2 - ancho * i,
                            ente.getArea().y + ente.getArea().height / 2 - ancho / 2 + ancho * i,
                            ancho * (i * 2 + 1),
                            ancho));
                    if (i > cantidad * 2 / 3) {
                        ((Rectangle) alcanceAtaques.get(i)).x = ((Rectangle) alcanceAtaques.get(i - c)).x;
                        ((Rectangle) alcanceAtaques.get(i)).width = ((Rectangle) alcanceAtaques.get(i - c)).width;
                        c += 2;
                    }
                }
            } else {
                for (int i = 0, c = 2; i < cantidad; i++) {
                    alcanceAtaques.add(new Rectangle(ente.getArea().x + ente.getArea().width / 2 - ancho / 2 - ancho * i,
                            ente.getArea().y + ente.getArea().height / 2 - ancho / 2 - ancho * i,
                            ancho * (i * 2 + 1),
                            ancho));
                    if (i > cantidad * 2 / 3) {
                        ((Rectangle) alcanceAtaques.get(i)).x = ((Rectangle) alcanceAtaques.get(i - c)).x;
                        ((Rectangle) alcanceAtaques.get(i)).width = ((Rectangle) alcanceAtaques.get(i - c)).width;
                        c += 2;
                    }
                }
            }
        } else if (ente.getDireccion() == 4 || ente.getDireccion() == 5) {
            // 4 = derecha, 35 = izquierda
            // Ataque en X
            if (ente.getDireccion() == 5) {
                for (int i = 0, c = 2; i < cantidad; i++) {
                    alcanceAtaques.add(new Rectangle(ente.getArea().x + ente.getArea().width / 2 - ancho / 2 - ancho * i,
                            ente.getArea().y + ente.getArea().height / 2 - ancho / 2 - ancho * i,
                            ancho,
                            ancho * (i * 2 + 1)));
                    if (i > cantidad * 2 / 3) {
                        ((Rectangle) alcanceAtaques.get(i)).y = ((Rectangle) alcanceAtaques.get(i - c)).y;
                        ((Rectangle) alcanceAtaques.get(i)).height = ((Rectangle) alcanceAtaques.get(i - c)).height;
                        c += 2;
                    }
                }
            } else {
                for (int i = 0, c = 2; i < cantidad; i++) {
                    alcanceAtaques.add(new Rectangle(ente.getArea().x + ente.getArea().width / 2 - ancho / 2 + ancho * i,
                            ente.getArea().y + ente.getArea().height / 2 - ancho / 2 - ancho * i,
                            ancho,
                            ancho * (i * 2 + 1)));
                    if (i > cantidad * 2 / 3) {
                        ((Rectangle) alcanceAtaques.get(i)).y = ((Rectangle) alcanceAtaques.get(i - c)).y;
                        ((Rectangle) alcanceAtaques.get(i)).height = ((Rectangle) alcanceAtaques.get(i - c)).height;
                        c += 2;
                    }
                }
            }
        }
        return alcanceAtaques;
    }

    @Override
    public int getPuntos(Ente ente, ListaSimple entes) {
        if( balasActual == 0 ) return 0;
        balasActual--;
        
        ListaSimple alcances = getAlcance(ente);
        int puntos = 0;
        NodoListaSimple nodoEnte = entes.getFirstNodo();
        while (nodoEnte != null) {
            Ente enteTemp = (Ente) nodoEnte.getObject();
            if (enteTemp.isVivo()) {
                NodoListaSimple nodoRectangulo = alcances.getFirstNodo();
                while (nodoRectangulo != null) {
                    if (enteTemp.getArea().intersects((Rectangle) nodoRectangulo.getObject())) {
                        quitarVida(enteTemp);
                        if (!enteTemp.isVivo()) {
                            puntos++; // Doble puntuacion
                        }
                        puntos++;
                    }
                    nodoRectangulo = nodoRectangulo.getNext();
                }
            }
            nodoEnte = nodoEnte.getNext();
        }

        return puntos;
    }

}
