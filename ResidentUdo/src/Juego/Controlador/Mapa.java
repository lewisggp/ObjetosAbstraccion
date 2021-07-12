package Juego.Controlador;

import java.awt.Point;
import java.awt.Rectangle;

import Juego.Modelo.Sprite;
import Juego.Modelo.Ente.Enemigo;
import Juego.Modelo.Ente.Vendedor;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Arma;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Municion;
import Juego.Modelo.Objeto;
import Juego.Modelo.Teletransportador;

public class Mapa {

    public static final byte LADO_SPRITE = 16;
    private static final HojaSprites PALETA_SPRITES = new HojaSprites("/tile/Tiles.png", LADO_SPRITE, true);
    
    private final int id;
    private final String nombre;
    private int ancho;
    private int alto;
    private Point posicionInicial;

    private int[][][] terreno;
    private final ListaSimple terrenoColisionable;

    private ListaSimple colisiones;
    private ListaSimple consumibles;
    private ListaSimple armas;
    private ListaSimple municiones;
    private ListaSimple enemigos;
    private ListaSimple vendedores;
    private ListaSimple teletransportadores;
    
    public Mapa(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        terrenoColisionable = new ListaSimple();
        colisiones = new ListaSimple();
        consumibles = new ListaSimple();
        armas = new ListaSimple();
        enemigos = new ListaSimple();
        vendedores = new ListaSimple();
        teletransportadores = new ListaSimple();
        municiones = new ListaSimple();        

        setInformacion(Cargador.texto("/mapas/mapa" + nombre + ".txt").split("-sig-"));
    }
    
    // --------------------------------------------------- Getters
    
    public static Sprite getSprite(int id) {
        return PALETA_SPRITES.getSprite(id);
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getIdTerreno(int c, int x, int y) {
        return terreno[c][y][x];
    }
    
    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Point getPosicionInicial() {
        return posicionInicial;
    }

    public ListaSimple getTerrenoColisionable() {
        return terrenoColisionable;
    }
    
    public ListaSimple getColisiones() {
        return colisiones;
    }

    public ListaSimple getConsumibles() {
        return consumibles;
    }

    public ListaSimple getArmas() {
        return armas;
    }

    public ListaSimple getEnemigos() {
        return enemigos;
    }

    public ListaSimple getVendedores() {
        return vendedores;
    }

    public ListaSimple getTeletransportadores() {
        return teletransportadores;
    }
    
    public ListaSimple getMuniciones() {
        return municiones;
    }
    
    // --------------------------------------------------- GETTERS EN COLISION
    
    public Consumible getConsumible(Rectangle area) {
        NodoListaSimple nodo = consumibles.getFirstNodo();
        while (nodo != null) {
            Consumible consumibleTemp = (Consumible) nodo.getObject();
            if (consumibleTemp.getArea().intersects(area)) {
                return (Consumible) consumibles.delete(consumibleTemp);
            }
            nodo = nodo.getNext();
        }
        return null;
    }

    public Arma getArma(Rectangle area) {
        NodoListaSimple nodo = armas.getFirstNodo();
        while (nodo != null) {
            Arma armaTemp = (Arma) nodo.getObject();
            if (armaTemp.getArea().intersects(area)) {
                return (Arma) armas.delete(armaTemp);
            }
            nodo = nodo.getNext();
        }
        return null;
    }

    public Vendedor getVendedor(Rectangle area) {
        NodoListaSimple nodo = vendedores.getFirstNodo();
        while (nodo != null) {
            Vendedor vendedorTemp = (Vendedor) nodo.getObject();
            if (vendedorTemp.getArea().intersects(area)) {
                return vendedorTemp;
            }
            nodo = nodo.getNext();
        }
        return null;
    }

    public Teletransportador getTeletransportador(Rectangle area) {
        NodoListaSimple nodo = teletransportadores.getFirstNodo();
        while (nodo != null) {
            Teletransportador teletransportadorTemp = (Teletransportador) nodo.getObject();
            if (teletransportadorTemp.getArea().intersects(area)) {
                return teletransportadorTemp;
            }
            nodo = nodo.getNext();
        }
        return null;
    }

    public Municion getMunicion(Rectangle area) {
        NodoListaSimple nodo = municiones.getFirstNodo();
        while (nodo != null) {
            Municion municionTemp = (Municion) nodo.getObject();
            if (municionTemp.getArea().intersects(area)) {
                return (Municion) municiones.delete(municionTemp);
            }
            nodo = nodo.getNext();
        }
        return null;
    }
        
    public int getAtaqueEnemigo(Rectangle area) {
        int puntos = 0;
        NodoListaSimple nodo = enemigos.getFirstNodo();
        while (nodo != null) {
            Enemigo enemigoTemp = (Enemigo) nodo.getObject();
            if (enemigoTemp.isVivo() && enemigoTemp.getArea().intersects(area)) {
                puntos += enemigoTemp.getAtaque();
            }
            nodo = nodo.getNext();
        }
        return puntos;
    }

    public boolean enColisionConOtroEnemigo(Rectangle area, Enemigo elEnemigo) {
        NodoListaSimple nodo = enemigos.getFirstNodo();
        while (nodo != null) {
            Enemigo enemigoTemp = (Enemigo) nodo.getObject();
            if (enemigoTemp != elEnemigo && area.intersects(enemigoTemp.getArea())) {
                return true;
            }
            nodo = nodo.getNext();
        }
        return false;
    }
    
    public boolean enColisionConTerreno(Rectangle area) {
        NodoListaSimple listaNodoTemp = terrenoColisionable.getFirstNodo();
        while (listaNodoTemp != null) {
            Rectangle rectangulo = (Rectangle) listaNodoTemp.getObject();
            if (rectangulo.intersects(area)) {
                return true;
            }
            listaNodoTemp = listaNodoTemp.getNext();
        }
        
        listaNodoTemp = colisiones.getFirstNodo();
        while (listaNodoTemp != null) {
            Rectangle rectangulo = (Rectangle) listaNodoTemp.get();
            if (rectangulo.intersects(area)) {
                return true;
            }
            listaNodoTemp = listaNodoTemp.getNext();
        }
        return false;
    }

    // --------------------------------------------------- SETTERS    
    
    private void setDimension(String[] str) {
        String[] d = str[1].split(", ");
        this.ancho = Integer.parseInt(d[0]);
        this.alto = Integer.parseInt(d[1]);
    }

    private void setCapasTerreno(String[] str) {
        terreno = new int[str.length - 1][][];
        for (int c = 1; c < str.length; c++) {
            String[] strs = str[c].split(", ");
            terreno[c - 1] = new int[alto][];
            for (int i = 0, j = 0, k = 0; i < strs.length; i++) {
                if (i % ancho == 0) {
                    terreno[c - 1][j] = new int[ancho];
                    terreno[c - 1][j][k] = Integer.parseInt(strs[i]) - 1;
                } else {
                    terreno[c - 1][j][k] = Integer.parseInt(strs[i]) - 1;
                }
                switch (terreno[c - 1][j][k]) {
                    case 129: // autobus 3x2
                    case 132:
                    case 135:
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE + 15, 3 * LADO_SPRITE, 9));
                        break;
                    case 169: // carro 2x2
                    case 172:
                    case 175:
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE + 15, 2 * LADO_SPRITE, 9));
                        break;
                    case 171: // carro 1x2
                    case 174:
                    case 177:
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE + 8, 1 * LADO_SPRITE, 1 * LADO_SPRITE));
                        break;
                    case 138: // arbol 2x3
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE + 15, 2 * LADO_SPRITE, 2 * LADO_SPRITE - 7));
                        break;
                    case 127: // mata 1x1
                    case 128:
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE, LADO_SPRITE, LADO_SPRITE));
                        break;
                    case 198: // mata 1x0.5
                    case 215: // piedra
                    case 69: // cerca o rejas
                    case 70:
                    case 71:
                    case 90:
                    case 109:
                    case 110:
                    case 111:
                    case 92: // muro
                    case 93:
                    case 94:
                    case 95:
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE + 8, 1 * LADO_SPRITE, 1 * LADO_SPRITE - 8));
                        break;
                    case 89: // Cerca o Reja Izquierda
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE, j * LADO_SPRITE, 4, 1 * LADO_SPRITE + 4));
                        break;
                    case 91: // Cerca o reja Derecha
                        terrenoColisionable.add(new Rectangle(k * LADO_SPRITE + 12, j * LADO_SPRITE, 4, 1 * LADO_SPRITE + 4));
                        break;
                }
                k++;
                if (i % ancho == ancho - 1) {
                    j++;
                    k = 0;
                }
            }
        }
    }
    
    public void setInformacion(String[] info) {
        for (int i = 1; i < info.length; i++) {
            String[] infoTemp = info[i].split("-");
            switch (infoTemp[0].toUpperCase()) {
                case "PALETA":
                    //setPaleta(infoTemp);
                    break;
                case "DIMENSION":
                    setDimension(infoTemp);
                    break;
                case "POSICION":
                    posicionInicial = (Point) Cargador.toObjeto(infoTemp[1]);
                    break;
                case "CAPAS":
                    setCapasTerreno(infoTemp);
                    break;
                case "COLISIONES":
                    colisiones = Cargador.toLista(infoTemp);
                    break;
                case "CONSUMIBLES":
                    consumibles = Cargador.toLista(infoTemp);
                    break;
                case "ARMAS":
                    armas = Cargador.toLista(infoTemp);
                    break;
                case "ENEMIGOS":
                    enemigos = Cargador.toLista(infoTemp);
                    break;
                case "VENDEDORES":
                    vendedores = Cargador.toLista(infoTemp);
                    break;
                case "TELETRANSPORTADORES":
                    teletransportadores = Cargador.toLista(infoTemp);
                    break;
                case "MUNICIONES":
                    municiones = Cargador.toLista(infoTemp);
                    break;
            }
        }
    }

    // --------------------------------------------------- AGREGAR
        
    public void agregarObjetos(ListaSimple objetos, Point posicion) {
        NodoListaSimple nodo = objetos.getFirstNodo();
        while(nodo != null) {
            if(nodo.get() instanceof Objeto) {
                Objeto objetoTemp = (Objeto) nodo.get();
                objetoTemp.getArea().setLocation(posicion.x + (int) (Math.random() * objetoTemp.getArea().width / 2),
                                                posicion.y + (int) (Math.random() * objetoTemp.getArea().height / 2));
                if(objetoTemp instanceof Consumible) {
                    consumibles.add((Consumible) objetoTemp);
                }
                else if(objetoTemp instanceof Arma) {
                    armas.add((Arma) objetoTemp);
                }
                else if(objetoTemp instanceof Municion) {
                    ((Municion) objetoTemp).spritePequeno();
                    municiones.add((Municion) objetoTemp);
                }
            }
            nodo = nodo.getNext();
        }
    }
    
    // --------------------------------------------------- ABSTRACTOS SOBREESCRITOS
    
    @Override
    public String toString() {
        String str = "mapa" + getNombre() + "\n";
        str += "-sig-\n";
        str += "POSICION\n-\nPoint: " + getPosicionInicial().x + ", " + getPosicionInicial().y;
        
        str += "\n-sig-\n" + getConsumibles().toString("CONSUMIBLES", "-");
        str += "\n-sig-\n" + getArmas().toString("ARMAS", "-");
        str += "\n-sig-\n" + getEnemigos().toString("ENEMIGOS", "-");
        str += "\n-sig-\n" + getVendedores().toString("VENDEDORES", "-");
        str += "\n-sig-\n" + getMuniciones().toString("MUNICIONES", "-");
        
        return str;
    }
    
}
