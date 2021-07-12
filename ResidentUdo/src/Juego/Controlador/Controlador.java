package Juego.Controlador;

import Juego.Modelo.Raton;
import Juego.Modelo.Estructura.Dijkstra;
import Juego.Modelo.Ente.Enemigo;
import Juego.Modelo.Modificador.Arma;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import Juego.Modelo.Ente.Jugador;
import Juego.Modelo.Ente.Vendedor;
import Juego.Modelo.Estructura.Diccionario;
import Juego.Modelo.Estructura.ListaSimple;
import Juego.Modelo.Estructura.NodoDiccionario;
import Juego.Modelo.Estructura.NodoListaSimple;
import Juego.Modelo.Modificador.Consumible;
import Juego.Modelo.Modificador.Desarmado;
import Juego.Modelo.Municion;
import Juego.Modelo.Objeto;
import Juego.Modelo.Sonido;
import Juego.Modelo.Teletransportador;
import java.awt.Point;

public class Controlador {

    public final static byte LADO_SPRITE = 32;
    public final static byte SONIDO_REPETIR = 0;
    public final static byte SONIDO_REPRODUCIR = 1;
    public final static byte SONIDO_REPRODUCIR_SINO = 2;
    public final static byte MUNICION_INVALIDA = -1;
    public final static byte MUNICION_VACIA = 0;
    public final static byte RECARGA_EXISTOSA = 1;
    public final static byte ARMA_FULL_MUNICION = 2;
    public final static byte RECARGA_FALLIDA = 3;
    public final static byte ARMA_YA_PORTADA = 0;
    public final static byte ARMA_CAMBIADA = 1;
    public final static byte OBJETO_CONSUMIDO = 1;
    public final static byte CONSUMIBLE_NO_ENCONTRADO = 0;
    public final static byte CONSUMIBLE_VACIO = -1;
    public final static byte COMPRA_EXITOSA = 1;
    public final static byte COMPRA_EXITOSA_ULTIMA = 2;
    
    private Jugador jugador;
    private Inventario inventario;
    private ListaSimple mapas;
    private Mapa mapa;
    private Dijkstra dijkstra;
    private Teclado teclado;
    private Raton raton;
    private Diccionario sonidos;
    private Diccionario atajos;

    private int contador;
    private int contadorDia;
    private boolean deDia;    

    public Controlador(String usuario, String jugadorSeleccionado, int puntosUsuario) {
        mapas = new ListaSimple();
        mapas.add(new Mapa(0, "UDO"));
        mapa = (Mapa) mapas.get(0);
        inventario = new Inventario();
        jugador = new Jugador(1,
                usuario,
                "El salvador",
                jugadorSeleccionado,
                puntosUsuario,
                mapa.getPosicionInicial(),
                100,
                1,
                (Arma) inventario.getArma());

        teclado = new Teclado();
        raton = new Raton();
        dijkstra = new Dijkstra(jugador.getArea(), mapa.getTerrenoColisionable());
        contador = 0;
        contadorDia = 0;
        deDia = true;
        atajos = new Diccionario();
        
        sonidos = new Diccionario();
        sonidos.add("Camina", new Sonido("/sonidos/Camina.wav", 0.2f));
        sonidos.add("Comer", new Sonido("/sonidos/Comer.wav"));
        sonidos.add("Muere", new Sonido("/sonidos/Muere.wav"));
        sonidos.add("GameOver", new Sonido("/sonidos/Camina.wav"));
        sonidos.add("Desarmado", new Sonido("/sonidos/Desarmado.wav"));
        sonidos.add("Pistola", new Sonido("/sonidos/Pistola.wav"));
        sonidos.add("Recargar Pistola", new Sonido("/sonidos/Pistola - Recarga.wav"));
        sonidos.add("Escopeta", new Sonido("/sonidos/Escopeta.wav"));
        sonidos.add("Recargar Escopeta", new Sonido("/sonidos/Escopeta - Recarga.wav"));
        sonidos.add("Zombie", new Sonido("/sonidos/Zombie.wav"));
        sonidos.add("Zombie Camina", new Sonido("/sonidos/Zombie - Camina.wav"));
        sonidos.add("Zombie Ataca", new Sonido("/sonidos/Zombie - Ataca.wav"));
        sonidos.add("Zombie Muere", new Sonido("/sonidos/Zombie - Muere.wav"));
        sonidos.add("Bosque", new Sonido("/sonidos/Bosque.wav"));
        sonidos.add("Bolso", new Sonido("/sonidos/Bolso.wav"));
        sonidos.add("Caja registradora", new Sonido("/sonidos/Caja registradora.wav"));
        
        reproducirSonido("Bosque", SONIDO_REPETIR);
    }

    public void actualizar() {
        contador++;
        
        // Actualizar cada segundo
        if (contador % 64 == 0) { // Cada segundo. Velocidad de FPS = 64

            contadorDia++;
            // Actualizar ataque el enemigo3
            int perderVida = mapa.getAtaqueEnemigo(jugador.getArea());
            if(perderVida != 0)
                reproducirSonido("Zombie Ataca", SONIDO_REPRODUCIR_SINO);
            jugador.perderVida(perderVida);
            // Devuelve 0 si no intersecta con enemigo

            // Mostrar en consola el debug
            if (teclado.isDebug()) {
                //System.out.println(jugador.toString());
                //System.out.println(inventario.toString());
                //System.out.println(mapa.toString());
                //System.out.println(teclado.toString());
            }
        }

        // Cambiar la paleta de Sprite cada 2 minutos
        if (contadorDia == 120) {
            deDia = !deDia;
            //mapa.changePaletaSprites();
            contadorDia = 0;
        }

        // Actualizar dijkstra
        dijkstra.actualizar(jugador.getArea(), mapa.getTerrenoColisionable());

        // Mover enemigos
        moverEnemigos();
        eliminarEnemigosMuertos();

        // Dikjstra debug
        if (teclado.isDebug()) {
            //System.out.println(dijkstra.toString());
        }

        // Animacion de los vendedores
        if(contador % 8 == 0) {
            NodoListaSimple vendedor = mapa.getVendedores().getFirstNodo();
            while (vendedor != null) {
                ((Vendedor) vendedor.getObject()).changeSprite();
                vendedor = vendedor.getNext();
            }
        }

        if (jugador.isVivo()) {
            // Atajo
            if (teclado.getAtajoPulsado() != ' ') {
                ejecutarAtajo(teclado.getAtajoPulsado());
                teclado.setAtajoPulsado(' ');
            }
            teclado.getAtajo().tooglePulsada();
            
            // Recargar
            if (teclado.getRecargando().isPulsada()) {
                recargar();
            }
            
            // Actualizar Resistencia
            if (!teclado.isCorriendo()) {
                jugador.aumentarResistencia();
            }

            // Actualizar Recogida
            if (teclado.isRecogiendo()) {
                recogerMapa();
            }

            // Actualizar Ataques
            if (teclado.getAtacando().isPulsada() && jugador.getArma().getBalasActual() != 0 && ( jugador.getArma() instanceof Desarmado || ((Sonido) sonidos.getValor("Recargar " + jugador.getArma().getClass().getSimpleName())).terminado())) {
                atacar();
            }

            // Actualizar movimiento y direccion
            if (teclado.getArriba().isPulsada() || teclado.getDerecha().isPulsada() || teclado.getAbajo().isPulsada() || teclado.getIzquierda().isPulsada()) {
                jugador.setEnMovimiento(true);
                if (!jugador.isAnimacionEspecial()) {
                    moverJugador();
                }
            } else {
                jugador.setEnMovimiento(false);
            }
            
        }

        if (contador % 8 == 0) {
            jugador.changeSprite();
        }
        
        if (!jugador.isVivo() && jugador.getHojaActual() != 10) {
            jugador.changeHoja(10);
            reproducirSonido("Muere", SONIDO_REPRODUCIR);
        }
    }
    
    // ----------------------------------------- GETTERS
    
    public Jugador getJugador() {
        return jugador;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    public Raton getRaton() {
        return raton;
    }

    public Dijkstra getDijkstra() {
        return dijkstra;
    }

    public Vendedor getVendedor() {
        return mapa.getVendedor(jugador.getArea());
    }

    public ListaSimple getMapas() {
        return mapas;
    }
    
    public Diccionario getAtajos() {
        return atajos;
    }
    
    public boolean gameOver() {
        return !jugador.isVivo() && !jugador.isAnimacionEspecial();
    }
    
    public boolean isDeDia() {
        return deDia;
    }
    
    private Objeto getObjeto(Point posicion, ListaSimple lista) {
        NodoListaSimple nodo = lista.getFirstNodo();
        while(nodo != null) {
            Objeto objeto = (Objeto) nodo.get();
            Rectangle areaTemp = objeto.getArea();
            if(posicion.x >= areaTemp.x 
            && posicion.y >= areaTemp.y 
            && posicion.x <= areaTemp.x + areaTemp.width 
            && posicion.y <= areaTemp.y + areaTemp.height)
                return objeto;
            nodo = nodo.getNext();
        }
        return null;
    }
    
    public Objeto getObjeto(Point posicion) {
        Objeto objeto = getObjeto(posicion, mapa.getConsumibles());
        if(objeto == null) objeto = getObjeto(posicion, mapa.getArmas());
        if(objeto == null) objeto = getObjeto(posicion, mapa.getMuniciones());
        if(objeto == null) objeto = getObjeto(posicion, mapa.getEnemigos());
        if(objeto == null) objeto = getObjeto(posicion, mapa.getVendedores());
        if(objeto == null) objeto = getObjeto(posicion, mapa.getTeletransportadores());
        return objeto;
    }
    
    // ----------------------------------------- SETTERS
    
    public void setMapa(String nombre) {        
        NodoListaSimple nodoMapa = mapas.getFirstNodo();
        while (nodoMapa != null) {
            Mapa mapaTemp = (Mapa) nodoMapa.getObject();
            if (nombre.equalsIgnoreCase(mapaTemp.getNombre())) {
                mapa = mapaTemp;
                return;
            } else {
                nodoMapa = nodoMapa.getNext();
            }
        }

        Mapa mapaTemp = new Mapa(mapas.size(), nombre);
        mapas.add(mapaTemp);
        mapa = mapaTemp;
        
        try {
            mapa.setInformacion(Cargador.texto("/registros/usuarios/" + jugador.getNombre() + "/mapa" + nombre + ".txt").split("-sig-"));
        } catch(Exception e) {
            System.out.println("Datos del jugador no conseguido " + e);
        }
    }

    public void setAtajos(Diccionario atajos) {
        this.atajos = new Diccionario();
        NodoDiccionario nodo = atajos.getFirstNodo();
        while(nodo != null) {
            if(nodo.getValor() instanceof Consumible) registrarAtajo(nodo.getClave().toString(), (Objeto) nodo.getValor());
            else if(nodo.getValor() instanceof Arma) registrarAtajo(nodo.getClave().toString(), (Objeto) nodo.getValor());
            else if(nodo.getValor() instanceof Municion) registrarAtajo(nodo.getClave().toString(), (Objeto) nodo.getValor());
            nodo = nodo.getNext();
        }
    }
    
    public void registrarAtajo(String tecla, Objeto objeto) {
        int numero = Integer.parseInt(tecla);
        if(numero < 0 || numero > 9) return;
        if(atajos.setValor(numero, objeto) == null) atajos.add(new Integer(numero), objeto);
    }
    
    // --------------------------------------------------- METODOS DE ACTUALIZAR
    
    private boolean eliminarEnemigosMuertos() {
        int muertos = 0;
        NodoListaSimple nodo = mapa.getEnemigos().getFirstNodo();
        while (nodo != null) {
            Enemigo enemigoTemp = (Enemigo) nodo.getObject();
            if (!enemigoTemp.isVivo()) {
                if (enemigoTemp.getHojaActual() != 10) {
                    mapa.agregarObjetos(enemigoTemp.getObjetos(), enemigoTemp.getPosicion());
                    enemigoTemp.changeHoja(10);
                } else if (!enemigoTemp.isAnimacionEspecial()) {
                    mapa.getEnemigos().delete(enemigoTemp);
                }
            }
            nodo = nodo.getNext();
        }
        return muertos != 0;
    }

    private void moverJugador() {
        reproducirSonido("Camina", SONIDO_REPRODUCIR_SINO);
        
        // Establecer resistencia
        if (teclado.isCorriendo() && jugador.getResistencia() > 0) {
            jugador.setVelocidad(2);
            jugador.setRecuperado(false);
            jugador.disminuirResistencia();
        } else {
            jugador.setVelocidad(1);
            teclado.setCorriendo(false);
        }

        // Movimiento en X
        int movimientoX = 0;
        if (teclado.getIzquierda().isPulsada() && !teclado.getDerecha().isPulsada()) {
            movimientoX = -1;
        } else if (!teclado.getIzquierda().isPulsada() && teclado.getDerecha().isPulsada()) {
            movimientoX = 1;
        }

        // Movimiento en Y
        int movimientoY = 0;
        if (teclado.getArriba().isPulsada() && !teclado.getAbajo().isPulsada()) {
            movimientoY = -1;
        } else if (!teclado.getArriba().isPulsada() && teclado.getAbajo().isPulsada()) {
            movimientoY = 1;
        }

        // Reseteando diagonales
        // izquierda y arriba
        if (movimientoX == -1 && movimientoY == -1) {
            if (teclado.getIzquierda().getUltimaPulsacion() > teclado.getArriba().getUltimaPulsacion()) {
                movimientoY = 0;
            } else {
                movimientoX = 0;
            }
        }

        // izquierda y abajo
        if (movimientoX == -1 && movimientoY == 1) {
            if (teclado.getIzquierda().getUltimaPulsacion() > teclado.getAbajo().getUltimaPulsacion()) {
                movimientoY = 0;
            } else {
                movimientoX = 0;
            }
        }

        // derecha y arriba
        if (movimientoX == 1 && movimientoY == -1) {
            if (teclado.getDerecha().getUltimaPulsacion() > teclado.getArriba().getUltimaPulsacion()) {
                movimientoY = 0;
            } else {
                movimientoX = 0;
            }
        }

        // derecha y arriba
        if (movimientoX == 1 && movimientoY == 1) {
            if (teclado.getDerecha().getUltimaPulsacion() > teclado.getAbajo().getUltimaPulsacion()) {
                movimientoY = 0;
            } else {
                movimientoX = 0;
            }
        }

        jugador.changeDireccion(movimientoX, movimientoY);

        // Posicion futura del jugador
        int posicionFuturaX = jugador.getArea().x + movimientoX * jugador.getVelocidad();
        int posicionFuturaY = jugador.getArea().y + movimientoY * jugador.getVelocidad();

        // Teletransportar
        Teletransportador teletransportador = mapa.getTeletransportador(new Rectangle(posicionFuturaX, posicionFuturaY, jugador.getArea().width, jugador.getArea().height));
        if (teletransportador != null) {
            if (teletransportador.getMapaFinal() != null && !teletransportador.getMapaFinal().equalsIgnoreCase(mapa.getNombre())) {
                setMapa(teletransportador.getMapaFinal());
                teletransportador.teletransportar(jugador);
            }
        }

        // Mover
        if (posicionFuturaX >= 0 && posicionFuturaY >= 0 && posicionFuturaX + jugador.getArea().width < mapa.getAncho() * Mapa.LADO_SPRITE && posicionFuturaY + jugador.getArea().height < mapa.getAlto() * Mapa.LADO_SPRITE) {
            Rectangle areaFutura = new Rectangle(posicionFuturaX, posicionFuturaY, jugador.getArea().width, jugador.getArea().height);
            if (!mapa.enColisionConTerreno(areaFutura)) {
                jugador.mover(movimientoX, movimientoY);
            }
        }
    }

    private void moverEnemigos() {
        NodoListaSimple nodoEnemigo = mapa.getEnemigos().getFirstNodo();
        while (nodoEnemigo != null) {
            Enemigo enemigo = (Enemigo) nodoEnemigo.getObject();
            if(contador % 8 == 0) enemigo.changeSprite();
            if (enemigo.isVivo()) {
                Rectangle areaMinimaJugador = new Rectangle(jugador.getArea().x + 1, jugador.getArea().y + 1, jugador.getArea().width - 2, jugador.getArea().height - 2);
                //g.setColor(Color.RED);
                //g.drawRect(referenciaX + areaMinimaJugador.x, referenciaY + areaMinimaJugador.y, areaMinimaJugador.width, areaMinimaJugador.height);
                Point movimiento = dijkstra.obtenerSiguienteMovimiento(enemigo.getArea(), true);

                enemigo.setEnMovimiento(false);
                Rectangle posicionFutura = new Rectangle(enemigo.getArea().x + movimiento.x * enemigo.getVelocidad(), enemigo.getArea().y + movimiento.y * enemigo.getVelocidad(), enemigo.getArea().width, enemigo.getArea().height);

                if (!enemigo.getArea().intersects(jugador.getArea())) {
                    if (movimiento.x != 0 || movimiento.y != 0) {
                        enemigo.changeDireccion(movimiento.x, movimiento.y);
                        if (!posicionFutura.intersects(areaMinimaJugador) && !mapa.enColisionConOtroEnemigo(posicionFutura, enemigo)) {
                            reproducirSonido("Zombie", SONIDO_REPRODUCIR_SINO);
                            reproducirSonido("Zombie Camina", SONIDO_REPRODUCIR_SINO);
                            if (!mapa.enColisionConTerreno(posicionFutura)) {
                                enemigo.setEnMovimiento(true);
                                enemigo.mover(movimiento.x, movimiento.y);
                            } else {
                                movimiento = dijkstra.obtenerSiguienteMovimiento(enemigo.getArea(), false);
                                posicionFutura = new Rectangle(enemigo.getArea().x + movimiento.x * enemigo.getVelocidad(), enemigo.getArea().y + movimiento.y * enemigo.getVelocidad(), enemigo.getArea().width, enemigo.getArea().height);
                                if (!mapa.enColisionConTerreno(posicionFutura) && !posicionFutura.intersects(areaMinimaJugador) && !mapa.enColisionConOtroEnemigo(posicionFutura, enemigo)) {
                                    enemigo.setEnMovimiento(true);
                                    enemigo.mover(movimiento.x, movimiento.y);
                                }
                            }
                        }
                    }
                } else if (!enemigo.isAnimacionEspecial()) {
                    enemigo.changeHoja(4 + enemigo.getDireccion());
                }
            }
            nodoEnemigo = nodoEnemigo.getNext();
        }
    }

    private void recogerMapa() {
        Consumible objetoConsumible = mapa.getConsumible(jugador.getArea());
        if (objetoConsumible != null) {
            inventario.agregarConsumible(objetoConsumible);
        }
        Arma arma = mapa.getArma(jugador.getArea());
        if (arma != null) {
            inventario.agregarArma(arma);
        }
        Municion municion = mapa.getMunicion(jugador.getArea());
        if (municion != null) {
            municion.spriteGrande();
            inventario.agregarMunicion(municion);
        }
        teclado.setRecogiendo(false);
    }

    private void atacar() {
        int puntos = jugador.getArma().getPuntos(jugador, mapa.getEnemigos());
        if (puntos != 0) {
            jugador.addPuntos(puntos);
            reproducirSonido("Zombie Muere", SONIDO_REPRODUCIR_SINO);
        }
        reproducirSonido(jugador.getArma().getClass().getSimpleName(), SONIDO_REPRODUCIR);
        teclado.getAtacando().tooglePulsada();
        jugador.changeHoja(4 + jugador.getDireccion());
    }
    
    private void ejecutarAtajo(char tecla) {
        if( ! Character.isDigit(tecla) ) return;
        if(atajos.isEmpty()) return;
                
        Object objeto = atajos.getValor(Integer.parseInt(tecla + ""));
        if(objeto == null) return;
        
        if(objeto instanceof Consumible) consumir((Consumible) objeto, 1);
        else if(objeto instanceof Arma) seleccionarArma((Arma) objeto);
        else if(objeto instanceof Municion) recargar((Municion) objeto);
    }
       
    // --------------------------------------------------- SOLICITUDES
    
    private int recargar() {
        int balasNecesarias = jugador.getArma().getBalasMaximo() - jugador.getArma().getBalasActual();        
        if(balasNecesarias == 0) return ARMA_FULL_MUNICION;
                
        Municion municionTemp = inventario.getMunicion(jugador.getArma().getClass().getSimpleName());
        if(municionTemp != null) {
            int balasDisponibles = municionTemp.getBalas(balasNecesarias);
            jugador.getArma().recargar(balasDisponibles);
            if(municionTemp.getBalas() == 0) {
                inventario.getMuniciones().delete(municionTemp);
                NodoDiccionario nodoAtajo = atajos.getFirstNodo();
                while(nodoAtajo != null){
                    if(nodoAtajo.get() instanceof Municion && ((Municion) nodoAtajo.get()).getId() == municionTemp.getId()) {
                        NodoDiccionario nodoSiguiente = nodoAtajo.getNext();
                        atajos.delete(nodoAtajo.getClave());
                        nodoAtajo = nodoSiguiente;
                    } else {
                        nodoAtajo = nodoAtajo.getNext();
                    }
                }
            }            
            if( ! (jugador.getArma() instanceof Desarmado))
                reproducirSonido("Recargar " + jugador.getArma().getClass().getSimpleName(), SONIDO_REPRODUCIR_SINO);
            return RECARGA_EXISTOSA;
        }
        
        return RECARGA_FALLIDA;
    }
    
    public int recargar(Municion municion) {
        if( ! jugador.getArma().getClass().getSimpleName().equalsIgnoreCase(municion.getTipo())) return MUNICION_INVALIDA;
        if( municion.getBalas() == 0 ) return MUNICION_VACIA;
                
        return recargar();
    }
    
    public int consumir(Consumible consumible, int cantidad) {
        if(consumible.getCantidad() == 0) return CONSUMIBLE_VACIO;
        jugador.ganarVida(consumible.getAumentaVida() * cantidad);
        reproducirSonido("Comer", SONIDO_REPRODUCIR_SINO);
        NodoListaSimple nodoConsumible = inventario.getConsumibles().getFirstNodo();
        while (nodoConsumible != null) {
            Consumible consumibleTemp = (Consumible) nodoConsumible.getObject();
            if (consumibleTemp.getId() == consumible.getId()) {
                consumibleTemp.disminuirCantidad(cantidad);
                if (consumibleTemp.getCantidad() == 0) {
                    inventario.getConsumibles().delete(consumibleTemp);
                    
                    NodoDiccionario nodoAtajo = atajos.getFirstNodo();
                    while(nodoAtajo != null){
                        if(nodoAtajo.get() instanceof Consumible && ((Consumible) nodoAtajo.get()).getId() == consumible.getId()) {
                            NodoDiccionario nodoSiguiente = nodoAtajo.getNext();
                            atajos.delete(nodoAtajo.getClave());
                            nodoAtajo = nodoSiguiente;
                        } else {
                            nodoAtajo = nodoAtajo.getNext();
                        }
                    }
                }
                return OBJETO_CONSUMIDO;
            }
            nodoConsumible = nodoConsumible.getNext();
        }
        return CONSUMIBLE_NO_ENCONTRADO;
    }

    public int seleccionarArma(Arma arma) {
        if(arma.getId() == jugador.getArma().getId()) return ARMA_YA_PORTADA;
        inventario.setArma(arma);
        jugador.setArma(inventario.getArma());
        reproducirSonido("Bolso", Controlador.SONIDO_REPRODUCIR_SINO);
        return ARMA_CAMBIADA;
    }

    public int comprarArma(Vendedor vendedor, Arma armaSeleccionada) {
        reproducirSonido("Caja registradora", SONIDO_REPRODUCIR);
        getJugador().removePuntos(vendedor.getPrecioArma(armaSeleccionada));
        Arma arma = vendedor.deleteArma(armaSeleccionada);
        getInventario().agregarArma(arma);
        return COMPRA_EXITOSA;
    }

    public int comprarMunicion(Vendedor vendedor, Municion municionSeleccionado) {
        reproducirSonido("Caja registradora", SONIDO_REPRODUCIR);
        getJugador().removePuntos(vendedor.getPrecioMunicion(municionSeleccionado));
        Municion municion = vendedor.deleteMunicion(municionSeleccionado);        
        getInventario().agregarMunicion(municion);
        return COMPRA_EXITOSA;
    }

    public int comprarConsumible(Vendedor vendedor, Consumible objetoSeleccionado) {
        reproducirSonido("Caja registradora", SONIDO_REPRODUCIR);
        getJugador().removePuntos(vendedor.getPrecioConsumible(objetoSeleccionado));
        boolean ultimo = objetoSeleccionado.getCantidad() == 1;
        Consumible consumible = vendedor.deleteConsumible(objetoSeleccionado, 1);
        getInventario().agregarConsumible(consumible);
        return (ultimo) ? COMPRA_EXITOSA_ULTIMA : COMPRA_EXITOSA;
    }

    public void reproducirSonido(String clave, int opcion) {
        Sonido sonido = (Sonido) sonidos.getValor(clave);
        if(sonido == null) return;
        
        switch(opcion) {
            case SONIDO_REPETIR:
                sonido.repetir();
                break;
            case SONIDO_REPRODUCIR_SINO:
                sonido.reproducirSino();
                break;
            case SONIDO_REPRODUCIR:
            default:
                sonido.reproducir();
                break;
        }
    }
    
    // --------------------------------------------------- TECLADO
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (!teclado.isComprando() && !teclado.isInventarioActivo()) {
                    teclado.getArriba().teclaPulsada();
                }
                break;
            case KeyEvent.VK_S:
                if (!teclado.isComprando() && !teclado.isInventarioActivo()) {
                    teclado.getAbajo().teclaPulsada();
                }
                break;
            case KeyEvent.VK_D:
                if (!teclado.isComprando() && !teclado.isInventarioActivo()) {
                    teclado.getDerecha().teclaPulsada();
                }
                break;
            case KeyEvent.VK_A:
                if (!teclado.isComprando() && !teclado.isInventarioActivo()) {
                    teclado.getIzquierda().teclaPulsada();
                }
                break;
            case KeyEvent.VK_F1:
                teclado.setDebug(!teclado.isDebug());
                break;
            case KeyEvent.VK_B:
                if (!teclado.isComprando()) {
                    Vendedor vendedor = getVendedor();
                    if (vendedor != null) {
                        teclado.setComprando(true);
                        teclado.setInventarioActivo(false);
                    }
                } else {
                    teclado.setComprando(false);
                }
                break;
            case KeyEvent.VK_I:
                teclado.setInventarioActivo(!teclado.isInventarioActivo());
                teclado.setComprando(false);
                break;
            case KeyEvent.VK_SHIFT:
                teclado.setCorriendo(true);
                break;
            case KeyEvent.VK_E:
                teclado.setRecogiendo(true);
                break;
            case KeyEvent.VK_SPACE:
                if (!teclado.isComprando() && !teclado.isInventarioActivo()) {
                    teclado.getAtacando().tooglePulsada();
                }
                break;
            case KeyEvent.VK_R:
                teclado.getRecargando().tooglePulsada();
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_4:
            case KeyEvent.VK_5:
            case KeyEvent.VK_6:
            case KeyEvent.VK_7:
            case KeyEvent.VK_8:
            case KeyEvent.VK_9:
            case KeyEvent.VK_0:
                teclado.getAtajo().tooglePulsada();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                teclado.getArriba().teclaLiberada();
                break;
            case KeyEvent.VK_S:
                teclado.getAbajo().teclaLiberada();
                break;
            case KeyEvent.VK_D:
                teclado.getDerecha().teclaLiberada();
                break;
            case KeyEvent.VK_A:
                teclado.getIzquierda().teclaLiberada();
                break;
            case KeyEvent.VK_SHIFT:
                teclado.setCorriendo(false);
                break;
            case KeyEvent.VK_E:
                teclado.setRecogiendo(false);
                break;
            case KeyEvent.VK_SPACE:
                teclado.getAtacando().teclaLiberada();
                break;
            case KeyEvent.VK_R:
                teclado.getRecargando().teclaLiberada();
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
            case KeyEvent.VK_4:
            case KeyEvent.VK_5:
            case KeyEvent.VK_6:
            case KeyEvent.VK_7:
            case KeyEvent.VK_8:
            case KeyEvent.VK_9:
            case KeyEvent.VK_0:
                teclado.getAtajo().teclaLiberada();
                teclado.setAtajoPulsado(e.getKeyChar());
                break;
        }
    }

    // --------------------------------------------------- RATON
    
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            raton.setClick(true);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            raton.setClickDerecho(true);
        }
        raton.setPosicion(new Point(e.getX(), e.getY()));
    }

    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            raton.setClick(false);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            raton.setClickDerecho(false);
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            raton.setClick(false);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            raton.setClickDerecho(false);
        }
    }

}
