/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego.Modelo;

import Juego.Controlador.Cargador;
import javax.sound.sampled.Clip;

/**
 *
 * @author Lewis
 */
public class Sonido {
   
    private final Clip sonido;
    private final float segundosReproduccion;
    private long ultimaReproduccion;

    public Sonido(final String ruta) {
        sonido = Cargador.sonido(ruta);
        segundosReproduccion = obtenerDuracion();
    }
    
    public Sonido(final String ruta, float delay) {
        sonido = Cargador.sonido(ruta);
        segundosReproduccion = obtenerDuracion() + delay;
    }
    
    // --------------------------------------------------- GETTERS
    
    private float obtenerDuracion() {
        return (float) sonido.getMicrosecondLength() / 1000000;
    }

    public boolean terminado() {
        // ! sonido.isActive() &&
        return System.nanoTime() - ultimaReproduccion > segundosReproduccion * 1000000000;
    }
    
    // --------------------------------------------------- REPRODUCIR
    
    public void reproducir() {
        sonido.stop();
        sonido.flush();
        sonido.setMicrosecondPosition(0);
        sonido.start();
        ultimaReproduccion = System.nanoTime();
    }

    public void reproducirSino() {
        if( terminado() ) reproducir();
    }
    
    public void repetir() {
        sonido.stop();
        sonido.flush();
        sonido.setMicrosecondPosition(0);

        sonido.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
