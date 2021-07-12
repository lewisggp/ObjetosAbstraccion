package Juego.Prueba;

import Juego.Vista.Ventana;

public class PruebaVentana {

    public static void main(String[] args) {
        Ventana ventana = new Ventana(80 * 16, 45 * 16);
        ventana.run();
    }
}
