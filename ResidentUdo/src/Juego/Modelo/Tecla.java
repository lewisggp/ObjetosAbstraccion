package Juego.Modelo;

import Juego.Controlador.Teclado;

public class Tecla {

    private boolean pulsada;
    private boolean soltada;
    private long ultimaPulsacion;

    public Tecla() {
        pulsada = false;
        soltada = true;
        ultimaPulsacion = Teclado.getTime();
    }

    // --------------------------------------------------- GETTERS
    
    public boolean isPulsada() {
        return pulsada;
    }

    public long getUltimaPulsacion() {
        return ultimaPulsacion;
    }
    
    // --------------------------------------------------- SETTERS
    
    public void teclaPulsada() {
        pulsada = true;
        soltada = false;
        ultimaPulsacion = Teclado.getTime();
    }

    public void teclaLiberada() {
        pulsada = false;
        soltada = true;
    }

    public void tooglePulsada() {
        if (soltada) {
            teclaPulsada();
        } else {
            pulsada = false;
        }
    }

}
